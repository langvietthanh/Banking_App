package Control.Login;

import DAO.NguoiDungDAO;
import DAO.TaiKhoanDAO;
import Model.NguoiDung;
import Model.SpareKey;
import Model.TaiKhoan;
import View.Popup.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_Register_Account {
    @FXML private TextField SoTaiKhoan;
    @FXML private PasswordField PIN;
    @FXML private PasswordField PIN2;
    @FXML private Label errorSoTaiKhoan;
    @FXML private Label errorPIN;
    @FXML private Label lblThongBao;

    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final TaiKhoan taiKhoan = new TaiKhoan();
    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    private final ManegerScene manegerScene = new ManegerScene();
    private NguoiDung  nguoiDung;

    private ManegerScene manegerSubScene;
    private Controller_Success controller_Success;

    public void handleRegister(ActionEvent actionEvent ) throws IOException, SQLException{

        taiKhoan.setControllerRegisterAccount(this);
        String stk = SoTaiKhoan.getText();
        String pin = PIN.getText();

        if(!taiKhoan.checkThongTin(stk,pin)) return;
        if (!taiKhoan.checkExistSTK(stk)) return;

        taiKhoan.setSoTaiKhoan(stk);
        taiKhoan.setMaPIN(pin);
        taiKhoan.setCccd(nguoiDung.getCccd());
        taiKhoanDAO.create(taiKhoan);
        nguoiDungDAO.create(nguoiDung);

        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Login/Success.fxml")));
        manegerSubScene.changeWithOldStage(actionEvent,"Thành công");
    }


    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerSubScene.changeWithOldStage(actionEvent,"Đăng kí thông tin");
    }


    public PasswordField getPIN2() {
        return PIN2;
    }

    public Label getErrorSoTaiKhoan() {
        return errorSoTaiKhoan;
    }

    public Label getErrorPIN() {
        return errorPIN;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public void setController_Success() {
        this.controller_Success = manegerSubScene.getControllerOfLoader();
    }

    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }

    public void setManegerMainScene(ManegerScene manegerSubScene) {

    }
}
