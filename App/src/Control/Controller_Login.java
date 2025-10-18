package Control;


import DAO.LockTimeDAO;
import DAO.NguoiDungDAO;
import DAO.TaiKhoanDAO;
import Model.BaoMat;
import Model.LockTime;
import Model.NguoiDung;
import Model.TaiKhoan;
import View.Popup.alert;
import View.Popup.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_Login {
    private Controller_DashBoard controller_DashBoard;
    private final ManegerScene scene = new ManegerScene();

    @FXML
    private TextField TextField_SoDienThoai;
    @FXML
    private PasswordField TextField_Password;

    private static int LuotDangNhapSai = 0;

    private static String SoDienThoai;
    private static TaiKhoan taiKhoan;
    private static NguoiDung nguoiDung;
    public static NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    public static TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    public static LockTimeDAO lockTimeDAO = new LockTimeDAO();
    public LockTime lockTime;

    public void handleDangNhap(ActionEvent actionEvent ) throws IOException, SQLException {
        String sdt = TextField_SoDienThoai.getText();
        String password = TextField_Password.getText();
        if( NguoiDung.kiemTraDauVaoSDT(sdt) ) {
            setNguoiDung(nguoiDungDAO.findByAttribute("SDT",sdt));

            if(lockTimeDAO.existObject("SDT",sdt)) alert.ERROR("Người dùng bị khóa" , "Vui lòng đăng nhập lại sau\n"+ lockTimeDAO.getLockTime(sdt));

            else if (BaoMat.checkPassword(password , nguoiDung.getPassword()) ) {
                setTaiKhoan(taiKhoanDAO.findByAttribute("CCCD",nguoiDung.getCccd()));
                SoDienThoai = sdt;
                scene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/DashBoard.fxml")));
                truyenDuLieu();
                scene.change(actionEvent,"Màn hình chính");

            }
            else {
                LuotDangNhapSai++;
                alert.ERROR("Sai thông tin","SDT hoặc mật khẩu không đúng \nVui lòng nhập lại");
            }
            if (LuotDangNhapSai == 3) {
                LuotDangNhapSai = 0;
                lockTime = new LockTime(sdt);
                lockTimeDAO.create(lockTime);
                alert.ERROR("Tài khoản bị khóa" , "Tài khoản của bạn tạm thời bị khóa\nVui lòng thử lại sau " + lockTimeDAO.getLockTime(sdt));

            }
        }
        TextField_SoDienThoai.setText("");
        TextField_Password.setText("");
    }

    public void handleDangKi(ActionEvent actionEvent) throws IOException  {
            scene.setLoader(new FXMLLoader(getClass().getResource("/View/Login/Register.fxml")));
            scene.change(actionEvent,"Đăng kí");
    }

    public void handleQuenMatKhau(ActionEvent actionEvent) throws IOException {
        scene.setLoader(new FXMLLoader(getClass().getResource("/View/Login/ForgotPassword.fxml")));
        scene.change(actionEvent, "Quên mật khẩu");
    }

//===============Lấy tài khoản lúc đăng nhập dùng cho DashBoard và những chức năng con trong DashBoard===============
    public void setTaiKhoan(TaiKhoan taiKhoan) throws SQLException {
        Controller_Login.taiKhoan = taiKhoan ;
    }

    private void truyenDuLieu() throws SQLException, IOException {
        setController();
        controller_DashBoard.setTaiKhoan(taiKhoan);
        controller_DashBoard.setSoDienThoai(SoDienThoai);
        controller_DashBoard.reload();
    }

    public void setNguoiDung(NguoiDung nd) {
        nguoiDung = nd;
    }

    public void setController_DashBoard() throws IOException {
        this.controller_DashBoard = scene.getLoader().getController();
    }

    private void setController() throws IOException {
        setController_DashBoard();
    }
}
