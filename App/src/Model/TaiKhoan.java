package Model;

import Control.Controller_Register_Account;
import DAO.NguoiDungDAO;

import java.time.LocalDateTime;

public class TaiKhoan {
    private String soTaiKhoan;         // Số tài khoản duy nhất
    private long soDu;               // Số dư hiện tại
    private String maPIN;
    private LocalDateTime ngayTao;   // Ngày tạo tài khoản
    private String cccd;
//    private String trangThai;          // "HoatDong", "Dong"

    private Controller_Register_Account controllerRegisterAccount;
    private NguoiDungDAO ndd = new NguoiDungDAO();
    // ----- Constructors -----
    public TaiKhoan() {}

    public TaiKhoan(String soTaiKhoan, String maPIN, LocalDateTime ngayTao) {
        this.soTaiKhoan = soTaiKhoan;
        this.maPIN = maPIN;
        this.soDu = 0;
        this.ngayTao = ngayTao;
    }

    // ----- Getters & Setters -----

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getMaPIN() {
        return maPIN;
    }

    public void setMaPIN(String maPIN) {
        this.maPIN = maPIN;
    }

    public long getSoDu() {
        return soDu;
    }

    public void setSoDu(long soDu) {
        this.soDu = soDu;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setControllerRegisterAccount(Controller_Register_Account controllerRegisterAccount) {
        this.controllerRegisterAccount = controllerRegisterAccount;
    }

    //    -------------- Kiểm Tra Lỗi cho phần đăng kí------------
    public boolean checkThongTin(String stk,String pin){
        if (checkThongTinSTK(stk)&checkThongTinPIN(pin)) return true;
       return false;
    }
    private boolean checkThongTinSTK(String stk){
        if (stk.trim().isEmpty()){
            controllerRegisterAccount.getErrorSoTaiKhoan().setText("Vui Lòng nhập số tài khoản!");
            controllerRegisterAccount.getErrorSoTaiKhoan().setVisible(true);
            return false;
        }
        if (stk.trim().length()<10||stk.trim().length()>14){
            controllerRegisterAccount.getErrorSoTaiKhoan().setText("Vui lòng nhập lại số tài khoản!");
            controllerRegisterAccount.getErrorSoTaiKhoan().setVisible(true);
            return false;
        }
        if (!stk.matches("[0-9]+")){
            controllerRegisterAccount.getErrorSoTaiKhoan().setText("Vui lòng nhập lại số tài khoản!");
            controllerRegisterAccount.getErrorSoTaiKhoan().setVisible(true);
            return false;
        }
        controllerRegisterAccount.getErrorSoTaiKhoan().setVisible(false);
        return true;
    }
    private boolean checkThongTinPIN(String pin){
        if (pin.trim().isEmpty()){
            controllerRegisterAccount.getErrorPIN().setText("Vui lòng nhập mã PIN!");
            controllerRegisterAccount.getErrorPIN().setVisible(true);
            return false;
        }
        if (pin.length()!=6){
            controllerRegisterAccount.getErrorPIN().setText("Vui lòng nhập lại mã PIN");
            controllerRegisterAccount.getErrorPIN().setVisible(true);
            return false;
        }
        if (!pin.matches("[0-9]+")){
            controllerRegisterAccount.getErrorPIN().setText("Vui lòng nhập lại mã PIN");
            controllerRegisterAccount.getErrorPIN().setVisible(true);
            return false;
        }
        if (!pin.equals(controllerRegisterAccount.getPIN2().getText())){
            controllerRegisterAccount.getErrorPIN().setText("Mã PIN không trùng nhau!");
            controllerRegisterAccount.getErrorPIN().setVisible(true);
            return false;
        }
        controllerRegisterAccount.getErrorPIN().setVisible(false);
        return true;
    }
    public boolean checkExistSTK(String stk){
        if (ndd.existObject("SoTK",stk)){
            controllerRegisterAccount.getErrorSoTaiKhoan().setText("Số tài khoản đã tồn tại!");
            controllerRegisterAccount.getErrorSoTaiKhoan().setVisible(true);
            return false;
        }
        controllerRegisterAccount.getErrorSoTaiKhoan().setVisible(false);
      return true;
    }
}
