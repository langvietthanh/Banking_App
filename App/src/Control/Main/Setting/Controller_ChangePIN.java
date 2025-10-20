package Control.Main.Setting;

import DAO.TaiKhoanDAO;
import Model.BaoMat;
import Model.SpareKey;
import View.Popup.alert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller_ChangePIN {
    @FXML private PasswordField txtOldPIN;
    @FXML private PasswordField txtNewPIN;
    @FXML private PasswordField txtNewPIN2;
    @FXML private Label errorPIN;
    @FXML private Label errorNewPIN;
    private SpareKey spareKey;
    private TaiKhoanDAO tkd = new TaiKhoanDAO();

    public void handleChangePIN(ActionEvent actionEvent ) {
        try {
            String oldPIN = txtOldPIN.getText();
            String newPIN = txtNewPIN.getText();
            if (!checkPIN(oldPIN)) return;
            if (!checkNewPIN(newPIN)) return;
            tkd.updateAttribute("maPIN", BaoMat.hashPassword(newPIN), spareKey.getCccd());
            alert.INFORMATION("Thông báo", "Bạn đã thay đổi mã PIN thành công");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleBack(ActionEvent actionEvent ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main/Setting/Setting.fxml"));
            Parent root = loader.load();

            // Truyền lại thông tin người dùng cho trang trước (nếu cần)
            Controller_Setting controllerSetting = loader.getController();
            controllerSetting.setSpareKey(spareKey);

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

    private boolean checkNewPIN(String newPIN){
        if (newPIN.trim().isEmpty()){
            errorNewPIN.setText("Vui lòng nhập mã PIN!");
            errorNewPIN.setVisible(true);
            return false;
        }
        if (newPIN.length()!=6){
            errorNewPIN.setText("Vui lòng nhập lại mã PIN");
            errorNewPIN.setVisible(true);
            return false;
        }
        if (!newPIN.matches("[0-9]+")){
            errorNewPIN.setText("Vui lòng nhập lại mã PIN");
            errorNewPIN.setVisible(true);
            return false;
        }
        if (!newPIN.equals(txtNewPIN2.getText())){
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
        if (!BaoMat.checkPassword(pin,tkd.findAttributeByCCCD("maPIN",spareKey.getCccd()))){
            errorPIN.setText("Mã PIN không hợp lệ!");
            errorPIN.setVisible(true);
            return false;
        }
        return true;
    }
}
