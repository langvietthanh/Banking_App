package Model;

import java.time.LocalDateTime;

public class GiaoDich {
    private String maGD;                 // Mã giao dịch hiển thị (vd: TX123456)
    private String taiKhoanNguon;          // Số TK gửi
    private String taiKhoanDich;            // Số TK nhận
    private double soTien;               // Số tiền giao dịch
    private String loaiGiaoDich;         // "ChuyenTien", "RutTien", "NapTien", "NhanTien"
    private String noiDung;              // Nội dung giao dịch
    private LocalDateTime thoiGianGD;    // Thời gian thực hiện

    public GiaoDich() {}

    public GiaoDich(String maGD, String taiKhoanNguon, String taiKhoanDich,
                    double soTien,String noiDung) {
        this.maGD = maGD;
        this.taiKhoanNguon = taiKhoanNguon;
        this.taiKhoanDich = taiKhoanDich;
        this.soTien = soTien;
        this.loaiGiaoDich = "Chuyển tiền";
        this.noiDung = noiDung;
        this.thoiGianGD = LocalDateTime.now();
    }

}
