package Control.Main;

import DAO.GiaoDichDAO;
import Model.GiaoDichTaiKhoanNguon;
import Model.TaiKhoan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Controller_History {

    //DAO===============================================================================//
    private final GiaoDichDAO giaoDichDAO = new GiaoDichDAO();

    //Attribute===============================================================================//
    private TaiKhoan taiKhoanNguon;
    private static ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich;
    private ObservableList<GiaoDichTaiKhoanNguon> filterGiaoDich;
    private int currentPage = 1;
    private final int rowsPerPage = 10;

    //FXML component===============================================================================//
    //<GiaoDich, String> : Kiểu dữ liệu mỗi dòng là GiaoDich, cột là String
    @FXML private TableView<GiaoDichTaiKhoanNguon> Table_LichSuGiaoDich;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_ThoiGianGiaoDich;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_LoaiGiaoDich;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, Double> Column_SoTien;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_TaiKhoanDich;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_NoiDung;
    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> ComboBox_LoaiGiaoDIch;
    @FXML private ComboBox<String> ComboBox_ThoiGian;
    @FXML private Label lblPage;
    // Liên kết các cột với thuộc tính của Transaction
    @FXML
    public void initialize() throws SQLException {
        Column_ThoiGianGiaoDich.setCellValueFactory(new PropertyValueFactory<>("thoiGianGiaoDich"));
        Column_LoaiGiaoDich.setCellValueFactory(new PropertyValueFactory<>("loaiGiaoDich"));
        Column_SoTien.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        Column_TaiKhoanDich.setCellValueFactory(new PropertyValueFactory<>("taiKhoanDich"));
        Column_NoiDung.setCellValueFactory(new PropertyValueFactory<>("noiDung"));

        ComboBox_LoaiGiaoDIch.setItems(FXCollections.observableArrayList("Tất cả", "Chuyển tiền", "Nhận tiền"));
        ComboBox_ThoiGian.setItems(FXCollections.observableArrayList("Tất cả", "Hôm nay", "7 ngày qua", "Tháng này"));

        ComboBox_LoaiGiaoDIch.getSelectionModel().selectFirst();
        ComboBox_ThoiGian.getSelectionModel().selectFirst();
    }

    //Event===============================================================================//
    public void capNhatBang() throws SQLException {
        Table_LichSuGiaoDich.setItems(tatCaGiaoDich);
    }

    public void handleLoc(ActionEvent actionEvent) {
        String loaiGiaoDich = ComboBox_LoaiGiaoDIch.getValue();
        String thoiGianGiaoDich = ComboBox_ThoiGian.getValue();
        String keyword =  txtSearch.getText();
        filterGiaoDich = tatCaGiaoDich.filtered(t -> {
            boolean checkLoaiGiaoDich = loaiGiaoDich.equals(t.getLoaiGiaoDich()) || loaiGiaoDich == null || loaiGiaoDich.equals("Tất cả");
            boolean checkThoiGian = true;
            if(thoiGianGiaoDich != null){
                LocalDate now = LocalDate.now();
                LocalDate time = t.getThoiGianGiaoDich().toLocalDate();
                switch (thoiGianGiaoDich) {
                    case "Hôm nay": checkThoiGian = time.getDayOfYear() == now.getDayOfYear(); break;
                    case "7 ngày qua": checkThoiGian = time.isAfter(now.minusDays(7)); break;
                    case "Tháng này": checkThoiGian = time.getMonth() == now.getMonth(); break;
                }
            }
            boolean checkKeyword = t.getNoiDung().toLowerCase().contains(keyword) || t.getTaiKhoanDich().toString().toLowerCase().contains(keyword);
            return checkLoaiGiaoDich && checkThoiGian && checkKeyword;
        });
        Table_LichSuGiaoDich.setItems(filterGiaoDich);
    }

    public void handleTrangTruoc(ActionEvent actionEvent) {
    }

    public void handleTrangSau(ActionEvent actionEvent) {

    }

    public void setTaiKhoanNguon(TaiKhoan taiKhoanNguon){
        this.taiKhoanNguon = taiKhoanNguon;
    }

    public void setTatCaGiaoDich(ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich) {
        Controller_History.tatCaGiaoDich = tatCaGiaoDich;
    }
}

