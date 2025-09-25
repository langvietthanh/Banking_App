package Model;

enum TRANGTHAI{
	
}

public class TaiKhoan {
	private String SoTK,TenTK,MatKhau;
	private double SoDu;
	private TRANGTHAI TrangThaiHoatDong;
	private int MaPIN;
	public TaiKhoan(String soTK, String tenTK, String matKhau, double soDu, TRANGTHAI trangThaiHoatDong, int maPIN) {
		super();
		SoTK = soTK;
		TenTK = tenTK;
		MatKhau = matKhau;
		SoDu = soDu;
		TrangThaiHoatDong = trangThaiHoatDong;
		MaPIN = maPIN;
	}
	
}
