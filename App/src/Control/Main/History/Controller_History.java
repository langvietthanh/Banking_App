package Control.Main.History;

import Control.Main.Controller_DashBoard;
import Model.GiaoDichTaiKhoanNguon;
import Control.ManegerScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class Controller_History {
    //Controller and Scene============================================================================================//
    private ManegerScene manegerSubScene;
    private Controller_DashBoard controller_DashBoard;

    //Attribute=======================================================================================================//
    private ObservableList<GiaoDichTaiKhoanNguon> listGiaoDichTaiKhoanNguon;
    private ObservableList<GiaoDichTaiKhoanNguon> filterGiaoDich;
    private int currentPage;
    private int maxPage;
    private final int maxRowOfPage = 10;

    //FXML component==================================================================================================//
    //<GiaoDich, String> : Kiểu dữ liệu mỗi dòng là GiaoDich, cột là String
    @FXML private TableView<GiaoDichTaiKhoanNguon> Table_LichSuGiaoDich;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_ThoiGianGiaoDich;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_LoaiGiaoDich;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_SoTien;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_TaiKhoanDich;
    @FXML private TableColumn<GiaoDichTaiKhoanNguon, String> Column_NoiDung;
    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> ComboBox_LoaiGiaoDich;
    @FXML private ComboBox<String> ComboBox_ThoiGian;
    @FXML private Label Label_Page;

    @FXML    // Liên kết các cột với thuộc tính của GiaoDichTaiKhoanNguon
    public void initialize() throws SQLException {
        Column_ThoiGianGiaoDich.setCellValueFactory(new PropertyValueFactory<>("thoiGianGiaoDichString"));
        Column_LoaiGiaoDich.setCellValueFactory(new PropertyValueFactory<>("loaiGiaoDich"));
        Column_SoTien.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        Column_TaiKhoanDich.setCellValueFactory(new PropertyValueFactory<>("taiKhoanDich"));
        Column_NoiDung.setCellValueFactory(new PropertyValueFactory<>("noiDung"));

        ComboBox_LoaiGiaoDich.setItems(FXCollections.observableArrayList("Tất cả", "Chuyển tiền", "Nhận tiền"));
        ComboBox_ThoiGian.setItems(FXCollections.observableArrayList("Tất cả", "Hôm nay", "7 ngày qua", "Tháng này"));

        ComboBox_LoaiGiaoDich.getSelectionModel().selectFirst();
        ComboBox_ThoiGian.getSelectionModel().selectFirst();
    }

    //Event===============================================================================//
    public void handleLoc(ActionEvent actionEvent) {
        String loaiGiaoDich = ComboBox_LoaiGiaoDich.getValue();
        String thoiGianGiaoDich = ComboBox_ThoiGian.getValue();
        String keyword =  txtSearch.getText();
        ObservableList<GiaoDichTaiKhoanNguon> tmp = listGiaoDichTaiKhoanNguon.filtered(t -> {
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
        setFilterGiaoDich(tmp);
        capNhatBang();
    }

    public void handleXoaDuLieuLoc(ActionEvent actionEvent) {
        ComboBox_LoaiGiaoDich.getSelectionModel().selectFirst();
        ComboBox_ThoiGian.getSelectionModel().selectFirst();
        txtSearch.clear();
        setFilterGiaoDich(listGiaoDichTaiKhoanNguon);
        capNhatBang();
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerSubScene.changeWithOldStage(actionEvent,"DashBoard");
    }

    public void handleTrangTruoc(ActionEvent actionEvent) {
        if(currentPage == 1 || maxPage == 0) return;
        setCurrentPage(currentPage - 1);
        int lastRow = currentPage * maxRowOfPage ;
        int firstRow = lastRow - maxRowOfPage;
        hienThiBang(firstRow,lastRow);
        capNhatPage();
    }

    public void handleTrangSau(ActionEvent actionEvent) {
        if(currentPage == maxPage || maxPage == 0) return;
        setCurrentPage(currentPage + 1);
        int firstRow = (currentPage - 1) * maxRowOfPage;
        int lastRow;
        if(currentPage == maxPage) lastRow = listGiaoDichTaiKhoanNguon.size();
        else lastRow = firstRow + maxRowOfPage;
        hienThiBang(firstRow,lastRow);
        capNhatPage();
    }

    //Phương thức riêng của Controller hiện tại=======================================================================//
    public void capNhatMaxPage(){
        int size = filterGiaoDich.size();
        int a = size/10,b=size%10;
        setMaxPage ( (b!=0) ? (a+1):(a));
    }

    public void capNhatBang(){
        capNhatMaxPage();
        setCurrentPage((maxPage != 0) ? 1:0);
        capNhatPage();
        hienThiBang(0, (maxPage > 1) ? 10 : filterGiaoDich.size());
    }

    public void capNhatPage(){
        Label_Page.setText("Trang " + currentPage + "/" + maxPage);
    }

    public void hienThiBang(int firstRow, int lastRow) {
        ObservableList<GiaoDichTaiKhoanNguon> sub = FXCollections.observableArrayList(filterGiaoDich.subList(firstRow, lastRow));
        setTable_LichSuGiaoDich(sub);
    }

    // Setter attribute===============================================================================================//
    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }

    public void setListGiaoDichTaiKhoanNguon(ObservableList<GiaoDichTaiKhoanNguon> listGiaoDichTaiKhoanNguon) {
        this.listGiaoDichTaiKhoanNguon = listGiaoDichTaiKhoanNguon;
    }

    public void setFilterGiaoDich(ObservableList<GiaoDichTaiKhoanNguon> filterGiaoDich) {
        this.filterGiaoDich = filterGiaoDich;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTable_LichSuGiaoDich(ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich) {
        Table_LichSuGiaoDich.setItems(tatCaGiaoDich);
    }
}

