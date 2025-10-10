package Control;

// Bat su kien
// Gia su trong Package View co:
// Window_1 : Cửa sổ đăng nhập
// Window_2 : Cửa sổ để đăng kí

import DAO.NguoiDungDAO;
import Model.NguoiDung;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;
import java.util.Objects;

public class Controller_Login {
    @FXML
    private TextField usernameField;
    private static int LuotDangNhapSai = 0;

    @FXML
    private PasswordField passwordField;

    public void handleLogin(ActionEvent actionEvent ) throws IOException {
        String sdt = usernameField.getText();
        String password = passwordField.getText();
        NguoiDungDAO ndd = new NguoiDungDAO();
        if (! ndd.Ton_Tai_Nguoi_Dung(sdt)){
//            popup tbao
        }
        else {
            NguoiDung nd = ndd.Dang_Nhap(sdt, password);
            //      Đăng nhập thành công => nd tồn tại
            if (nd != null && LuotDangNhapSai < 3) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/DashBoard.fxml")));
                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Banking App");
                stage.show();
            }
            //        Đăng nhập sai tôi đa 3 lần
            else {
                LuotDangNhapSai++;
                System.out.print("wrong" + LuotDangNhapSai);
            }
        }
    }

    public void handleRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Register.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Banking App");
        stage.show();
    }

    public void handleForgotPassword(ActionEvent actionEvent) {
    }
}
