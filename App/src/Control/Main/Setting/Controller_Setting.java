package Control.Main.Setting;

import Model.NguoiDung;
import Model.SpareKey;

import Control.ManegerScene;
import Model.TaiKhoan;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Controller_Setting {
    //Controller and scene============================================================================================//
    private Controller_UserInfor controller_UserInfor;
    private Controller_ChangePassword  controller_ChangePassword;
    private Controller_ChangePIN  controller_ChangePIN;
    private ManegerScene manegerSubScene = new ManegerScene(),manegerMainScene;

    //Attribute=======================================================================================================//
    private NguoiDung nguoiDung;
    private TaiKhoan taiKhoan;

    //Event===========================================================================================================//

    public void handleXemThongTin(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/Setting/UserInfor.fxml")));
        truyenDuLieuUserInfor();
        manegerSubScene.changeWithOldStage(actionEvent,"User infor");
    }

    public void handleDoiMatKhau(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/Setting/ChangePassword.fxml")));
        truyenDuLieuChangePassword();
        manegerSubScene.changeWithOldStage(actionEvent,"Banking App");
    }

    public void handleDoiMaPIN(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/Setting/ChangePIN.fxml")));
        truyenDuLieuChangePIN();
        manegerSubScene.changeWithOldStage(actionEvent,"ChangePIN");
    }

    public void handleDangXuat(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Login/Login.fxml")));
        manegerSubScene.changeWithOldStage(actionEvent,"Login");
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerMainScene.back(actionEvent);
    }
    //Truyen du lieu==================================================================================================//
    private void truyenDuLieuUserInfor() {
        setController_UserInfor();
        manegerSubScene.setBackLoarder(manegerMainScene.getCurrentLoader());
        controller_UserInfor.setManegerMainScene(manegerSubScene);
        controller_UserInfor.setNguoiDung(nguoiDung);
    }

    private void truyenDuLieuChangePassword() {
        setController_ChangePassword();
        manegerSubScene.setBackLoarder(manegerMainScene.getCurrentLoader());
        controller_ChangePassword.setManegerMainScene(manegerSubScene);
        controller_ChangePassword.setNguoiDung(nguoiDung);
        controller_ChangePassword.setTaiKhoan(taiKhoan);
    }

    private void truyenDuLieuChangePIN() {
        setController_ChangePIN();
        manegerSubScene.setBackLoarder(manegerMainScene.getCurrentLoader());
        controller_ChangePIN.setManegerMainScene(manegerSubScene);
        controller_ChangePIN.setTaiKhoan(taiKhoan);
        controller_ChangePIN.setNguoiDung(nguoiDung);
    }

    //Set controller==================================================================================================//
    public void setController_UserInfor() {
        this.controller_UserInfor = manegerSubScene.getControllerOfLoader();
    }

    public void setController_ChangePIN() {
        this.controller_ChangePIN = manegerSubScene.getControllerOfLoader();
    }

    public void setController_ChangePassword() {
        this.controller_ChangePassword = manegerSubScene.getControllerOfLoader();
    }

    // Set attribute==================================================================================================//
    public void setManegerMainScene(ManegerScene manegerScene) {
        this.manegerMainScene = manegerScene;
    }

    public void setManegerSubScene(ManegerScene manegerScene) {
        this.manegerSubScene = manegerScene;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {this.taiKhoan = taiKhoan;}
}
