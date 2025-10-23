package Control.Main.Transaction;

import DAO.NguoiDungDAO;
import DAO.TaiKhoanDAO;
import Model.GiaoDich;
import Model.NguoiDung;
import Model.TaiKhoan;
import View.Popup.label;
import View.Popup.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_Transaction {
    //DAO=============================================================================================================//
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

    //Attribute=======================================================================================================//
    private TaiKhoan taiKhoanNguon;
    private TaiKhoan taiKhoanDich;
    private double soTienGiaoDich;
    private String soDienThoai;

    //Controller and Scene============================================================================================//
    private Controller_KeyBoard  controller_KeyBoard;
    private Controller_VerifyPIN controller_VerifyPIN;
    private ManegerScene manegerMainScene;
    private ManegerScene manegerSubScene;

    //FXML Component==================================================================================================//
    public Button Button_XacNhan;
    public Label Label_SoTien;
    public Label Label_TaiKhoanKhongTonTai;
    public Label Label_TenTaiKhoan;
    public TextField TextField_OTP;
    public Label Label_OTPHetHan;
    public Label Label_TaiKhoanDichLoi;
    public TextField TextField_TaiKhoanNhanTien;
    public TextField TextField_PIN;

    //HandleButton====================================================================================================//

    public void handleXacNhanTaiKhoanDich() throws SQLException {
        String soTKDich = TextField_TaiKhoanNhanTien.getText();
        //Kiểm tra tài khoản đích tồn tại không + không trùng tài khoản gốc====================//
        int mode = GiaoDich.kiemTraNguonVaDich(taiKhoanNguon.getSoTaiKhoan(), soTKDich);
        if(mode == 0) {
            this.setTaiKhoanDich(taiKhoanDAO.findByAttribute("SotaiKhoan",soTKDich));
            NguoiDung nguoiDung = nguoiDungDAO.findByAttribute("CCCD",taiKhoanDich.getCccd());
            Label_TenTaiKhoan.setText(nguoiDung.getHoTen());
        }
        else xuatThongBaoLoiTKDich(mode);
    }

    public void handleNhapSoTien() throws IOException {
        manegerSubScene.setLoader(new FXMLLoader(Controller_Transaction.class.getResource("/View/Main/Transaction/KeyBoard.fxml")));
        nhapSoTien();
        if (controller_KeyBoard.getFlag()){
            label.MESS(Label_SoTien, controller_KeyBoard.getValueIsString());
            Button_XacNhan.setVisible(true);
            Button_XacNhan.setDisable(false);
        }
    }

    public void handleXacNhan(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/Transaction/VerifyPIN.fxml")));
        truyenDuLieuVerifyPIN();
        manegerSubScene.changeWithOldStage(actionEvent, "Xác thực mã PIN");
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerSubScene.changeWithOldStage(actionEvent, "Dash Board");
    }

    //Phương thức riêng của Controller hiện tại=======================================================================//

    private void nhapSoTien() {
        truyenDuLieuKeyBoard();
        Scene scene = new Scene(manegerSubScene.getRoot());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Key Board");
        stage.showAndWait();
        soTienGiaoDich = controller_KeyBoard.getValueIsDouble();
    }

    private void xuatThongBaoLoiTKDich(int modeTaiKhoanDich) {
        switch(modeTaiKhoanDich){
            case 1:
                label.MESS(Label_TaiKhoanDichLoi,"Tài khoản không tồn tại trong hệ thống");
                break;
            case 2:
                label.MESS(Label_TaiKhoanDichLoi,"Tài khoản không hợp lệ ");
                break;
        }
    }

    // Truyen du lieu=================================================================================================//

    private void truyenDuLieuVerifyPIN() {
        setController_VerifyPIN();
        controller_VerifyPIN.setSoDienThoai(soDienThoai);
        controller_VerifyPIN.setTaiKhoanNguon(taiKhoanNguon);
        controller_VerifyPIN.setTaiKhoanDich(taiKhoanDich);
        controller_VerifyPIN.setManegerMainScene(manegerSubScene);
        controller_VerifyPIN.setManegerSubScene(manegerMainScene);
        controller_VerifyPIN.setSoTienGiaoDich(soTienGiaoDich);
    }

    private void truyenDuLieuKeyBoard() {
        setController_KeyBoard();
        controller_KeyBoard.setTaiKhoanNguon(taiKhoanNguon);
        controller_KeyBoard.setManegerMainScene(manegerSubScene);
        controller_KeyBoard.setManegerSubScene(manegerMainScene);
    }

    // Setter attribute===============================================================================================//

    public void setTaiKhoanDich(TaiKhoan taiKhoan) {taiKhoanDich = taiKhoan;}

    public void setTaiKhoanNguon(TaiKhoan taiKhoan) {
        taiKhoanNguon = taiKhoan;
    }

    public void setSoDienThoai(String sdt) {
        soDienThoai = sdt;
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }

    // Setter controller==============================================================================================//

    public void setController_KeyBoard() {
        this.controller_KeyBoard = manegerSubScene.getControllerOfLoader();
    }

    private void setController_VerifyPIN() {
        controller_VerifyPIN = manegerSubScene.getControllerOfLoader();
    }
}
