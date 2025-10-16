package Control;

import DAO.NguoiDungDAO;
import DAO.OTPDAO;
import DAO.TaiKhoanDAO;
import Model.BaoMat;
import Model.GiaoDich;
import Model.NguoiDung;
import Model.TaiKhoan;
import View.Popup.alert;
import View.Popup.label;
import View.Popup.scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Controller_Transaction {
//====================Biến final lấy từ Controller đăng nhập====================//
    private final TaiKhoan taiKhoan = Controller_Login.getTaiKhoan();
    private final String soDienThoai = Controller_Login.getSoDienThoai();

    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private OTPDAO OtpDAO = new OTPDAO();

    public TextField TextField_SoTienGiaoDich;
    public TextField TextField_OTP;
    public Label Label_OTPHetHan;
    public Label Label_TaiKhoanKhongTonTai;
    public TextField TextField_TaiKhoanNhanTien;
    public TextField TextField_PIN;

    public void handleChuyenTien(ActionEvent actionEvent) throws IOException {
        String soTKDich = TextField_TaiKhoanNhanTien.getText();
        TaiKhoan taiKhoanDich = taiKhoanDAO.findByAttribute("sotaikhoan",soTKDich);


//====================Kiểm tra tài khoản đích tồn tại không + không trùng tài khoản gốc====================//
        if(taiKhoanDAO.existObject("sotaikhoan",soTKDich) && !soTKDich.equals(taiKhoan.getSoTaiKhoan())) {

//====================Sinh ra một giao dịch====================//
            GiaoDich giaoDich = new GiaoDich();


            scene.change(actionEvent,"View/Main/VerifyPIN.fxml","Xác thực mã PIN");
        }
        else {
            label.setVisible(Label_TaiKhoanKhongTonTai, true);
        }
    }

    public void handleXacThucMaPIN(ActionEvent actionEvent) throws Exception {
        String PIN = TextField_PIN.getText();

//====================Kiểm tra mã PIN tài khoản đã mã hóa====================//
        if(BaoMat.checkPassword(PIN,taiKhoan.getMaPIN())){

//====================Gửi mã OTP====================//
            String OTP  = BaoMat.generateOTP();
            String hashed_OTP = BaoMat.sha256(OTP);
            LocalDateTime thoiGianHetHan = BaoMat.nowPlusMinutes(5);
            OtpDAO.saveOtp(soDienThoai,hashed_OTP,thoiGianHetHan);

            alert.INFORMATION("Mã OTP","Mã OTP của bạn là "+OTP);

            scene.change(actionEvent,"View/Login/VerifyOTPTransaction.fxml","Xác thực OTP");

        }
        else {
            alert.ERROR("Mã PIN lỗi", "Mã PIN không hợp lệ");
            TextField_PIN.clear();
        }
    }

    public void xacNhanOTP(ActionEvent actionEvent) throws Exception {
        String OTP = TextField_OTP.getText();
        String hashed_OTP = BaoMat.sha256(OTP);

        if(OtpDAO.verifyOtp(soDienThoai,hashed_OTP)) {

        }
        else alert.ERROR("Mã OTP lỗi!","Mã OTP không chính xác! \n Yêu cầu nhập lại");

    }
}
