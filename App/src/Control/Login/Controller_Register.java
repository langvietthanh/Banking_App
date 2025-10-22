package Control.Login;

import Model.NguoiDung;
import Model.SpareKey;
import View.Popup.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import DAO.NguoiDungDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.lang.String;
import java.time.LocalDateTime;


public class Controller_Register {


    //Attribute===============================================================================//
    private final NguoiDung nd = new NguoiDung();
    private final SpareKey spareKey = new SpareKey();

    //Controller and scene===============================================================================//
    private Controller_Register_Account controller_Register_Account;
    private ManegerScene manegerMainScene ;
    private ManegerScene manegerSubScene;

    //FXML component===============================================================================//
    @FXML private TextField textField_hoten;
    @FXML private TextField textField_cccd;
    @FXML private TextField textField_sdt;
    @FXML private TextField textField_mk;
    @FXML private TextField textField_mk2;
    @FXML private TextField textField_email;
    @FXML private TextField textField_diaChi;
    @FXML private DatePicker datePicker_ngaySinh;
    @FXML private DatePicker datePicker_hanCCCD;
    @FXML private Label errorHoTen;
    @FXML private Label errorCCCD;
    @FXML private Label errorSDT;
    @FXML private Label errorMK;
    @FXML private Label errorMK2;
    @FXML private Label errorDiaChi;
    @FXML private Label errorEmail;
    @FXML private Label errorNgaySinh;
    @FXML private Label errorHanCCCD;

    public void handleRegister(ActionEvent actionEvent ) throws IOException {
        nd.setControllerRegister(this);
        String hoTen = textField_hoten.getText();
        String cccd = textField_cccd.getText();
        String sdt = textField_sdt.getText();
        String passWord = textField_mk.getText();
        String email = textField_email.getText();
        String diaChi = textField_diaChi.getText();
        LocalDate ngaySinh = datePicker_ngaySinh.getValue();
        LocalDate hanCCCD = datePicker_hanCCCD.getValue();

        if (!nd.checkThongTin(hoTen, cccd, sdt, passWord, email, diaChi, ngaySinh, hanCCCD)) return;
        if(!nd.checkExist(cccd,sdt,email)) return;

        nd.setHoTen(hoTen);
        nd.setCccd(cccd);
        nd.setPassword(passWord);
        nd.setSoDienThoai(sdt);
        nd.setEmail(email);
        nd.setDiaChi(diaChi);
        nd.setNgaySinh(ngaySinh);
        nd.setHanCCCD(hanCCCD);
        nd.setNgayDangKi(LocalDateTime.now());
        spareKey.setCccd(cccd);

        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Login/RegisterBankAccount.fxml")));
        truyenDuLieu_Register_Account();
        manegerSubScene.changeWithOldStage(actionEvent,"Đăng kí tài khoản");
    }

    private void truyenDuLieu_Register_Account() {
        setController_Register_Account();
        controller_Register_Account.setManegerMainScene(manegerSubScene);
        controller_Register_Account.setManegerSubScene(manegerMainScene);
        controller_Register_Account.setNguoiDung(nd);
    }

    // Quay lại màn Login
    public void handleBack(ActionEvent actionEvent) throws IOException {
            manegerSubScene.changeWithOldStage(actionEvent,"Login");
        }

    public TextField getTextField_mk2() {
        return textField_mk2;
    }

    public Label getErrorCCCD() {
        return errorCCCD;
    }

    public Label getErrorHoTen() {
        return errorHoTen;
    }

    public Label getErrorSDT() {
        return errorSDT;
    }

    public Label getErrorMK() {
        return errorMK;
    }

    public Label getErrorMK2() {
        return errorMK2;
    }

    public Label getErrorDiaChi() {
        return errorDiaChi;
    }

    public Label getErrorNgaySinh() {
        return errorNgaySinh;
    }

    public Label getErrorEmail() {
        return errorEmail;
    }

    public Label getErrorHanCCCD() {
        return errorHanCCCD;
    }

    public void setController_Register_Account() {
        this.controller_Register_Account = manegerSubScene.getControllerOfLoader();
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }
}

