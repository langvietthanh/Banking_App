package Model;

import DAO.NguoiDungDAO;
import View.alert;

import java.sql.SQLException;
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

    public NguoiDung() {}

    public NguoiDung(String cccd, String password, String hoTen,
                     LocalDate ngaySinh, String soDienThoai,
                     String email, String diaChi, LocalDate hanCCCD) {
        this.cccd = cccd;
        this.password = password;
        this.hoTen = chuanHoaTenRieng(hoTen);
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = chuanHoaTenRieng(diaChi);
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
        this.password = BaoMat.hashPassword(password);
    }

    public String getHoTen() {
        return hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public String getCccd() {
        return cccd;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public LocalDate getHanCCCD() {
        return hanCCCD;
    }

    public LocalDateTime getNgayDangKi() {
        return ngayDangKi;
    }

    public static boolean kiemTraSDT(String sdt){
        sdt = sdt.replaceAll("\\s+", ""); // bỏ khoảng trắng
        // Hỗ trợ dạng +84xxx hoặc 0xxx
        return sdt.matches("^(\\+84|0)([35789])\\d{8}$");
    }

    public static boolean kiemTraDauVaoSDT(String sdt) throws SQLException {
        if(sdt.isEmpty()) {
            alert.ERROR("Lỗi đăng nhập!","Vui lòng nhập số điện thoại!");
            return false;
        }
        else if(!kiemTraSDT(sdt)) {
            alert.ERROR("Lỗi đăng nhập!","Định dạng số điện không phù hợp");
            return false;
        }
        else if (! new NguoiDungDAO().existObject("SDT",sdt)) {
            alert.ERROR("Lỗi đăng nhập!","Người dùng chưa tồn tại trong hệ thống");
            return false;
        }
        return true;
    }

    private String chuanHoaTenRieng(String Ten){
        String[] tu = Ten.trim().toLowerCase().split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : tu){
            if (!word.isEmpty()){
                if (word.charAt(0) == 'đ') stringBuilder.append('Đ');
                else stringBuilder.append(Character.toUpperCase(word.charAt(0)));
                stringBuilder.append(word.substring(1))
                        .append(" ");
            }
        }
        return stringBuilder.toString().trim();
    }

    public static int kiemTraMatKhau(String matKhau, String matKhauXacThuc){
        //----------Độ dài 8 (1)-------------
        if (matKhau.length()<8) return 1;

        //-----------kiểm tra định dạng mật khẩu (2) --------
        boolean hasUpper = matKhau.matches(".*[A-Z].*");
        boolean hasLower = matKhau.matches(".*[a-z].*");
        boolean hasDigit = matKhau.matches(".*[0-9].*");
        boolean hasSpecial = matKhau.matches(".*[@!%&*$#].*");
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) return 2;

        //------------kiểm tra nhập lại mật khẩu có đúng không (3)-------
        if( !matKhau.equals(matKhauXacThuc) )return 3;
        return 0;
    }

}
