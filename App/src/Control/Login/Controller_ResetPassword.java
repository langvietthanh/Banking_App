package Control.Login;

import Control.ManegerScene;
import DAO.NguoiDungDAO;
import DAO.OTPDAO;
import Model.NguoiDung;
import Model.Password;
import View.Popup.alert;
import View.Popup.label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class Controller_ResetPassword {
    //DAO=============================================================================================================//
    private static final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    public static OTPDAO OtpDAO = new OTPDAO();

    //Attribute=======================================================================================================//
    private NguoiDung nguoiDung;
    private Password password;
    //Controller and scene============================================================================================//
    private ManegerScene manegerSubScene = new  ManegerScene();


    //FXML component==================================================================================================//
    public PasswordField PasswordField_NhapMatKhau;
    public PasswordField PasswordField_XacThucMatKhau;
    public Label Label_MatKhauLoi;
    public Label Label_MatKhauXacThucLoi;

    public void handleCapNhatMatKhau(ActionEvent actionEvent) throws Exception {
        if (kiemTraMatKhau()) {
            nguoiDung.setPassword(password.getPass());
            if (nguoiDungDAO.update(nguoiDung)) {
                alert.INFORMATION("Thành công!", "Mật khẩu đã được thiết lập lại");
                manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Login/Login.fxml")));
                manegerSubScene.changeWithOldStage(actionEvent, "");
            }
        }
    }

    //Method==========================================================================================================//
    public boolean kiemTraMatKhau(){
        password =  new Password(PasswordField_NhapMatKhau.getText());
        String matKhauXacThuc = PasswordField_XacThucMatKhau.getText();
        label.setVisible(Label_MatKhauLoi, false);
        label.setVisible(Label_MatKhauXacThucLoi, false);
        switch(password.checkPass(matKhauXacThuc)){
            case 1 :
                label.MESS(Label_MatKhauLoi,"Mật khẩu tối thiểu 8 kí tự!");
                return false;
            case 2 :
                label.MESS(Label_MatKhauLoi,"Mật khẩu phải có ít nhất 1 kí tự in hoa, 1 kí tự thường, 1 số, 1 kí tự đặc biệt!");
                return false;
            case 3 :
                label.MESS(Label_MatKhauXacThucLoi,"Mật khẩu xác thực không khớp!");
                return false;
            default:
                return true;
        }

    }

    //Set attribute===================================================================================================//
    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

}
