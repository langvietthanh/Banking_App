package Control.Main.Setting;

import DAO.NguoiDungDAO;
import Model.NguoiDung;
import Model.SpareKey;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Controller_UserInfor  {
    @FXML private Label lblCCCD;
    @FXML private Label lblHoTen;
    @FXML private Label lblNgaySinh;
    @FXML private Label lblDiaChi;
    @FXML private Label lblSDT;
    @FXML private Label lblEmail;
    @FXML private Label lblHanCCCD;
    private SpareKey spareKey;
    private NguoiDungDAO ndd = new NguoiDungDAO();

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
    public void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main/Setting/Setting.fxml"));
            Parent root = loader.load();

            // Truyền lại thông tin người dùng cho trang trước (nếu cần)
            Controller_Setting controllerSetting = loader.getController();
            controllerSetting.setSpareKey(spareKey);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking App");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
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
