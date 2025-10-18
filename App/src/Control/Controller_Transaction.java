package Control;

import DAO.GiaoDIchDAO;
import DAO.OTPDAO;
import DAO.TaiKhoanDAO;
import Model.BaoMat;
import Model.GiaoDich;
import Model.TaiKhoan;
import View.Popup.alert;
import View.Popup.label;
import View.Popup.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Controller_Transaction {
    //DAO=====================================================================================//
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final GiaoDIchDAO giaoDichDAO = new GiaoDIchDAO();
    private final OTPDAO OtpDAO = new OTPDAO();

    //Attribute===============================================================================//
    private static TaiKhoan taiKhoanNguon;
    private static TaiKhoan taiKhoanDich;
    private static double soTienGiaoDich;
    private static String soDienThoai;

    //Controller===============================================================================//
    private Controller_KeyBoard  controller_KeyBoard;
    private Controller_DashBoard  controller_DashBoard;
    private final ManegerScene manegerScene = new ManegerScene();

    //FXML===============================================================================//
    public Label Label_TaiKhoanKhongTonTai;
    public Label Label_TenTaiKhoan;
    public Label Label_HienTenTaiKhoanDich;
    public TextField TextField_OTP;
    public Label Label_OTPHetHan;
    public Label Label_TaiKhoanDichLoi;
    public TextField TextField_TaiKhoanNhanTien;
    public TextField TextField_PIN;

    //HandleButton===============================================================================//
    public void handleXacNhanTaiKhoanDich(ActionEvent actionEvent) throws IOException, SQLException {
        String soTKDich = TextField_TaiKhoanNhanTien.getText();

        //Kiểm tra tài khoản đích tồn tại không + không trùng tài khoản gốc====================//
        int mode = GiaoDich.kiemTraNguonVaDich(taiKhoanNguon.getSoTaiKhoan(), soTKDich);
        if(mode == 0) {
            setTaiKhoanDich(taiKhoanDAO.findByAttribute("SotaiKhoan",soTKDich));
            nhapSoTien();
            if (controller_KeyBoard.getFlag()){
                manegerScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/VerifyPIN.fxml")));
                manegerScene.change(actionEvent, "Xác thực mã PIN");
            }
        }
        else xuatThongBaoLoiTKDich(mode);
    }

    public void handleXacThucMaPIN(ActionEvent actionEvent) throws Exception {
        String PIN = TextField_PIN.getText();

//====================Kiểm tra mã PIN tài khoản đã mã hóa====================//
        if(BaoMat.checkPassword(PIN,taiKhoanNguon.getMaPIN())){

//====================Gửi mã OTP====================//
            String OTP  = BaoMat.generateOTP();
            String hashed_OTP = BaoMat.sha256(OTP);
            LocalDateTime thoiGianHetHan = BaoMat.nowPlusMinutes(5);

            OtpDAO.saveOtp(soDienThoai,hashed_OTP,thoiGianHetHan);

            alert.INFORMATION("Mã OTP","Mã OTP của bạn là "+OTP);
            manegerScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/VerifyOTPTransaction.fxml")));
            manegerScene.change(actionEvent,"Xác thực OTP");

        }
        else {
            alert.ERROR("Mã PIN lỗi", "Mã PIN không hợp lệ");
            TextField_PIN.clear();
        }
    }

    public void handleXacNhanOTP(ActionEvent actionEvent) throws Exception {
        String OTP = TextField_OTP.getText();
        String hashed_OTP = BaoMat.sha256(OTP);

        if(OtpDAO.verifyOtp(soDienThoai,hashed_OTP)) thucHienGiaoDich(actionEvent);

        else alert.ERROR("Mã OTP lỗi!","Mã OTP không chính xác! \n Yêu cầu nhập lại");

    }

    //Phương thức riêng của Controller hiện tại================================================//

    private void nhapSoTien() throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller_Transaction.class.getResource("/View/KeyBoard.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Key Board");
        controller_KeyBoard = loader.getController();
        controller_KeyBoard.setTaiKhoanNguon(taiKhoanNguon);
        stage.showAndWait();
        setSoTienGiaoDich(controller_KeyBoard.getValue());
    }

    private void thucHienGiaoDich(ActionEvent actionEvent) throws SQLException, IOException {
        GiaoDich giaoDich = new GiaoDich(taiKhoanNguon.getSoTaiKhoan(), taiKhoanDich.getSoTaiKhoan(), soTienGiaoDich, "");
        giaoDich.congTienTaiKhoanDich();
        giaoDich.truTienTaiKhoanNguon(taiKhoanNguon);
        giaoDichDAO.create(giaoDich);

        if(alert.INFORMATION("Giao dịch", "Giao dịch thành công") != null){
            manegerScene.setLoader(new  FXMLLoader(getClass().getResource("/View/Main/DashBoard.fxml")));
            controller_DashBoard = manegerScene.getLoader().getController();
            controller_DashBoard.setTaiKhoan(taiKhoanNguon);
            controller_DashBoard.reload();
            manegerScene.change(actionEvent,"DashBoard");
        }
    }

    private void xuatThongBaoLoiTKDich(int modeTaiKhoanDich) {
        switch(modeTaiKhoanDich){
            case 1:
                label.ERROR(Label_TaiKhoanDichLoi,"Tài khoản không tồn tại trong hệ thống");
                break;
            case 2:
                label.ERROR(Label_TaiKhoanDichLoi,"Tài khoản không hợp lệ ");
                break;
        }
    }

    public void setTaiKhoanDich(TaiKhoan taiKhoan) {taiKhoanDich = taiKhoan;}

    public void setSoTienGiaoDich(double soTien) {
        soTienGiaoDich = soTien;
    }

    // Data lấy từ Controller trước đấy (DashBoard)==========================================================//

    public void setTaiKhoanNguon(TaiKhoan taiKhoan) {
        taiKhoanNguon = taiKhoan;
    }

    public void setSoDienThoai(String sdt) {
        soDienThoai = sdt;
    }
}
