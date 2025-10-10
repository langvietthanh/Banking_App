package Model;

import java.time.LocalDateTime;

public class TaiKhoan {
    private String soTaiKhoan;         // Số tài khoản duy nhất
    private long soDu;               // Số dư hiện tại
    private String maPIN;
    private LocalDateTime ngayTao;     // Ngày tạo tài khoản
//    private String trangThai;          // "HoatDong", "Dong"

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
}
