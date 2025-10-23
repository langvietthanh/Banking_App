package Model;

public enum LoaiGiaoDich{
    ChuyenTien,
    NhanTien;
    public static LoaiGiaoDich nhanDuLieu(String string) {
        if(string.equalsIgnoreCase("Chuyển tiền")) return ChuyenTien;
        else return NhanTien;
    }

    public static String chuyenDuLieu(LoaiGiaoDich loaiGiaoDich) {
        if(loaiGiaoDich.equals(ChuyenTien)) return "Chuyển tiền";
        else return "Nhận tiền";
    }
    public static LoaiGiaoDich xacDinhLoaiGiaoDich(boolean flag) {
        return flag ? NhanTien : ChuyenTien;
    }
}
