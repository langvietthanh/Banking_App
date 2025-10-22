package Control.Main;

import Control.Main.History.Controller_History;
import Control.Main.Setting.Controller_Setting;
import Control.Main.Transaction.Controller_Transaction;
import DAO.NguoiDungDAO;
import Model.GiaoDichTaiKhoanNguon;
import Model.NguoiDung;
import Model.SpareKey;
import Model.TaiKhoan;
import View.Popup.ManegerScene;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_DashBoard  {
    //DAO=============================================================================================================//
    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

    //Attribute=======================================================================================================//
    private TaiKhoan taiKhoan;
    private SpareKey spareKey = new SpareKey();
    private String soDienThoai;
    private ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich;

    //Controller and scene============================================================================================//
    private ManegerScene manegerMainScene;
    private ManegerScene manegerSubScene = new ManegerScene();
    private Controller_Transaction controller_Transaction;
    private Controller_History controller_History;
    private Controller_Setting controller_Setting;

    //FXML component==================================================================================================//
    public Label Label_SoTaiKhoan;
    public Label Label_SoDu;
    public Label Label_TenNguoiDung;

    //HandleButton===========================================================================================================//
    public void handleChuyenTien(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/Transaction/Transaction.fxml")));
        truyenDuLieuTransaction();
        manegerSubScene.changeWithOldStage(actionEvent, "Giao dịch");
    }

    public void handleLichSu(ActionEvent actionEvent) throws IOException, SQLException {
        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/History/History.fxml")));
        truyenDuLieuHistory();
        manegerSubScene.changeWithOldStage(actionEvent,"Lịch sử giao dịch");
    }

    public void handleCaiDat(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setLoader(new FXMLLoader(getClass().getResource("/View/Main/Setting/Setting.fxml")));
        truyenDuLieuSetting();
        manegerSubScene.changeWithOldStage(actionEvent,"Setting");
    }

    //Truyền data============================================================================//
    private void truyenDuLieuTransaction() throws IOException {
        setController_Transaction();
        controller_Transaction.setTaiKhoanNguon(taiKhoan);
        controller_Transaction.setSoDienThoai(soDienThoai);
        controller_Transaction.setManegerMainScene(manegerSubScene);
        controller_Transaction.setManegerSubScene(manegerMainScene);
    }

    private void truyenDuLieuHistory() throws SQLException {
        setController_History();
        controller_History.setTaiKhoanNguon(taiKhoan);
        controller_History.setTatCaGiaoDich(tatCaGiaoDich);
        controller_History.capNhatBang();
    }

    private void truyenDuLieuSetting() throws IOException {
        setController_Setting();
        spareKey.setCccd(taiKhoan.getCccd());
        controller_Setting.setSpareKey(spareKey);
        controller_Setting.setManegerMainScene(manegerSubScene);
        controller_Setting.setManegerSubScene(manegerMainScene);
    }

    //Phương thức riêng của Controller hiện tại=======================================================================//
    public void reload() throws SQLException {
        String soTaiKhoan = this.taiKhoan.getSoTaiKhoan();
        String soDu = String.valueOf(Math.round(this.taiKhoan.getSoDu()));
        Label_SoTaiKhoan.setText(soTaiKhoan);
        Label_SoDu.setText(soDu);
        NguoiDung nguoiDung = nguoiDungDAO.findByAttribute("SDT",soDienThoai);
        Label_TenNguoiDung.setText("Xin chào " + nguoiDung.getHoTen());
    }

    // Setter attribute===============================================================================================//
    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;

    }

    public void setTatCaGiaoDich(ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich) {
        this.tatCaGiaoDich = tatCaGiaoDich;
    }

    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

    // Setter controller==============================================================================================//
    public void setController_Transaction() throws IOException {
        this.controller_Transaction = manegerSubScene.getControllerOfLoader();
    }

    public void setController_History() {
        this.controller_History = manegerSubScene.getControllerOfLoader();
    }

    public void setController_Setting() throws IOException {
        this.controller_Setting = manegerSubScene.getControllerOfLoader();
    }


}
