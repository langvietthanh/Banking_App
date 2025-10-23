package Control.Main.Transaction;

import DAO.OTPDAO;
import Model.BaoMat;
import Model.TaiKhoan;
import View.Popup.ManegerScene;
import View.Popup.alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Controller_VerifyPIN {
    //DAO=============================================================================================================//
    private final OTPDAO OtpDAO = new OTPDAO();

    //Attribute=======================================================================================================//
    private TaiKhoan taiKhoanNguon, taiKhoanDich;
    private String soDienThoai;
    private double soTienGiaoDich;
    private LocalDateTime thoiGianHetHan;

    //Controller and Scene============================================================================================//
    private Controller_VerifyOTPTransaction controller_VerifyOTPTransaction;
    private ManegerScene manegerMainScene;
    private ManegerScene manegerSubScene;

    //FXML Component==================================================================================================//
    public PasswordField PasswordField_PIN;

    //HandleButton====================================================================================================//
    public void handleXacThucMaPIN(ActionEvent actionEvent) throws Exception {

    String PIN = PasswordField_PIN.getText();

    //====================Kiểm tra mã PIN tài khoản đã mã hóa====================//
        if(BaoMat.checkPassword(PIN,taiKhoanNguon.getMaPIN())){
            guiMaOTP();
            manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/Transaction/VerifyOTPTransaction.fxml")));
            truyenDuLieu_VerifyOTPTransaction();
            manegerSubScene.changeWithOldStage(actionEvent,"Xác thực OTP");
        }
        else {
            alert.ERROR("Mã PIN lỗi", "Mã PIN không hợp lệ");
        }
        PasswordField_PIN.clear();
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerSubScene.changeWithOldStage(actionEvent, "Dash Board");
    }

    //Phương thức riêng của Controller hiện tại=======================================================================//
    public void guiMaOTP() throws Exception {
        String OTP  = BaoMat.generateOTP();
        String hashed_OTP = BaoMat.sha256(OTP);
        thoiGianHetHan = BaoMat.nowPlusMinutes(5);
        OtpDAO.saveOtp(soDienThoai,hashed_OTP,thoiGianHetHan);
        alert.INFORMATION("Mã OTP","Mã OTP của bạn là "+OTP);
    }

    // Truyen du lieu=================================================================================================//
    private void truyenDuLieu_VerifyOTPTransaction() {
        setController_VerifyOTPTransaction();
        controller_VerifyOTPTransaction.setTaiKhoanDich(taiKhoanDich);
        controller_VerifyOTPTransaction.setTaiKhoanNguon(taiKhoanNguon);
        controller_VerifyOTPTransaction.setSoTienGiaoDich(soTienGiaoDich);
        controller_VerifyOTPTransaction.setSoDienThoai(soDienThoai);
        controller_VerifyOTPTransaction.setEndTime(thoiGianHetHan);
        controller_VerifyOTPTransaction.setManegerSubScene(manegerMainScene);
    }

    // Setter attribute===============================================================================================//
    public void setTaiKhoanNguon(TaiKhoan taiKhoanNguon) {
        this.taiKhoanNguon = taiKhoanNguon;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setTaiKhoanDich(TaiKhoan taiKhoanDich) {
        this.taiKhoanDich = taiKhoanDich;
    }

    public void setSoTienGiaoDich(double soTienGiaoDich) {
        this.soTienGiaoDich = soTienGiaoDich;
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }

    // Setter controller==============================================================================================//
    public void setController_VerifyOTPTransaction() {
        this.controller_VerifyOTPTransaction = manegerSubScene.getControllerOfLoader() ;
    }

}
