package Model;

import Control.Controller_Register_Account;
import DAO.TaiKhoanDAO;
import View.Popup.label;

import java.time.LocalDateTime;

import static Control.Controller_Login.taiKhoanDAO;

public class TaiKhoan {
    private String cccd;
    private String maPIN;
    private String soTaiKhoan;
    private String loaiTaiKhoan;
    private long soDu;
    private LocalDateTime ngayTao;

    private Controller_Register_Account controllerRegisterAccount;

    private final TaiKhoanDAO tkd =  new TaiKhoanDAO();
    // ----- Constructors -----
    public TaiKhoan() {
        this.ngayTao = LocalDateTime.now();
    }

    public TaiKhoan(String soTaiKhoan, String maPIN, LocalDateTime ngayTao) {
        this.soTaiKhoan = soTaiKhoan;
        this.maPIN = maPIN;
        this.soDu = 0;
        this.ngayTao = ngayTao;
    }

    public TaiKhoan(String cccd, String maPIN, String soTaiKhoan, String loaiTaiKhoan, long soDu, LocalDateTime ngayTao) {
        this.cccd = cccd;
        this.maPIN = maPIN;
        this.soTaiKhoan = soTaiKhoan;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.soDu = soDu;
        this.ngayTao = ngayTao;
    }

    public int kiemTraSoDu(double soTienGiaoDich) {
        if (soDu < soTienGiaoDich) return 1;
        else if(soTienGiaoDich < soDu && soDu - soTienGiaoDich < 50) return 2;
        return 0;
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
        this.maPIN = BaoMat.hashPassword(maPIN);
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
        return checkThongTinSTK(stk) & checkThongTinPIN(pin);
    }
    private boolean checkThongTinSTK(String stk){

        if (stk.trim().isEmpty()){
            label.ERROR(controllerRegisterAccount.getErrorSoTaiKhoan(),"Vui Lòng nhập số tài khoản!");
            return false;
        }
        if (stk.trim().length()<10||stk.trim().length()>14){
            label.ERROR(controllerRegisterAccount.getErrorSoTaiKhoan(),"Vui lòng nhập lại số tài khoản!");
            return false;
        }
        if (!stk.matches("[0-9]+")){
            label.ERROR(controllerRegisterAccount.getErrorSoTaiKhoan(),"Vui lòng nhập lại số tài khoản!");
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
        if (tkd.existObject("SoTaiKhoan",stk)){
            controllerRegisterAccount.getErrorSoTaiKhoan().setText("Số tài khoản đã tồn tại!");
            controllerRegisterAccount.getErrorSoTaiKhoan().setVisible(true);
            return false;
        }
        controllerRegisterAccount.getErrorSoTaiKhoan().setVisible(false);
      return true;
    }
}
