package Model;

import java.time.LocalDateTime;

public class NguoiDung {
	private String CCCD,HoTen,SDT,Email,DiaChi,QuocTich;
	private LocalDateTime NgaySinh,HanCCCD;
	public NguoiDung(String cCCD, String hoTen, String sDT, String email, String diaChi, LocalDateTime ngaySinh,
			LocalDateTime hanCCCD, String quocTich) {
		super();
		CCCD = cCCD;
		HoTen = hoTen;
		SDT = sDT;
		Email = email;
		DiaChi = diaChi;
		NgaySinh = ngaySinh;
		HanCCCD = hanCCCD;
		QuocTich = quocTich;
	}	
	
}
