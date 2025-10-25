package Control.Main.Setting;


import DAO.TaiKhoanDAO;
import Model.BaoMat;
import Model.NguoiDung;
import Model.SpareKey;
import Model.TaiKhoan;
import View.Popup.alert;
import Control.ManegerScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;


public class Controller_ChangePIN {
    //DAO=======================================================================================================//
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    //Attribute=======================================================================================================//
    private NguoiDung nguoiDung;
    private TaiKhoan taiKhoan;

    //Controller and Scene============================================================================================//
    private ManegerScene manegerMainScene;

    //FXML Component==================================================================================================//
    @FXML private PasswordField txtOldPIN;
    @FXML private PasswordField txtNewPIN;
    @FXML private PasswordField txtNewPIN2;
    @FXML private Label errorPIN;
    @FXML private Label errorNewPIN;

    //Event===========================================================================================================//
    public void handleChangePIN(ActionEvent actionEvent ) {
        String oldPIN = txtOldPIN.getText();
        String newPIN = txtNewPIN.getText();
        if (!checkPIN(oldPIN) || !checkNewPIN(newPIN)) return;
        taiKhoan.setMaPIN(BaoMat.hashPassword(newPIN));
        taiKhoanDAO.updateAttribute("maPIN", BaoMat.hashPassword(newPIN), nguoiDung.getCccd());
        alert.INFORMATION("Thông báo", "Bạn đã thay đổi mã PIN thành công");
    }

    public void handleBack(ActionEvent actionEvent ) throws IOException {
        manegerMainScene.back(actionEvent);
    }
    //Method===========================================================================================================//

    private boolean checkNewPIN(String newPIN) {
        if (newPIN.trim().isEmpty()) {
            errorNewPIN.setText("Vui lòng nhập mã PIN!");
            errorNewPIN.setVisible(true);
            return false;
        }
        if (newPIN.length() != 6) {
            errorNewPIN.setText("Vui lòng nhập lại mã PIN");
            errorNewPIN.setVisible(true);
            return false;
        }
        if (!newPIN.matches("[0-9]+")) {
            errorNewPIN.setText("Vui lòng nhập lại mã PIN");
            errorNewPIN.setVisible(true);
            return false;
        }
        if (!newPIN.equals(txtNewPIN2.getText())) {
            errorNewPIN.setText("Mã PIN không trùng nhau!");
            errorNewPIN.setVisible(true);
            return false;
        }
        errorNewPIN.setVisible(false);
        return true;
    }

    private boolean checkPIN(String pin){
        if (pin.isEmpty()) {
            errorNewPIN.setText("Vui lòng nhập mã PIN");
            errorNewPIN.setVisible(true);
            return false;
        }
        if (!BaoMat.checkPassword(pin,taiKhoan.getMaPIN())){
            errorPIN.setText("Mã PIN không hợp lệ!");
            errorPIN.setVisible(true);
            return false;
        }
        return true;
    }

    // Set attribute==================================================================================================//
    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

}
