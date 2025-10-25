package Control.Main.Setting;

import Model.NguoiDung;
import Control.ManegerScene;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Controller_UserInfor  {
    //Controller and scene===============================================================================//
    private  ManegerScene manegerMainScene;

    //Attribute===============================================================================//
    private NguoiDung nguoiDung;

    //FXML component===============================================================================//
    @FXML private Label lblCCCD;
    @FXML private Label lblHoTen;
    @FXML private Label lblNgaySinh;
    @FXML private Label lblDiaChi;
    @FXML private Label lblSDT;
    @FXML private Label lblEmail;
    @FXML private Label lblHanCCCD;

    //Event===========================================================================================================//
    public void handleBack(ActionEvent actionEvent) throws IOException {
        manegerMainScene.back(actionEvent);
    }

    //Method==========================================================================================================//
    private void loadInfor() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblCCCD.setText(nguoiDung.getCccd());
        lblHoTen.setText(nguoiDung.getHoTen());
        lblEmail.setText(nguoiDung.getEmail());
        lblNgaySinh.setText(nguoiDung.getNgaySinh().format(dtf));
        lblDiaChi.setText(nguoiDung.getDiaChi());
        lblSDT.setText(nguoiDung.getSoDienThoai());
        lblHanCCCD.setText(nguoiDung.getHanCCCD().format(dtf));
    }

    //Set attribute===================================================================================================//
    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
        loadInfor();
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }
}
