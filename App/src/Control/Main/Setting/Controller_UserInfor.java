package Control.Main.Setting;

import DAO.NguoiDungDAO;
import Model.NguoiDung;
import Model.SpareKey;
import View.Popup.ManegerScene;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Controller_UserInfor  {
    private  ManegerScene manegerMainScene,manegerSubScene = new ManegerScene();

    private Controller_Setting  controller_Setting;

    @FXML private Label lblCCCD;
    @FXML private Label lblHoTen;
    @FXML private Label lblNgaySinh;
    @FXML private Label lblDiaChi;
    @FXML private Label lblSDT;
    @FXML private Label lblEmail;
    @FXML private Label lblHanCCCD;
    private SpareKey spareKey;
    private NguoiDungDAO ndd = new NguoiDungDAO();

    //Event===========================================================================================================//

    public void handleBack(ActionEvent actionEvent) throws IOException {
        //không cần setloader vì manegerScene đang quản lí Controller của Setting
        truyenDuLieuSetting();
        manegerSubScene.changeWithOldStage(actionEvent,"Setting");
    }

    //Truyền data cho controller tiếp theo============================================================================//

    private void truyenDuLieuSetting() {
        setController_Setting();
        controller_Setting.setSpareKey(spareKey);
    }

    //Phương thức riêng của Controller hiện tại=======================================================================//

    private void loadUserInfo()  {
        try {
            NguoiDung nguoiDung = ndd.findByAttribute("CCCD", spareKey.getCccd());
            lblCCCD.setText(nguoiDung.getCccd());
            lblHoTen.setText(nguoiDung.getHoTen());
            lblEmail.setText(nguoiDung.getEmail());
            lblNgaySinh.setText(nguoiDung.getNgaySinh().toString());
            lblDiaChi.setText(nguoiDung.getDiaChi());
            lblSDT.setText(nguoiDung.getSoDienThoai());
            lblHanCCCD.setText(nguoiDung.getHanCCCD().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Set Controller==================================================================================================//

    public void setController_Setting() {
        this.controller_Setting = manegerSubScene.getControllerOfLoader();
    }

    public void setManegerMainScene(ManegerScene manegerScene) {
        this.manegerMainScene = manegerScene;
    }

    public void setManegerSubScene(ManegerScene manegerSubScene) {
        this.manegerSubScene = manegerSubScene;
    }

    //Set Attribute - Data lấy từ Controller trước đấy (Setting)==========================================================//

    public void setSpareKey(SpareKey spareKey) {
        this.spareKey = spareKey;
        if (spareKey != null) {
            try {
                loadUserInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
