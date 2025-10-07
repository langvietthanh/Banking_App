package Model;

import java.time.LocalDateTime;

public class TaiKhoan {
    private int accountID;             // Mã tài khoản (PK)
    private int userID;                // FK đến NguoiDung
    private String soTaiKhoan;         // Số tài khoản duy nhất
    private String loaiTaiKhoan;       // "ThanhToan", "TietKiem", "TinDung"
    private double soDu;               // Số dư hiện tại
    private LocalDateTime ngayTao;     // Ngày tạo tài khoản
//    private String trangThai;          // "HoatDong", "Dong"

    // ----- Constructors -----
    public TaiKhoan() {}

    public TaiKhoan(int accountID, int userID, String soTaiKhoan, String loaiTaiKhoan,
                    double soDu, LocalDateTime ngayTao) {
        this.accountID = accountID;
        this.userID = userID;
        this.soTaiKhoan = soTaiKhoan;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.soDu = soDu;
        this.ngayTao = ngayTao;
    }

    // ----- Getters & Setters -----
	
}
