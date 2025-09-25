package Model;

import java.time.*;

public class LichSuGiaoDich {
	private String SoTKNguon,SoTKDich,TheLoaiGD;
	private double SoTienGD,SoDu;
	private LocalDateTime ThoiGianGD;
	public LichSuGiaoDich(String soTKNguon, String soTKDich, String theLoaiGD, double soTienGD, double soDu,LocalDateTime thoiGianGD) {
		super();
		SoTKNguon = soTKNguon;
		SoTKDich = soTKDich;
		TheLoaiGD = theLoaiGD;
		SoTienGD = soTienGD;
		SoDu = soDu;
		ThoiGianGD = thoiGianGD;
	}
	
}
