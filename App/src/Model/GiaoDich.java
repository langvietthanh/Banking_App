package Model;

import DAO.TaiKhoanDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static Control.Login.Controller_Login.taiKhoanDAO;


public class GiaoDich {
    private String maGD;
    private String soTaiKhoanNguon;
    private String soTaiKhoanDich;
    private double soTien;
    private LoaiGiaoDich loaiGiaoDich;
    private LocalDateTime thoiGianGiaoDich;
    private String noiDung;

    public GiaoDich() {}

    public GiaoDich(String maGD, String soTaiKhoanNguon, String soTaiKhoanDich, double soTien, LoaiGiaoDich loaiGiaoDich, LocalDateTime thoiGianGiaoDich, String noiDung) {
        this.maGD = maGD;
        this.soTaiKhoanNguon = soTaiKhoanNguon;
        this.soTaiKhoanDich = soTaiKhoanDich;
        this.soTien = soTien;
        this.loaiGiaoDich = loaiGiaoDich;
        this.thoiGianGiaoDich = thoiGianGiaoDich;
        this.noiDung = noiDung;
    }

    public GiaoDich(String soTaiKhoanNguon, String soTaiKhoanDich, double soTien, String noiDung) throws SQLException {
        this.soTaiKhoanNguon = soTaiKhoanNguon;
        this.soTaiKhoanDich = soTaiKhoanDich;
        this.soTien = soTien;
        this.thoiGianGiaoDich = LocalDateTime.now();
        this.maGD = taoMaGiaoDich();
        this.noiDung = noiDung;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public LocalDateTime getThoiGianGiaoDich() {
        return thoiGianGiaoDich;
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

    public String getLoaiGiaoDich() {
        return loaiGiaoDich.toString();
    }

    public <T> void setLoaiGiaoDich(T loaiGiaoDich) {
        try{
            this.loaiGiaoDich = (LoaiGiaoDich) loaiGiaoDich;
        }
        catch(Exception e){
            this.loaiGiaoDich = LoaiGiaoDich.nhanDuLieu(loaiGiaoDich.toString());
        }
    }

    public String taoMaGiaoDich() {
        String timePart = thoiGianGiaoDich.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
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
