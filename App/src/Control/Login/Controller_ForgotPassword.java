package Control.Login;

import DAO.NguoiDungDAO;
import DAO.OTPDAO;
import Model.BaoMat;
import Model.NguoiDung;
import View.Popup.alert;
import View.Popup.label;
import Control.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;

public class Controller_ForgotPassword {
    //DAO=============================================================================================================//
    private static final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    public static OTPDAO OtpDAO = new OTPDAO();

    //Attribute=======================================================================================================//
    public static NguoiDung nguoiDung;
    public static String SDT;

    //Controller and scene============================================================================================//
    private ManegerScene manegerMainScene;
    private ManegerScene manegerSubScene = new ManegerScene();
    private Controller_VerifyOTP  controller_VerifyOTP;

    //FXML component==================================================================================================//
    public TextField TextField_SoDienThoai,TextField_OTP;
    public PasswordField PasswordField_NhapMatKhau;
    public PasswordField PasswordField_XacThucMatKhau;
    public Label Label_MatKhauLoi;
    public Label Label_MatKhauXacThucLoi;

    //Event===========================================================================================================//
    public void handleGuiMaXacNhan(ActionEvent actionEvent) throws Exception {
        SDT = TextField_SoDienThoai.getText();
        if(NguoiDung.kiemTraDauVaoSDT(SDT)){

            String otp = BaoMat.generateOTP();

            String otp_hash = BaoMat.sha256(otp);

            alert.INFORMATION("Mã OTP","Mã OTP của bạn là "+otp);

            LocalDateTime expiresAt = BaoMat.nowPlusMinutes(5);

            OtpDAO.saveOtp(SDT,otp_hash,expiresAt);
            nguoiDung = nguoiDungDAO.findByAttribute("SDT", SDT);
            manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Login/VerifyOTPPassword.fxml")));
            truyenDuLieuVerifyOTP();
            manegerSubScene.changeWithOldStage(actionEvent,"Xác thực OTP");

        }
        TextField_SoDienThoai.setText("");
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerSubScene.changeWithOldStage(actionEvent,"Login");
    }

    //Truyen du lieu==================================================================================================//
    private void truyenDuLieuVerifyOTP() {
        setController_VerifyOTP();
        controller_VerifyOTP.setSDT(SDT);
        manegerSubScene.setBackLoarder(manegerMainScene.getCurrentLoader());
        controller_VerifyOTP.setManegerMainScene(manegerSubScene);
        controller_VerifyOTP.setNguoiDung(nguoiDung);
    }

    //Set attribute===================================================================================================//
    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }

    //Set controller==================================================================================================//
    public void setController_VerifyOTP() {
        this.controller_VerifyOTP = manegerSubScene.getControllerOfLoader();
    }
}
