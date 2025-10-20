package Control.Main.Setting;

import Model.SpareKey;

import View.Popup.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller_Setting {

    @FXML private Button btnXemThongTin;
    @FXML private Button btnDoiMatKhau;
    @FXML private Button btnDoiMaPIN;
    @FXML private Button btnDangXuat;
    private SpareKey spareKey;
    private ManegerScene scene;
    public void handleXemThongTin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main/Setting/UserInfor.fxml"));
            Parent root = loader.load();

            Controller_UserInfor controllerUserInfor = loader.getController();
            controllerUserInfor.setSpareKey(spareKey);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking App");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleDoiMatKhau(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main/Setting/ChangePassword.fxml"));
            Parent root = loader.load();

            Controller_ChangePassword controllerChangePassword = loader.getController();
            controllerChangePassword.setSpareKey(spareKey);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking App");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleDoiMaPIN(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main/Setting/ChangePIN.fxml"));
            Parent root = loader.load();

            Controller_ChangePIN controllerChangePIN = loader.getController();
            controllerChangePIN.setSpareKey(spareKey);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking App");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleDangXuat(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login/Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking App");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setSpareKey(SpareKey spareKey) {
        this.spareKey = spareKey;
    }
}
