package Control;

import DAO.TaiKhoanDAO;
import Model.SpareKey;
import Model.TaiKhoan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_Register_Account {
    @FXML private TextField SoTaiKhoan;
    @FXML private PasswordField PIN;
    @FXML private PasswordField PIN2;
    @FXML private Label errorSoTaiKhoan;
    @FXML private Label errorPIN;
    @FXML private Label lblThongBao;
    private SpareKey spareKey ;

    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final TaiKhoan taiKhoan = new TaiKhoan();
    public void handleRegister(ActionEvent actionEvent ) throws IOException, SQLException{
        taiKhoan.setControllerRegisterAccount(this);
        String stk = SoTaiKhoan.getText();
        String pin = PIN.getText();
        if(!taiKhoan.checkThongTin(stk,pin)) return;
//        if (!taiKhoan.checkExistSTK(stk)) return;
        taiKhoan.setSoTaiKhoan(stk);
        taiKhoan.setMaPIN(pin);
        taiKhoan.setCccd(spareKey.getCccd());
//        taiKhoanDAO.create(taiKhoan);
        Parent root = FXMLLoader.load(getClass().getResource("/View/Success.fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Thông báo");
        stage.show();
    }
    public PasswordField getPIN2() {
        return PIN2;
    }

    public Label getErrorSoTaiKhoan() {
        return errorSoTaiKhoan;
    }

    public Label getErrorPIN() {
        return errorPIN;
    }

    public void setSpareKey(SpareKey spareKey) {
        this.spareKey = spareKey;
    }
}
