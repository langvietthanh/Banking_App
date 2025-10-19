package Control.Login;


import Control.Main.Controller_DashBoard;
import DAO.GiaoDichDAO;
import DAO.LockTimeDAO;
import DAO.NguoiDungDAO;
import DAO.TaiKhoanDAO;
import Model.*;
import View.Popup.alert;
import View.Popup.ManegerScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;


public class Controller_Login {
    //DAO===============================================================================//
    public static NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    public static TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    public static LockTimeDAO lockTimeDAO = new LockTimeDAO();
    public static GiaoDichDAO giaoDichDAO = new GiaoDichDAO();

    //Attribute===============================================================================//
    private static ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich;
    private static int LuotDangNhapSai = 0;
    private String SoDienThoai;
    private TaiKhoan taiKhoan;
    private NguoiDung nguoiDung;
    public LockTime lockTime;

    //Controller and scene===============================================================================//
    private Controller_DashBoard controller_DashBoard;
    private final ManegerScene manegerScene = new ManegerScene();

    //FXML component===============================================================================//
    @FXML
    private TextField TextField_SoDienThoai;
    @FXML
    private PasswordField TextField_Password;

    //Event===============================================================================//
    public void handleDangNhap(ActionEvent actionEvent ) throws IOException, SQLException {
        String sdt = TextField_SoDienThoai.getText();
        String password = TextField_Password.getText();
        if( NguoiDung.kiemTraDauVaoSDT(sdt) ) {
            setNguoiDung(nguoiDungDAO.findByAttribute("SDT",sdt));

            if(lockTimeDAO.existObject("SDT",sdt)) alert.ERROR("Người dùng bị khóa" , "Vui lòng đăng nhập lại sau\n"+ lockTimeDAO.getLockTime(sdt));

            else if (BaoMat.checkPassword(password , nguoiDung.getPassword()) ) {
                manegerScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/DashBoard.fxml")));
                truyenDuLieu();
                manegerScene.changeWithOldStage(actionEvent,"Màn hình chính");
            }
            else {
                LuotDangNhapSai++;
                alert.ERROR("Sai thông tin","SDT hoặc mật khẩu không đúng \nVui lòng nhập lại");
            }
            if (LuotDangNhapSai == 3) {
                LuotDangNhapSai = 0;
                lockTime = new LockTime(sdt);
                lockTimeDAO.create(lockTime);
                alert.ERROR("Tài khoản bị khóa" , "Tài khoản của bạn tạm thời bị khóa\nVui lòng thử lại sau " + lockTimeDAO.getLockTime(sdt));

            }
        }
        TextField_SoDienThoai.setText("");
        TextField_Password.setText("");
    }

    public void handleDangKi(ActionEvent actionEvent) throws IOException  {
            manegerScene.setLoader(new FXMLLoader(getClass().getResource("/View/Login/Register.fxml")));
            manegerScene.changeWithOldStage(actionEvent,"Đăng kí");
    }

    public void handleQuenMatKhau(ActionEvent actionEvent) throws IOException {
        manegerScene.setLoader(new FXMLLoader(getClass().getResource("/View/Login/ForgotPassword.fxml")));
        manegerScene.changeWithOldStage(actionEvent, "Quên mật khẩu");
    }


    //Method===============================================================================//
    private void truyenDuLieu() throws SQLException, IOException {
        setTaiKhoan(taiKhoanDAO.findByAttribute("CCCD",nguoiDung.getCccd()));
        setSoDienThoai(TextField_SoDienThoai.getText());
        setController();
        controller_DashBoard.setTaiKhoan(taiKhoan);
        controller_DashBoard.setSoDienThoai(SoDienThoai);

        tatCaGiaoDich =  FXCollections.observableArrayList();
        tatCaGiaoDich = giaoDichDAO.getAllGiaoDich(taiKhoan.getSoTaiKhoan());

        controller_DashBoard.setTatCaGiaoDich(tatCaGiaoDich);
        controller_DashBoard.reload();
    }

    //Lấy tài khoản lúc đăng nhập dùng cho DashBoard và những chức năng con trong DashBoard===============
    public void setTaiKhoan(TaiKhoan taiKhoan) throws SQLException {
        this.taiKhoan = taiKhoan ;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public void setNguoiDung(NguoiDung nd) {
        this.nguoiDung = nd;
    }

    public void setController_DashBoard() throws IOException {
        this.controller_DashBoard = manegerScene.getControllerOfLoader();
    }

    private void setController() throws IOException {
        setController_DashBoard();
    }
}
