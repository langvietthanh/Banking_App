package Control;

// Bat su kien
// Gia su trong Package View co:
// Window_1 : Cửa sổ đăng nhập
// Window_2 : Cửa sổ để đăng kí

import DAO.LockTimeDAO;
import DAO.NguoiDungDAO;
import Model.BaoMat;
import Model.LockTime;
import Model.NguoiDung;
import View.alert;
import View.scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_Login {
    public static NguoiDungDAO ndd = new NguoiDungDAO();
    public static LockTimeDAO ltd = new LockTimeDAO();
    public LockTime lt;
    public Button Button_QuenMatKhau;

    @FXML
    private TextField TextField_SoDienThoai;
    private static int LuotDangNhapSai = 0;

    @FXML
    private PasswordField TextField_Password;

    public void Dang_Nhap(ActionEvent actionEvent ) throws IOException, SQLException {
        String sdt = TextField_SoDienThoai.getText();
        String password = TextField_Password.getText();
        if( NguoiDung.kiemTraDauVaoSDT(sdt) ) {

            NguoiDung nd = ndd.findByAttribute("SDT",sdt);

            if(ltd.existObject("SDT",sdt)) alert.ERROR("Người dùng bị khóa" , "Vui lòng đăng nhập lại sau\n"+ltd.getLockTime(sdt));

            else if (BaoMat.checkPassword(password , nd.getPassword()) ) {
                scene.change(actionEvent,"/View/DashBoard.fxml","Màn hình chính");
            }
            else {
                LuotDangNhapSai++;
                alert.ERROR("Sai thông tin","SDT hoặc mật khẩu không đúng \nVui lòng nhập lại");
            }
            if (LuotDangNhapSai == 3) {
                LuotDangNhapSai = 0;
                lt = new LockTime(sdt);
                ltd.create(lt);
            }
        }
        TextField_SoDienThoai.setText("");
        TextField_Password.setText("");
    }

    public void Dang_Ki(ActionEvent actionEvent) throws IOException  {
        scene.change(actionEvent,"/View/Register.fxml","Đăng kí");
    }

    public void Quen_Mat_Khau(ActionEvent actionEvent) throws IOException {
        scene.change(actionEvent,"/View/ForgotPassword.fxml","Quên mật khẩu");
    }
}
