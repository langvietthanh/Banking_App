package Control;

import Model.TaiKhoan;
import View.Popup.scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;

public class Controller_DashBoard {
    private final TaiKhoan tk = Controller_Login.getTaiKhoan();
    private final String soDienThoai = Controller_Login.getSoDienThoai();
    public Label Label_TenTaiKhoan;
    public Label Label_SoDu;

    public void handleChuyenTien(ActionEvent actionEvent) throws IOException {
        scene.change(actionEvent, "/View/Main/Transaction.fxml","Giao dịch");
    }

    public void handleLichSu(ActionEvent actionEvent) {

    }

    public void handleCaiDat(ActionEvent actionEvent) {
    }

}
