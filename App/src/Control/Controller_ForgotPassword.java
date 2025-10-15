package Control;

import DAO.NguoiDungDAO;
import DAO.OTPDAO;
import Model.BaoMat;
import Model.NguoiDung;
import View.alert;
import View.label;
import View.scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.time.LocalDateTime;

public class Controller_ForgotPassword {
    private static NguoiDungDAO NDDAO = new NguoiDungDAO();
    public static OTPDAO OtpDAO = new OTPDAO();
    public static NguoiDung nd;
    public static String SDT;

    public TextField TextField_SoDienThoai,TextField_OTP;
    public Label Label_ExpireTime;
    public PasswordField PasswordField_NhapMatKhau;
    public PasswordField PasswordField_XacThucMatKhau;
    public Label Label_MatKhauLoi;
    public Label Label_MatKhauXacThucLoi;

    public void guiMaXacNhan(ActionEvent actionEvent) throws Exception {
        SDT = TextField_SoDienThoai.getText();
        if(NguoiDung.kiemTraDauVaoSDT(SDT)){

            String otp = BaoMat.generateOTP();

            String otp_hash = BaoMat.sha256(otp);

            alert.INFORMATION("Mã OTP","Mã OTP của bạn là "+otp);

            LocalDateTime expiresAt = BaoMat.nowPlusMinutes(5);

            OtpDAO.saveOtp(SDT,otp_hash,expiresAt);

            scene.change(actionEvent,"/View/VerifyOTP.fxml","Xác thực OTP");

        }
        TextField_SoDienThoai.setText("");
    }

    public void xacNhanOTP(ActionEvent actionEvent) throws Exception {
        String OTP_NguoiDung = TextField_OTP.getText();
        String HashedOTP_NguoiDung = BaoMat.sha256(OTP_NguoiDung);

        if(OtpDAO.verifyOtp(SDT,HashedOTP_NguoiDung)) {
            scene.change(actionEvent, "/View/ResetPassword.fxml","Cấp lại mật khẩu");
        }
        else alert.ERROR("Mã OTP lỗi!","Mã OTP không chính xác! \n Yêu cầu nhập lại");
    }

    public void capNhatMatKhau(ActionEvent actionEvent) throws Exception {
        String matKhau =  PasswordField_NhapMatKhau.getText()
                , matKhauXacThuc = PasswordField_XacThucMatKhau.getText();
        int mode = NguoiDung.kiemTraMatKhau(matKhau,matKhauXacThuc);

        if(mode != 0) xuatThongTinLoi(mode);

        else{
            label.setVisible(Label_MatKhauLoi,false);
            label.setVisible(Label_MatKhauXacThucLoi,false);
            nd = NDDAO.findByAttribute("SDT",SDT);
            nd.setPassword(matKhau);
            if(NDDAO.update(nd)) {
                alert.INFORMATION("Thành công!", "Mật khẩu đã được thiết lập lại");
                scene.change(actionEvent,"/View/Login.fxml","");
            }
        }
    }

    public void xuatThongTinLoi (int mode){
        if(mode == 1) label.ERROR(Label_MatKhauLoi,"Mật khẩu tối thiểu 8 kí tự!");
        else if(mode == 2) label.ERROR(Label_MatKhauLoi,"Mật khẩu phải có ít nhất 1 kí tự in hoa, 1 kí tự thường, 1 số, 1 kí tự đặc biệt!");
        else if(mode == 3) label.ERROR(Label_MatKhauXacThucLoi,"Mật khẩu xác thực không khớp!");
    }
}
