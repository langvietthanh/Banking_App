package Model;

import DAO.TaiKhoanDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static Control.Controller_Login.taiKhoanDAO;

public class GiaoDich {
    private String maGD;                 // Mã giao dịch hiển thị (vd: TX123456)
    private String soTaiKhoanNguon;          // Số TK gửi
    private String soTaiKhoanDich;            // Số TK nhận
    private double soTien;               // Số tiền giao dịch
    private LocalDateTime thoiGianGD;
    private String noiDung;              // Nội dung giao dịch
       // Thời gian thực hiện

    public GiaoDich() {}

    public GiaoDich(String soTaiKhoanNguon, String soTaiKhoanDich, double soTien, String noiDung) throws SQLException {
        this.soTaiKhoanNguon = soTaiKhoanNguon;
        this.soTaiKhoanDich = soTaiKhoanDich;
        this.soTien = soTien;
        this.thoiGianGD = LocalDateTime.now();
        this.maGD = taoMaGiaoDich();
        this.noiDung = noiDung;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public LocalDateTime getThoiGianGD() {
        return thoiGianGD;
    }

    public double getSoTien() {
        return soTien;
    }

    public String getSoTaiKhoanDich() {
        return soTaiKhoanDich;
    }

    public String getSoTaiKhoanNguon() {
        return soTaiKhoanNguon;
    }

    public String getMaGD() {
        return maGD;
    }

    public String taoMaGiaoDich() {
        String timePart = thoiGianGD.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        String randomPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "PTIT" + timePart + randomPart;
    }

    public void truTienTaiKhoanNguon(TaiKhoan taiKhoanNguon) throws SQLException {
        taiKhoanNguon.setSoDu(taiKhoanNguon.getSoDu() - (long) soTien);
        TaiKhoanDAO.truTienTaiKhoan(soTien,soTaiKhoanNguon);
    }

    public void congTienTaiKhoanDich() throws SQLException {
        TaiKhoanDAO.congTienTaiKhoan(soTien, soTaiKhoanDich);
    }

    public static int kiemTraNguonVaDich(String soTaiKhoanNguon, String soTaiKhoanDich){
        if (!taiKhoanDAO.existObject("sotaikhoan",soTaiKhoanDich)) return 1;
        if (soTaiKhoanDich.equals(soTaiKhoanNguon)) return 2;
        return 0;
    }
}
