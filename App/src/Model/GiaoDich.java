package Model;

enum THELOAI{
	
}

public class GiaoDich {
	private String MaGD,NoiDungGD,SoTKNguon,SoTKDich;
	private double SoTienGD;
	private THELOAI TheLoaiGD;
	public GiaoDich(String maGD, String noiDungGD, String soTKNguon, String soTKDich, double soTienGD,
			THELOAI theLoaiGD) {
		super();
		MaGD = maGD;
		NoiDungGD = noiDungGD;
		SoTKNguon = soTKNguon;
		SoTKDich = soTKDich;
		SoTienGD = soTienGD;
		TheLoaiGD = theLoaiGD;
	}
	
}
