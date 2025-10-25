package Control.Main.Setting;

import DAO.NguoiDungDAO;
import DAO.TaiKhoanDAO;
import Model.*;
import View.Popup.alert;
import Control.ManegerScene;
import View.Popup.label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_ChangePassword {
    //DAO=============================================================================================================//
    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

    //Controller and scene============================================================================================//
    private ManegerScene manegerMainScene;

    //FXML component==================================================================================================//
    @FXML private PasswordField  txtPIN;
    @FXML private TextField txtNewPassword;
    @FXML private Label errorPIN;
    public TextField txtNewPassword2;
    public Label errorMK;

    //Attribute=======================================================================================================//
    private NguoiDung nguoiDung;
    private TaiKhoan taiKhoan;

    //Event===========================================================================================================//
    public void handleChangePassword(ActionEvent actionEvent) throws SQLException {
        String pin = txtPIN.getText();
        Password newPass = new Password(txtNewPassword.getText());

        if (!checkNewPass(newPass)) return;
        if (!checkPIN(pin)) return;
        newPass.hashPass();
        nguoiDung.setPassword(newPass.getHashedPass());
        nguoiDungDAO.update(nguoiDung);
        alert.INFORMATION("Thông báo", "Cập nhật mật khẩu thành công");

    }

    public void handleBack(ActionEvent actionEvent ) throws IOException {
        manegerMainScene.back(actionEvent);
    }

    //Set attribute===================================================================================================//
    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {this.taiKhoan = taiKhoan;}

    private boolean checkNewPass(Password newPass) {
        String passChecker = txtNewPassword2.getText();
        switch (newPass.checkPass(passChecker)){
            case 1:
                label.MESS(errorMK,"Mật khẩu tối thiểu 8 kí tự!");
                return false;
            case 2:
                label.MESS(errorMK,"Mật khẩu phải có ít nhất 1 kí tự in hoa, 1 kí tự thường, 1 số, 1 kí tự đặc biệt!");
                return false;
            case 3:
                label.MESS(errorMK,"Mật khẩu không khớp với mật khẩu xác nhận");
                return false;
            default:
                return true;
        }
    }

    private boolean checkPIN(String pin){
        if (pin.isEmpty()) {
            errorPIN.setText("Vui lòng nhập mã PIN");
            errorPIN.setVisible(true);
            return false;
        }
        if (!BaoMat.checkPassword(pin, taiKhoan.getMaPIN())){
            errorPIN.setText("Mã PIN không hợp lệ!");
            errorPIN.setVisible(true);
            return false;
        }
        return true;
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }
}
