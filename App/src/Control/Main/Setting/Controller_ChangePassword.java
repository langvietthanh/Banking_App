package Control.Main.Setting;

import DAO.NguoiDungDAO;
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

public class Controller_ChangePassword {
    @FXML private PasswordField  txtPIN;
    @FXML private TextField txtNewPassword,txtNewPassword2;
    @FXML private Label errorMK,errorPIN;
    private SpareKey spareKey;
    private final NguoiDungDAO ndd = new NguoiDungDAO();
    private final TaiKhoanDAO tkd = new TaiKhoanDAO();
    public void handleChangePassword(ActionEvent actionEvent){
        try {
            String newPass = txtNewPassword.getText();
            String pin = txtPIN.getText();
            if (!checknewMK(newPass)) return;
            if (!checkPIN(pin)) return;
            ndd.updateAttribute("Password", BaoMat.hashPassword(newPass), spareKey.getCccd());
            alert.INFORMATION("Thông báo", "Cập nhật mật khẩu thành công");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void handleBack(ActionEvent actionEvent ){
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
    private boolean checknewMK(String mk){
        //----------Độ dài 8-------------
        if (mk.length()<8){
            errorMK.setText("Mật khẩu tối thiểu 8 kí tự!");
            errorMK.setVisible(true);
            return false;
        }
        //-----------kiểm tra định dạng mật khẩu --------
        boolean hasUpper = mk.matches(".*[A-Z].*");
        boolean hasLower = mk.matches(".*[a-z].*");
        boolean hasDigit = mk.matches(".*[0-9].*");
        boolean hasSpecial = mk.matches(".*[@!%&*$#].*");
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial){
            errorMK.setText("Mật khẩu phải có ít nhất 1 kí tự in hoa, 1 kí tự thường, 1 số, 1 kí tự đặc biệt!");
            errorMK.setVisible(true);
            return false;
        }
        //------------kiểm tra nhập lại mật khẩu có đúng không-------
        if(!mk.equals(txtNewPassword2.getText())){
            errorMK.setText("Mật khẩu không trùng khớp!");
            errorMK.setVisible(true);
            return false;
        }
        errorMK.setVisible(false);
        return true;
    }
    private boolean checkPIN(String pin){
        if (pin.isEmpty()) {
            errorPIN.setText("Vui lòng nhập mã PIN");
            errorPIN.setVisible(true);
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
