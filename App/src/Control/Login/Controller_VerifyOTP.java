package Control.Login;

import Control.ManegerScene;
import DAO.OTPDAO;
import Model.BaoMat;
import Model.NguoiDung;
import View.Popup.alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.io.IOException;

public class Controller_VerifyOTP {
    //DAO=============================================================================================================//
    public static OTPDAO OtpDAO = new OTPDAO();

    //Attribute=======================================================================================================//
    private String SDT;
    private NguoiDung nguoiDung ;

    //Controller and scene============================================================================================//
    private ManegerScene manegerMainScene;
    private ManegerScene manegerSubScene = new  ManegerScene();
    private Controller_ResetPassword controller_resetPassword;

    //FXML component==================================================================================================//
    public TextField TextField_OTP;

    public void handleXacNhanOTP(ActionEvent actionEvent) throws Exception {
        String OTP_NguoiDung = TextField_OTP.getText();
        String HashedOTP_NguoiDung = BaoMat.sha256(OTP_NguoiDung);

        if(OtpDAO.verifyOtp(SDT,HashedOTP_NguoiDung)) {
            manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Login/ResetPassword.fxml")));
            truyenDuLieuResetPassword();
            manegerSubScene.changeWithOldStage(actionEvent, "Cấp lại mật khẩu");
        }
        else alert.ERROR("Mã OTP lỗi!","Mã OTP không chính xác! \n Yêu cầu nhập lại");
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerMainScene.back(actionEvent);
    }

    //Truyen du lieu==================================================================================================//
    private void truyenDuLieuResetPassword() {
        setController_resetPassword();
        controller_resetPassword.setNguoiDung(nguoiDung);
    }

    //Set controller==================================================================================================//
    public void setController_resetPassword() {
        this.controller_resetPassword = manegerSubScene.getControllerOfLoader();
    }

    //Set attribute===================================================================================================//
    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }
}
