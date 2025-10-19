package Model;

import java.time.LocalDateTime;

public class GiaoDichTaiKhoanNguon {
    private LocalDateTime thoiGianGiaoDich;
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

    public double getSoTien() {
        return soTien;
    }

    public LocalDateTime getThoiGianGiaoDich() {
        return thoiGianGiaoDich;
    }
}
