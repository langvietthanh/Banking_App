package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NguoiDung {
    private String password;
    private String hoTen;
    private LocalDate ngaySinh;
    private String cccd;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private LocalDate hanCCCD;
    private LocalDateTime ngayDangKi;   
    // Ngày tạo tài khoản
    public NguoiDung() {}

    public NguoiDung(String cccd, String password, String hoTen,
                     LocalDate ngaySinh, String soDienThoai,
                     String email, String diaChi, LocalDate hanCCCD) {
        this.cccd = cccd;
        this.password = password;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.hanCCCD = hanCCCD;
        this.ngayDangKi = LocalDateTime.now();
    }


    public NguoiDung(String cccd, String password, String hoTen,
                     LocalDate ngaySinh, String soDienThoai,
                     String email, String diaChi, LocalDate hanCCCD, LocalDateTime ngayDangKi) {
        this.cccd = cccd;
        this.password = password;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.hanCCCD = hanCCCD;
        this.ngayDangKi = ngayDangKi;
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

    public static boolean Kiem_Tra_SDT(String sdt){
        sdt = sdt.replaceAll("\\s+", ""); // bỏ khoảng trắng
        // Hỗ trợ dạng +84xxx hoặc 0xxx
        return sdt.matches("^(\\+84|0)(3|5|7|8|9)\\d{8}$");
    }

}
