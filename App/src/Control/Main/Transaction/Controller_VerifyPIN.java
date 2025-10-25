package Control.Main.Transaction;

import DAO.OTPDAO;
import Model.BaoMat;
import Model.GiaoDichTaiKhoanNguon;
import Model.TaiKhoan;
import Control.ManegerScene;
import View.Popup.alert;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.time.LocalDateTime;

public class Controller_VerifyPIN {
    //DAO=============================================================================================================//
    private final OTPDAO OtpDAO = new OTPDAO();

    //Attribute=======================================================================================================//
    private TaiKhoan taiKhoanNguon, taiKhoanDich;
    private String soDienThoai;
    private double soTienGiaoDich;
    private LocalDateTime thoiGianHetHan;
    private ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich;

    //Controller and Scene============================================================================================//
    private Controller_VerifyOTPTransaction controller_VerifyOTPTransaction;
    private ManegerScene manegerMainScene;
    private ManegerScene manegerSubScene = new ManegerScene();

    //FXML Component==================================================================================================//
    public PasswordField PasswordField_PIN;

    //HandleButton====================================================================================================//
    public void handleXacThucMaPIN(ActionEvent actionEvent) throws Exception {

    String PIN = PasswordField_PIN.getText();

    //====================Kiểm tra mã PIN tài khoản đã mã hóa====================//
        if(BaoMat.checkPassword(PIN,taiKhoanNguon.getMaPIN())){
            guiMaOTP();
            manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/Transaction/VerifyOTPTransaction.fxml")));
            truyenDuLieu_VerifyOTPTransaction();
            manegerSubScene.changeWithOldStage(actionEvent,"Xác thực OTP");
        }
        else alert.ERROR("Mã PIN lỗi", "Mã PIN không hợp lệ");
        PasswordField_PIN.clear();
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerMainScene.back(actionEvent);
    }

    //Method==========================================================================================================//
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
        manegerSubScene.setBackLoarder(manegerMainScene.getCurrentLoader());
        controller_VerifyOTPTransaction.setManegerSubScene(manegerMainScene);
        controller_VerifyOTPTransaction.setTaiKhoanDich(taiKhoanDich);
        controller_VerifyOTPTransaction.setTaiKhoanNguon(taiKhoanNguon);
        controller_VerifyOTPTransaction.setSoTienGiaoDich(soTienGiaoDich);
        controller_VerifyOTPTransaction.setSoDienThoai(soDienThoai);
        controller_VerifyOTPTransaction.setEndTime(thoiGianHetHan);
        controller_VerifyOTPTransaction.setTatCaGiaoDich(tatCaGiaoDich);
    }

    // Setter controller==============================================================================================//
    public void setController_VerifyOTPTransaction() {
        this.controller_VerifyOTPTransaction = manegerSubScene.getControllerOfLoader() ;
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

    public void setTatCaGiaoDich(ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich) {
        this.tatCaGiaoDich = tatCaGiaoDich;
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }

}
