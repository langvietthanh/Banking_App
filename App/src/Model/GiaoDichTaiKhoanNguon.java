package Model;

import Control.Main.Controller_DashBoard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GiaoDichTaiKhoanNguon {
    private LocalDateTime thoiGianGiaoDich;
    private String thoiGianGiaoDichString;
    private LoaiGiaoDich loaiGiaoDich;
    private double soTien;
    private TaiKhoan taiKhoanDich;
    private String noiDung;

    public GiaoDichTaiKhoanNguon(LocalDateTime thoiGianGiaoDich, LoaiGiaoDich loaiGiaoDich, TaiKhoan taiKhoanDich, double soTien, String noiDung) {
        this.thoiGianGiaoDich = thoiGianGiaoDich;
        this.soTien = soTien;
        this.loaiGiaoDich = loaiGiaoDich;
        this.taiKhoanDich = taiKhoanDich;
        this.noiDung = noiDung;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.thoiGianGiaoDichString =  thoiGianGiaoDich.format(formatter);
    }

    public String getNoiDung() {
        return noiDung;
    }

    public TaiKhoan getTaiKhoanDich() {
        return taiKhoanDich;
    }

    public String getLoaiGiaoDich() {
        return LoaiGiaoDich.chuyenDuLieu(loaiGiaoDich);
    }

    public String getSoTien() {
        return Controller_DashBoard.chuanHoaDouble(String.valueOf(soTien));
    }

    public LocalDateTime getThoiGianGiaoDich() {
        return thoiGianGiaoDich;
    }

    public String getThoiGianGiaoDichString() {
        return thoiGianGiaoDichString;
    }
}
