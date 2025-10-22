package Control.Main.Setting;

import Model.SpareKey;

import View.Popup.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Controller_Setting {
    //Controller and scene===============================================================================//
    private Controller_UserInfor controller_UserInfor;
    private Controller_ChangePassword  controller_ChangePassword;
    private Controller_ChangePIN  controller_ChangePIN;

    //Attribute===============================================================================//
    private SpareKey spareKey;

    //FXML component===============================================================================//
    @FXML private Button btnXemThongTin;
    @FXML private Button btnDoiMatKhau;
    @FXML private Button btnDoiMaPIN;
    @FXML private Button btnDangXuat;

    //Event===============================================================================//
    private ManegerScene manegerSubScene = new ManegerScene(),manegerMainScene;

    public void handleXemThongTin(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/Setting/UserInfor.fxml")));
        truyenDuLieuUserInfor();
        manegerSubScene.changeWithOldStage(actionEvent,"Banking App");
    }

    public void handleDoiMatKhau(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/Setting/ChangePassword.fxml")));
        truyenDuLieuChangePassword();
        manegerSubScene.changeWithOldStage(actionEvent,"Banking App");
    }

    public void handleDoiMaPIN(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/Setting/ChangePIN.fxml")));
        truyenDuLieuChangePIN();
        manegerSubScene.changeWithOldStage(actionEvent,"Banking App");
    }

    public void handleDangXuat(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Login/Login.fxml")));
        manegerSubScene.changeWithOldStage(actionEvent,"Banking App");
    }

    //Truyền data cho controller con===============================================================================//

    private void truyenDuLieuUserInfor() {
        setController_UserInfor();
        controller_UserInfor.setSpareKey(spareKey);
        controller_UserInfor.setManegerMainScene(manegerSubScene);
        controller_UserInfor.setManegerSubScene(manegerMainScene);
    }

    private void truyenDuLieuChangePassword() {
        setController_ChangePassword();
        controller_ChangePassword.setSpareKey(spareKey);
    }

    private void truyenDuLieuChangePIN() {
        setController_ChangePIN();
        controller_ChangePIN.setSpareKey(spareKey);
    }

    //Set Controller=================================================================================================//

    public void setController_UserInfor() {
        this.controller_UserInfor = manegerSubScene.getControllerOfLoader();
    }

    public void setController_ChangePIN() {
        this.controller_ChangePIN = manegerSubScene.getControllerOfLoader();
    }

    public void setController_ChangePassword() {
        this.controller_ChangePassword = manegerSubScene.getControllerOfLoader();
    }
    // Data lấy từ Controller trước đấy (DashBoard)==========================================================//
    public void setManegerMainScene(ManegerScene manegerScene) {
        this.manegerMainScene = manegerScene;
    }

    public void setManegerSubScene(ManegerScene manegerScene) {
        this.manegerSubScene = manegerScene;
    }

    public void setSpareKey(SpareKey spareKey) {
        this.spareKey = spareKey;
    }
}
