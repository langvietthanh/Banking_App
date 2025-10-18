package Control;

import Model.TaiKhoan;
import View.Popup.ManegerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class Controller_DashBoard {
    //Attribute===============================================================================//
    private TaiKhoan taiKhoan;
    private String soDienThoai;

    private final ManegerScene manegerScene = new ManegerScene();
    private Controller_Transaction controller_Transaction;

    public Label Label_SoTaiKhoan;
    public Label Label_SoDu;

    public void handleChuyenTien(ActionEvent actionEvent) throws IOException {
        manegerScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/Transaction.fxml")));
        truyenDuLieu();
        manegerScene.change(actionEvent, "Giao dịch");
    }

    public void handleLichSu(ActionEvent actionEvent) {

    }

    public void handleCaiDat(ActionEvent actionEvent) {
    }

    //Truyền data cho controller con===============================================================================//

    private void truyenDuLieu() throws IOException {
        setController_Transaction();
        controller_Transaction.setTaiKhoanNguon(taiKhoan);
        controller_Transaction.setSoDienThoai(soDienThoai);
    }

    //Phương thức riêng của Controller hiện tại================================================//

    public void reload(){
        String soTaiKhoan = this.taiKhoan.getSoTaiKhoan();
        String soDu = String.valueOf(Math.round(this.taiKhoan.getSoDu()));
        Label_SoTaiKhoan.setText(soTaiKhoan);
        Label_SoDu.setText(soDu);
    }

    public void setController_Transaction() throws IOException {
        this.controller_Transaction = manegerScene.getLoader().getController();
    }

    // Data lấy từ Controller trước đấy (Login)==========================================================//

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;

    }
}
