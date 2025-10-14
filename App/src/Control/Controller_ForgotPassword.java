package Control;

import DAO.NguoiDungDAO;
import DAO.OTPDAO;
import Model.BaoMat;
import Model.NguoiDung;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Controller_ForgotPassword {
    private static NguoiDungDAO ndd = new NguoiDungDAO();
    public static OTPDAO OtpDAO = new OTPDAO();
    public static NguoiDung nd;
    public static String SDT;

    public TextField TextField_SoDienThoai,TextField_OTP;
    public Label Label_ExpireTime;
    public PasswordField PasswordField_NhapMatKhau;
    public PasswordField PasswordField_XacThucMatKhau;

    public static void change_scene(ActionEvent actionEvent, String nameFileFXML, String sceneTitle) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Controller_ForgotPassword.class.getResource(nameFileFXML)));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(sceneTitle);
        stage.show();
    }

    public static void alert_ERROR(String Title, String Message) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setContentText(Message);
        alert.showAndWait();
    }

    public static void alert_INFORMATION(String Title, String Message) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setContentText(Message);
        alert.showAndWait();
    }

    public void Gui_Ma_Xac_Nhan(ActionEvent actionEvent) throws Exception {
        SDT = TextField_SoDienThoai.getText();
        if(Check_Input_SDT(SDT)){
//          SDT ton tai (khong can biet có phải dung cua nguoi dung không)
//          OTP chưa hash sẽ được gửi cho người dung qua sdt
            String otp = BaoMat.generateOTP();

            String otp_hash = BaoMat.sha256(otp);
            System.out.println(otp+ "\n" +  otp_hash);

            LocalDateTime expiresAt = BaoMat.nowPlusMinutes(5);
//            luu vao db
            OtpDAO.saveOtp(SDT,otp_hash,expiresAt);

            change_scene(actionEvent,"/View/VerifyOTP.fxml","Xác thực OTP");

        }
        TextField_SoDienThoai.setText("");
    }

    public void Xac_Nhan_OTP(ActionEvent actionEvent) throws Exception {
        String OTP_NguoiDung = TextField_OTP.getText();
        String Hash_OTP_NguoiDung = BaoMat.sha256(OTP_NguoiDung);
        System.out.println(Hash_OTP_NguoiDung);
        if(OtpDAO.verifyOtp(SDT,Hash_OTP_NguoiDung)) {
            change_scene(actionEvent, "/View/ResetPassword.fxml","Cấp lại mật khẩu");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mã OTP lỗi!");
            alert.setContentText("Mã OTP không chính xác! \n Yêu cầu nhập lại");
            alert.showAndWait();
        }
    }

    public void Cap_Nhat_Mat_Khau(ActionEvent actionEvent) throws Exception {
        String MatKhau =  PasswordField_NhapMatKhau.getText(),MatKhauXacThuc = PasswordField_XacThucMatKhau.getText();

        if(MatKhau.equals(MatKhauXacThuc)){
            nd = ndd.findBySDT(SDT);
            nd.setPassword(MatKhau);
            if(ndd.update(nd)) alert_INFORMATION("Thành công!", "Mật khẩu đã được thiết lập lại");
        }
        else alert_ERROR("Lỗi mật khẩu","Mật khẩu xác thực không khớp!");
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
        else if (ndd.findBySDT(sdt) == null) {
            Xuat_Thong_Bao_SDT_Khong_Ton_Tai();
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

    private static void Xuat_Thong_Bao_SDT_Khong_Ton_Tai() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi đăng nhập!");
        alert.setContentText("Người dùng chưa tồn tại trong hệ thống");
        alert.showAndWait();
    }


}
