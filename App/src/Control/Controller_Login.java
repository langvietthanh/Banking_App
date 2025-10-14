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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Objects;

public class Controller_Login {
    public static NguoiDungDAO ndd = new NguoiDungDAO();
    public Button Button_QuenMatKhau;

    @FXML
    private TextField TextField_SoDienThoai;
    private static int LuotDangNhapSai = 0;

    @FXML
    private PasswordField TextField_Password;
    public static void change_scene(ActionEvent actionEvent, String nameFileFXML, String sceneTitle) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Controller_Login.class.getResource(nameFileFXML)));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(sceneTitle);
        stage.show();
    }

    public static boolean Check_Input_SDT(String sdt) throws SQLException {
        if(sdt.isEmpty()) {
            Xuat_Thong_Bao_SDT_Empty();
            return false;
        }
        else if(!NguoiDung.Kiem_Tra_SDT(sdt)) {
            Xuat_Thong_Bao_SDT_Sai_Dinh_Dang();
            return false;
        }
        else if (!ndd.Ton_Tai_SDT(sdt)) {
            Xuat_Thong_Bao_Loi();
            return false;
        }
        return true;
    }

    private static void Xuat_Thong_Bao_SDT_Sai_Dinh_Dang() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi đăng nhập!");
        alert.setContentText("Định dạng số điện không phù hợp");
        alert.showAndWait();
    }

    private static void Xuat_Thong_Bao_SDT_Empty() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lỗi đăng nhập!");
        alert.setContentText("Vui lòng nhập số điện thoại!");
        alert.showAndWait();
    }

    private static void Xuat_Thong_Bao_Loi() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi đăng nhập!");
        alert.setContentText("Người dùng chưa tồn tại trong hệ thống");
        alert.showAndWait();
    }

    public void Dang_Nhap(ActionEvent actionEvent ) throws IOException, SQLException {
        String sdt = TextField_SoDienThoai.getText();
        String password = TextField_Password.getText();
        if(Check_Input_SDT(sdt)) {
//           Người dùng tồn tại
            NguoiDung nd = ndd.findBySDTandPassword(sdt, password);
            //      Đăng nhập thành công => nd tồn tại
            if (nd != null && LuotDangNhapSai < 3 ) {
                change_scene(actionEvent,"/View/DashBoard.fxml","Màn hình chính");
            }
            //        Đăng nhập sai tôi đa 3 lần => giảm 1 mức uy tín
            else {
                LuotDangNhapSai++;

            }
        }
        TextField_SoDienThoai.setText("");
        TextField_Password.setText("");
    }

    public void Dang_Ki(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Register.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Banking App");
        stage.show();
    }

    public void Quen_Mat_Khau(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ForgotPassword.fxml")));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Quên mật khẩu");
        stage.show();
    }
}
