package Control.Main.Transaction;

import Control.Main.Controller_DashBoard;
import DAO.GiaoDichDAO;
import DAO.OTPDAO;
import Model.*;
import Control.ManegerScene;
import View.Popup.alert;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Controller_VerifyOTPTransaction {
    //DAO=======================================================================================================//
    private final OTPDAO OtpDAO = new OTPDAO();
    private final GiaoDichDAO giaoDichDAO = new GiaoDichDAO();

    //Controller and Scene=======================================================================================================//
    private ManegerScene manegerMainScene;
    private ManegerScene manegerSubScene = new ManegerScene();
    private Controller_DashBoard controller_DashBoard;

    //Attribute=======================================================================================================//
    private TaiKhoan taiKhoanNguon;
    private TaiKhoan taiKhoanDich;
    private double soTienGiaoDich;
    private String soDienThoai;
    private Timeline timeline;
    private LocalDateTime endTime;
    private ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich;

    //FXML Component==================================================================================================//
    public TextField TextField_OTP;
    public Label Label_ThoiGianHieuLuc;
    public Label Label_Error;
    public Button Button_GuiLaiOTP;

    //Event===========================================================================================================//
    public void handleXacNhanOTP(ActionEvent actionEvent) throws Exception {
        if(LocalDateTime.now().isAfter(endTime)) {
            Label_Error.setText("Mã OTP đã hết hiệu lực");
            return;
        }
        String OTP = TextField_OTP.getText();
        String hashed_OTP = BaoMat.sha256(OTP);

        if(OtpDAO.verifyOtp(soDienThoai,hashed_OTP)) thucHienGiaoDich(actionEvent);

        else Label_Error.setText("Mã OTP không chính xác");

    }

    public void handleGuiLaiOTP(ActionEvent actionEvent) throws Exception {
        manegerSubScene.changeWithOldStage(actionEvent,"Xác thực mã PIN");
    }

    //Method==========================================================================================================//
    private void thucHienGiaoDich(ActionEvent actionEvent) throws SQLException, IOException {
        GiaoDich giaoDich = new GiaoDich(taiKhoanNguon.getSoTaiKhoan(), taiKhoanDich.getSoTaiKhoan(), soTienGiaoDich, "");
        giaoDich.congTienTaiKhoanDich();
        giaoDich.truTienTaiKhoanNguon(taiKhoanNguon);
        giaoDichDAO.create(giaoDich);
        GiaoDichTaiKhoanNguon giaoDichTaiKhoanNguon = new GiaoDichTaiKhoanNguon(giaoDich.getThoiGianGiaoDich(),LoaiGiaoDich.ChuyenTien,taiKhoanDich,soTienGiaoDich,"");
        tatCaGiaoDich.add(giaoDichTaiKhoanNguon);
        if(alert.INFORMATION("Giao dịch", "Giao dịch thành công") != null){
            manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/DashBoard.fxml")));
            truyenDuLieuDashBoard();
            manegerSubScene.changeWithOldStage(actionEvent,"DashBoard");
        }
    }

    public void initialize() {
        startCountdown();
    }

    private void startCountdown() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateCountdown()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateCountdown() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(endTime)) {
            java.time.Duration duration = java.time.Duration.between(now, endTime);
            long minutes = duration.toMinutes();
            long seconds = duration.getSeconds() % 60;
            Label_ThoiGianHieuLuc.setText(String.format("%02d:%02d", minutes, seconds));
        } else {
            Label_ThoiGianHieuLuc.setText("Hết hiệu lực!");
            timeline.stop();
        }
    }

    // Truyen du lieu=================================================================================================//
    private void truyenDuLieuDashBoard() throws SQLException {
        setController_DashBoard();
        controller_DashBoard.setTaiKhoan(taiKhoanNguon);
        controller_DashBoard.setSoDienThoai(soDienThoai);
        controller_DashBoard.setTatCaGiaoDich(tatCaGiaoDich);
        controller_DashBoard.reload();
    }

    // Setter attribute===============================================================================================//
    public void setTaiKhoanNguon(TaiKhoan taiKhoanNguon) {
        this.taiKhoanNguon = taiKhoanNguon;
    }

    public void setTaiKhoanDich(TaiKhoan taiKhoanDich) {
        this.taiKhoanDich = taiKhoanDich;
    }

    public void setSoTienGiaoDich(double soTienGiaoDich) {
        this.soTienGiaoDich = soTienGiaoDich;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setTatCaGiaoDich(ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich) {
        this.tatCaGiaoDich = tatCaGiaoDich;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }

    // Setter controller==============================================================================================//
    public void setController_DashBoard() {
        this.controller_DashBoard = manegerSubScene.getControllerOfLoader();
    }
}
