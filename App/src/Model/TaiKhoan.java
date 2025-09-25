package Model;

public class TaiKhoan {
	private String SoTK,TenTK,MatKhau,TrangThaiHoatDong;
	private double SoDu;
	private int MaPIN;
	public TaiKhoan(String soTK, String tenTK, String matKhau, double soDu, String trangThaiHoatDong, int maPIN) {
		super();
		SoTK = soTK;
		TenTK = tenTK;
		MatKhau = matKhau;
		SoDu = soDu;
		TrangThaiHoatDong = trangThaiHoatDong;
		MaPIN = maPIN;
	}
	
}
