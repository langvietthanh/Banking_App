package Model;

import java.time.*;

public class LichSuGiaoDich {
	private String SoTKNguon,SoTKDich;
	private THELOAI TheLoaiGD;
	private double SoTienGD,SoDu;
	private LocalDateTime ThoiGianGD;
	public LichSuGiaoDich(String soTKNguon, String soTKDich, THELOAI theLoaiGD, double soTienGD, double soDu,
			LocalDateTime thoiGianGD) {
		super();
		SoTKNguon = soTKNguon;
		SoTKDich = soTKDich;
		TheLoaiGD = theLoaiGD;
		SoTienGD = soTienGD;
		SoDu = soDu;
		ThoiGianGD = thoiGianGD;
	}
	
}
