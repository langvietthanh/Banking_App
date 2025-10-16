package Control;

// Bat su kien
// Gia su trong Package View co:
// Window_1 : Cửa sổ đăng nhập
// Window_2 : Cửa sổ để đăng kí

import DAO.LockTimeDAO;
import DAO.NguoiDungDAO;
import DAO.TaiKhoanDAO;
import Model.BaoMat;
import Model.LockTime;
import Model.NguoiDung;
import Model.TaiKhoan;
import View.Popup.alert;
import View.Popup.scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_Login {

    public Button Button_QuenMatKhau;
    @FXML
    private TextField TextField_SoDienThoai;
    @FXML
    private PasswordField TextField_Password;

    private static int LuotDangNhapSai = 0;

    private static String SoDienThoai;
    private static TaiKhoan taiKhoan;
    public static NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    public static TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    public static LockTimeDAO lockTimeDAO = new LockTimeDAO();
    public LockTime lt;


    public void Dang_Nhap(ActionEvent actionEvent ) throws IOException, SQLException {
        String sdt = TextField_SoDienThoai.getText();
        String password = TextField_Password.getText();

        if( NguoiDung.kiemTraDauVaoSDT(sdt) ) {

            NguoiDung nd = nguoiDungDAO.findByAttribute("SDT",sdt);

            if(lockTimeDAO.existObject("SDT",sdt)) alert.ERROR("Người dùng bị khóa" , "Vui lòng đăng nhập lại sau\n"+ lockTimeDAO.getLockTime(sdt));

            else if (BaoMat.checkPassword(password , nd.getPassword()) ) {

                taiKhoan = taiKhoanDAO.findByAttribute("CCCD",nd.getCccd());
                SoDienThoai = nd.getSoDienThoai();

                scene.change(actionEvent, "/View/Main/DashBoard.fxml","Màn hình chính");
            }
            else {
                LuotDangNhapSai++;
                alert.ERROR("Sai thông tin","SDT hoặc mật khẩu không đúng \nVui lòng nhập lại");
            }
            if (LuotDangNhapSai == 3) {
                LuotDangNhapSai = 0;
                lt = new LockTime(sdt);
                lockTimeDAO.create(lt);
            }
        }
        TextField_SoDienThoai.setText("");
        TextField_Password.setText("");
    }

    public void Dang_Ki(ActionEvent actionEvent) throws IOException  {
        scene.change(actionEvent, "/View/Login/Register.fxml","Đăng kí");
    }

    public void Quen_Mat_Khau(ActionEvent actionEvent) throws IOException {
        scene.change(actionEvent, "/View/Login/ForgotPassword.fxml","Quên mật khẩu");
    }

//===============Lấy tài khoản lúc đăng nhập dùng cho DashBoard và những chức năng con trong DashBoard===============
    public static TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public static String getSoDienThoai() {
        return SoDienThoai;
    }
}
