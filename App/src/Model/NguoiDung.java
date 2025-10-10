package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
//CCCD, HoTen, NgaySinh,QuocTich, SDT, Email,HanCCCD, DiaChi
public class NguoiDung {
    private Long userID;              // Mã người dùng (PK)
    private String tenDangNhap;         // Tên đăng nhập
    private String password;         // Mật khẩu
    private String hoTen;            // Họ tên đầy đủ
    private LocalDate ngaySinh;      // Ngày sinh
    private String cccd;             // Số CCCD
    private String soDienThoai;      // Số điện thoại
    private String email;            // Email
    private String diaChi;           // Địa chỉ
    private LocalDate hanCCCD;
    private LocalDateTime ngayDangKi;   
    // Ngày tạo tài khoản
    public NguoiDung() {}

    public NguoiDung(String password, String hoTen,
                     LocalDate ngaySinh, String cccd, String soDienThoai,
                     String email, String diaChi, LocalDate hanCCCD, LocalDateTime ngayDangKi) {
        this.tenDangNhap = soDienThoai;
        this.password = password;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.cccd = cccd;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.hanCCCD = hanCCCD;
        this.ngayDangKi = ngayDangKi;
    }
    // ----- Constructors -----
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public LocalDate getHanCCCD() {
        return hanCCCD;
    }

    public void setHanCCCD(LocalDate hanCCCD) {
        this.hanCCCD = hanCCCD;
    }

    public LocalDateTime getNgayDangKi() {
        return ngayDangKi;
    }

    public void setNgayDangKi(LocalDateTime ngayDangKi) {
        this.ngayDangKi = ngayDangKi;
    }

}
