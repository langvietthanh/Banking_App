package Control.Login;


import Control.Main.Controller_DashBoard;
import DAO.GiaoDichDAO;
import DAO.LockTimeDAO;
import DAO.NguoiDungDAO;
import DAO.TaiKhoanDAO;
import Model.*;
import View.Popup.alert;
import Control.ManegerScene;
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
    //DAO=============================================================================================================//
    public static NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    public static TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    public static LockTimeDAO lockTimeDAO = new LockTimeDAO();
    public static GiaoDichDAO giaoDichDAO = new GiaoDichDAO();

    //Attribute=======================================================================================================//
    private static ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich;
    private static int LuotDangNhapSai = 0;
    private String SoDienThoai;
    private TaiKhoan taiKhoan;
    private NguoiDung nguoiDung;
    public LockTime lockTime;

    //Controller and scene============================================================================================//
    private ManegerScene manegerMainScene;
    private ManegerScene manegerSubScene = new ManegerScene();
    private Controller_DashBoard controller_DashBoard;
    private Controller_Register controller_Register;
    private Controller_ForgotPassword  controller_ForgotPassword;

    //FXML component==================================================================================================//
    @FXML private TextField TextField_SoDienThoai;
    @FXML private PasswordField TextField_Password;

    //Event===========================================================================================================//
    public void handleDangNhap(ActionEvent actionEvent ) throws IOException, SQLException {
        String sdt = TextField_SoDienThoai.getText();
        String password = TextField_Password.getText();

        if(!NguoiDung.kiemTraDauVaoSDT(sdt) ) return;

        setNguoiDung(nguoiDungDAO.findByAttribute("SDT",sdt));

        if(nguoiDungBiKhoa(sdt)) return;

        else if (BaoMat.checkPassword(password , nguoiDung.getPassword()) ) {
            manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/DashBoard.fxml")));
            truyenDuLieuDashBoard();
            manegerSubScene.changeWithOldStage(actionEvent,"Màn hình chính");
        }

        else {
            LuotDangNhapSai++;
            alert.ERROR("Sai thông tin","SDT hoặc mật khẩu không đúng \nVui lòng nhập lại");
        }
        if (LuotDangNhapSai == 3) vuotGioiHanDangNhap(sdt);

        TextField_SoDienThoai.setText("");
        TextField_Password.setText("");
    }

    public void handleDangKi(ActionEvent actionEvent) throws IOException  {
            manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Login/Register.fxml")));
            truyenDuLieuRegister();
            manegerSubScene.changeWithOldStage(actionEvent,"Đăng kí");
    }

    public void handleQuenMatKhau(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Login/ForgotPassword.fxml")));
        truyenDuLieuForgotPassword();
        manegerSubScene.changeWithOldStage(actionEvent, "Quên mật khẩu");
    }


    //Method==========================================================================================================//
    private void vuotGioiHanDangNhap(String sdt) throws SQLException {
        if (LuotDangNhapSai == 3) {
            LuotDangNhapSai = 0;
            lockTime = new LockTime(sdt);
            lockTimeDAO.create(lockTime);
            alert.ERROR("Tài khoản bị khóa" , "Tài khoản của bạn tạm thời bị khóa\nVui lòng thử lại sau " + lockTimeDAO.getLockTime(sdt));
        }
    }

    private boolean nguoiDungBiKhoa(String sdt) throws SQLException {
        if(lockTimeDAO.existObject("SDT",sdt)) {
            alert.ERROR("Người dùng bị khóa", "Vui lòng đăng nhập lại sau\n" + lockTimeDAO.getLockTime(sdt));
            return true;
        }
        return false;
    }


    //Truyen du lieu==================================================================================================//
    private void truyenDuLieuDashBoard() throws SQLException {
        setTaiKhoan(taiKhoanDAO.findByAttribute("CCCD",nguoiDung.getCccd()));
        setSoDienThoai(TextField_SoDienThoai.getText());
        setController_DashBoard();
        tatCaGiaoDich =  FXCollections.observableArrayList();
        tatCaGiaoDich = giaoDichDAO.getAllGiaoDich(taiKhoan.getSoTaiKhoan());

        controller_DashBoard.setTaiKhoan(taiKhoan);
        controller_DashBoard.setSoDienThoai(SoDienThoai);
        controller_DashBoard.setManegerMainScene(manegerSubScene);
        controller_DashBoard.setTatCaGiaoDich(tatCaGiaoDich);
        controller_DashBoard.setNguoiDung(nguoiDung);
        controller_DashBoard.reload();
    }

    private void truyenDuLieuRegister() {
        setController_Register();
        controller_Register.setManegerMainScene(manegerSubScene);
        controller_Register.setManegerSubScene(manegerMainScene);
    }

    private void truyenDuLieuForgotPassword() {
        setController_ForgotPassword();
        controller_ForgotPassword.setManegerMainScene(manegerSubScene);
        controller_ForgotPassword.setManegerSubScene(manegerMainScene);
    }

    //Set controller==================================================================================================//
    public void setController_DashBoard(){
        this.controller_DashBoard = manegerSubScene.getControllerOfLoader();
    }

    public void setController_Register() {
        this.controller_Register = manegerSubScene.getControllerOfLoader();
    }

    public void setController_ForgotPassword() {
        this.controller_ForgotPassword = manegerSubScene.getControllerOfLoader();
    }

    //Set attribute===================================================================================================//
    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan ;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public void setNguoiDung(NguoiDung nd) {
        this.nguoiDung = nd;
    }
}
