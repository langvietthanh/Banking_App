package Control.Main;

import Control.Main.History.Controller_History;
import Control.Main.Setting.Controller_Setting;
import Control.Main.Transaction.Controller_Transaction;
import DAO.NguoiDungDAO;
import Model.GiaoDichTaiKhoanNguon;
import Model.NguoiDung;
import Model.SpareKey;
import Model.TaiKhoan;
import Control.ManegerScene;
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
    private static NguoiDung nguoiDung;
    private TaiKhoan taiKhoan;
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


    //HandleButton====================================================================================================//
    public void handleChuyenTien(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/Transaction/Transaction.fxml")));
        truyenDuLieuTransaction();
        manegerSubScene.changeWithOldStage(actionEvent, "Giao dịch");
    }

    public void handleLichSu(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/History/History.fxml")));
        truyenDuLieuHistory();
        manegerSubScene.changeWithOldStage(actionEvent,"Lịch sử giao dịch");
    }

    public void handleCaiDat(ActionEvent actionEvent) throws IOException {
        manegerSubScene.setCurrentLoader(new FXMLLoader(getClass().getResource("/View/Main/Setting/Setting.fxml")));
        truyenDuLieuSetting();
        manegerSubScene.changeWithOldStage(actionEvent,"Setting");
    }


    //Truyen du lieu==================================================================================================//
    private void truyenDuLieuTransaction() throws IOException {
        setController_Transaction();
        manegerSubScene.setBackLoarder(manegerMainScene.getCurrentLoader());
        controller_Transaction.setManegerMainScene(manegerSubScene);
        controller_Transaction.setTaiKhoanNguon(taiKhoan);
        controller_Transaction.setSoDienThoai(soDienThoai);
        controller_Transaction.setTatCaGiaoDich(tatCaGiaoDich);
    }

    private void truyenDuLieuHistory(){
        setController_History();
        controller_History.setListGiaoDichTaiKhoanNguon(tatCaGiaoDich);
        controller_History.setFilterGiaoDich(tatCaGiaoDich);
        controller_History.capNhatBang();
        controller_History.setManegerSubScene(manegerMainScene);
    }

    private void truyenDuLieuSetting() {
        setController_Setting();
        controller_Setting.setTaiKhoan(taiKhoan);
        controller_Setting.setNguoiDung(nguoiDung);
        manegerSubScene.setBackLoarder(manegerMainScene.getCurrentLoader());
        controller_Setting.setManegerMainScene(manegerSubScene);
    }

    //Method==========================================================================================================//
    public void reload() throws SQLException {
        String soTaiKhoan = taiKhoan.getSoTaiKhoan();
        String soDu = String.valueOf(Math.round(taiKhoan.getSoDu()));
        Label_SoTaiKhoan.setText(soTaiKhoan);
        Label_SoDu.setText(chuanHoaDouble(soDu));
        Label_TenNguoiDung.setText("Xin chào " + nguoiDung.getHoTen());
    }

    public static String chuanHoaDouble(String num) {
        // Xóa dấu chấm cũ
        String input = num;
        input = input.replaceAll("\\.", "");
        if (input.isEmpty()) return "0.0";

        // Nếu có phần thập phân
        String integerPart = input;

        // Đảo chuỗi để dễ nhóm
        StringBuilder sb = new StringBuilder(integerPart).reverse();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sb.length(); i++) {
            if (i > 0 && i % 3 == 0) result.append(".");
            result.append(sb.charAt(i));
        }
        num = (result.reverse().toString());
        return num;
    }

    //Setter attribute================================================================================================//
    public void setManegerMainScene(ManegerScene manegerMainScene) {
        this.manegerMainScene = manegerMainScene;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;

    }

    public void setTatCaGiaoDich(ObservableList<GiaoDichTaiKhoanNguon> tatCaGiaoDich) {
        this.tatCaGiaoDich = tatCaGiaoDich;
    }

    public void setNguoiDung(NguoiDung nguoidung) {
        nguoiDung = nguoidung;
    }

    //Setter controller===============================================================================================//
    public void setController_Transaction()   {
        this.controller_Transaction = manegerSubScene.getControllerOfLoader();
    }

    public void setController_History() {
        this.controller_History = manegerSubScene.getControllerOfLoader();
    }

    public void setController_Setting()   {
        this.controller_Setting = manegerSubScene.getControllerOfLoader();
    }


}
