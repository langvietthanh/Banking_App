package Model;

import Control.Controller_Register;
import DAO.NguoiDungDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import View.Popup.alert;

import java.sql.SQLException;

public class NguoiDung {
    private String password;
    private String hoTen;
    private LocalDate ngaySinh;
    private String cccd;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private LocalDate hanCCCD;
    private LocalDateTime ngayDangKi;
    private final NguoiDungDAO ndd = new NguoiDungDAO();
    private Controller_Register controllerRegister;
    // Ngày tạo tài khoản
    public NguoiDung() {}

    public NguoiDung(String cccd, String password, String hoTen,
                     LocalDate ngaySinh, String soDienThoai,
                     String email, String diaChi, LocalDate hanCCCD) {
        this.cccd = cccd;
        this.password = password;
        this.hoTen = chuanHoaTen(hoTen);
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = chuanHoaTen(diaChi);
        this.hanCCCD = hanCCCD;
        this.ngayDangKi = LocalDateTime.now();
    }

    public NguoiDung(String cccd, String password, String hoTen,
                     LocalDate ngaySinh, String soDienThoai,
                     String email, String diaChi, LocalDate hanCCCD, LocalDateTime ngayDangKi) {
        this.cccd = cccd;
        this.password = password;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.hanCCCD = hanCCCD;
        this.ngayDangKi = ngayDangKi;
    }
    public void setControllerRegister(Controller_Register controllerRegister) {
        this.controllerRegister = controllerRegister;
    }

    public void setHoTen(String hoTen) {this.hoTen = chuanHoaTen(hoTen);}

    public void setNgaySinh(LocalDate ngaySinh) {this.ngaySinh = ngaySinh;}

    public void setCccd(String cccd) {this.cccd = cccd;}

    public void setSoDienThoai(String soDienThoai) {this.soDienThoai = soDienThoai;}

    public void setEmail(String email) {this.email = email;}

    public void setDiaChi(String diaChi) {this.diaChi = chuanHoaTen(diaChi) ;}

    public void setHanCCCD(LocalDate hanCCCD) {this.hanCCCD = hanCCCD;}

    public void setNgayDangKi(LocalDateTime ngayDangKi) {this.ngayDangKi = ngayDangKi;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BaoMat.hashPassword(password);
    }

    public String getHoTen() {
        return hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public String getCccd() {
        return cccd;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public LocalDate getHanCCCD() {
        return hanCCCD;
    }

    public LocalDateTime getNgayDangKi() {
        return ngayDangKi;
    }

    public static boolean kiemTraSDT(String sdt){
        sdt = sdt.replaceAll("\\s+", ""); // bỏ khoảng trắng
        // Hỗ trợ dạng +84xxx hoặc 0xxx
        return sdt.matches("^(\\+84|0)([35789])\\d{8}$");
    }

    public static boolean kiemTraDauVaoSDT(String sdt) throws SQLException {
        if(sdt.isEmpty()) {
            alert.ERROR("Lỗi đăng nhập!","Vui lòng nhập số điện thoại!");
            return false;
        }
        else if(!kiemTraSDT(sdt)) {
            alert.ERROR("Lỗi đăng nhập!","Định dạng số điện không phù hợp");
            return false;
        }
        else if (! new NguoiDungDAO().existObject("SDT",sdt)) {
            alert.ERROR("Lỗi đăng nhập!","Người dùng chưa tồn tại trong hệ thống");
            return false;
        }
        return true;
    }

    public static int kiemTraMatKhau(String matKhau, String matKhauXacThuc){
        //----------Độ dài 8 (1)-------------
        if (matKhau.length()<8) return 1;

        //-----------kiểm tra định dạng mật khẩu (2) --------
        boolean hasUpper = matKhau.matches(".*[A-Z].*");
        boolean hasLower = matKhau.matches(".*[a-z].*");
        boolean hasDigit = matKhau.matches(".*[0-9].*");
        boolean hasSpecial = matKhau.matches(".*[@!%&*$#].*");
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) return 2;

        //------------kiểm tra nhập lại mật khẩu có đúng không (3)-------
        if( !matKhau.equals(matKhauXacThuc) )return 3;
        return 0;
    }
    //---------Methob Thông báo lỗi-----------
    public boolean checkThongTin(String hoTen,String cccd,String sdt,String mk,String email,String diaChi,LocalDate ngaySinh,LocalDate hanCCCD){
        if(checkHoTen(hoTen)&checkCCCD(cccd)&checkSDT(sdt)&checkMK(mk)&checkEMAIL(email)&checkDiaChi(diaChi)&checkNgaySinh(ngaySinh)&checkHanCCCD(hanCCCD)) return true;
        return false;
    }
    public boolean checkExist(String cccd,String sdt,String email){
        if(checkExistCCCD(cccd)&checkExistSDT(sdt)&checkExistEMAIL(email)) return true;
        return false;
    }
    //------------check name--------------
    private boolean checkHoTen(String hoTen){
        if (!checkHoTenEmpty(hoTen)) return false;
        if (!checkHoTenRegex(hoTen)) return false;
        return true;
    }
    private boolean checkHoTenEmpty(String hoTen){
        if (hoTen.trim().isEmpty()) {
            controllerRegister.getErrorHoTen().setText("Vui lòng nhập họ và tên!");
            controllerRegister.getErrorHoTen().setVisible(true);
            controllerRegister.getErrorHoTen().setText("Vui lòng nhập họ và tên!");
            controllerRegister.getErrorHoTen().setVisible(true);
            return false;
        } else {
            controllerRegister.getErrorHoTen().setVisible(false);
        }
        return true;
    }
    private boolean checkHoTenRegex(String hoTen){
        for (char c:hoTen.toCharArray()){
            if (!Character.isLetter(c) && c != ' '){
                controllerRegister.getErrorHoTen().setText("Vui lòng nhập lại họ tên!");
                controllerRegister.getErrorHoTen().setVisible(true);
                return false;
            }
        }
        controllerRegister.getErrorHoTen().setVisible(false);
        return true;
    }
    //--------------check CCCD---------
    private boolean checkCCCD(String cccd){
        if (cccd.trim().isEmpty()) {
            controllerRegister.getErrorCCCD().setText("Vui lòng nhập căn cước công nhân!");
            controllerRegister.getErrorCCCD().setVisible(true);
            return false;
        }
        if(!cccd.matches("\\d+") || cccd.length()!=12){
            controllerRegister.getErrorCCCD().setText("Vui lòng nhập lại căn cước công dân!");
            controllerRegister.getErrorCCCD().setVisible(true);
            return false;
        }
        controllerRegister.getErrorCCCD().setVisible(false);
        return true;
    }
    private boolean checkExistCCCD(String cccd){
        boolean exist = ndd.existObject("CCCD",cccd);
        if (exist){
            controllerRegister.getErrorCCCD().setText("Căn cước công dân đã tồn tại!");
            controllerRegister.getErrorCCCD().setVisible(true);
            return false;
        }
        controllerRegister.getErrorCCCD().setVisible(false);
        return true;
    }
    //---------------check SDT------------
    private boolean checkSDT(String sdt){
        if (sdt.trim().isEmpty()) {
            controllerRegister.getErrorSDT().setText("Vui lòng nhập số điện thoại!");
            controllerRegister.getErrorSDT().setVisible(true);
            return false;
        }
        if(!sdt.matches("\\d+") || (sdt.length()!=10&&sdt.length()!=11)){
            controllerRegister.getErrorSDT().setText("Vui lòng nhập lại số điện thoại!");
            controllerRegister.getErrorSDT().setVisible(true);
            return false;
        }
        controllerRegister.getErrorSDT().setVisible(false);
        return true;
    }
    private boolean checkExistSDT(String sdt){
        boolean exist  = ndd.existObject("SDT" , sdt);
        if (exist){
            controllerRegister.getErrorSDT().setText("Số điện thoại đã tồn tại!");
            controllerRegister.getErrorSDT().setVisible(true);
            return false;
        }
        controllerRegister.getErrorSDT().setVisible(false);
        return true;
    }
    //-----------------check Mat Khau------------
    private boolean checkMK(String mk){
        //----------Độ dài 8-------------
        if (mk.length()<8){
            controllerRegister.getErrorMK().setText("Mật khẩu tối thiểu 8 kí tự!");
            controllerRegister.getErrorMK().setVisible(true);
            controllerRegister.getErrorMK2().setVisible(true);
            return false;
        }
        //-----------kiểm tra định dạng mật khẩu --------
        boolean hasUpper = mk.matches(".*[A-Z].*");
        boolean hasLower = mk.matches(".*[a-z].*");
        boolean hasDigit = mk.matches(".*[0-9].*");
        boolean hasSpecial = mk.matches(".*[@!%&*$#].*");
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial){
            controllerRegister.getErrorMK().setText("Mật khẩu phải có ít nhất 1 kí tự in hoa, 1 kí tự thường, 1 số, 1 kí tự đặc biệt!");
            controllerRegister.getErrorMK().setVisible(true);
            controllerRegister.getErrorMK2().setVisible(true);
            return false;
        }
        //------------kiểm tra nhập lại mật khẩu có đúng không-------
        if(!mk.equals(controllerRegister.getTextField_mk2().getText())){
            controllerRegister.getErrorMK().setVisible(true);
            controllerRegister.getErrorMK2().setVisible(true);
            return false;
        }
        controllerRegister.getErrorMK().setVisible(false);
        controllerRegister.getErrorMK2().setVisible(false);
        return true;
    }
    //-----------------check email---------------
    private boolean checkEMAIL(String email){
        if (email.trim().isEmpty()){
            controllerRegister.getErrorEmail().setText("Vui lòng nhập email!");
            controllerRegister.getErrorEmail().setVisible(true);
            return false;
        }
        String regex = "^[a-zA-Z0-9]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(regex)){
            controllerRegister.getErrorEmail().setText("Vui lòng nhập lại email!");
            controllerRegister.getErrorEmail().setVisible(true);
            return false;
        }
        controllerRegister.getErrorEmail().setVisible(false);
        return true;
    }
    private boolean checkExistEMAIL(String email){
        boolean exist  = ndd.existObject("Email" , email);
        if (exist){
            controllerRegister.getErrorEmail().setText("Email đã tồn tại!");
            controllerRegister.getErrorEmail().setVisible(true);
            return false;
        }
        controllerRegister.getErrorEmail().setVisible(false);
        return true;
    }
    //----------check địa chỉ -------------
    private boolean checkDiaChi(String diaChi){
        if (diaChi.trim().isEmpty()){
            controllerRegister.getErrorDiaChi().setText("Vui lòng nhập địa chỉ!");
            controllerRegister.getErrorDiaChi().setVisible(true);
            return false;
        }
        controllerRegister.getErrorDiaChi().setVisible(false);
        return true;
    }
    //----------------check ngày sinh--------------
    private boolean checkNgaySinh(LocalDate ngaySinh){
        if (ngaySinh == null){
            controllerRegister.getErrorNgaySinh().setText("Vui lòng nhập ngày sinh!");
            controllerRegister.getErrorNgaySinh().setVisible(true);
            return false;
        }
        //-------- Kiểm tra xem đủ 18 tuổi không----------
        LocalDate today = LocalDate.now();
        Period age = Period.between(ngaySinh,today);
        if (age.getYears()<18){
            controllerRegister.getErrorNgaySinh().setText("Bạn chưa đủ tuổi để tạo tài khoản!");
            controllerRegister.getErrorNgaySinh().setVisible(true);
            return false;
        }
        controllerRegister.getErrorNgaySinh().setVisible(false);
        return true;
    }
    //----------------check Hạn CCCD------------
    private boolean checkHanCCCD(LocalDate hanCCCD){
        if (hanCCCD == null){
            controllerRegister.getErrorHanCCCD().setText("Vui lòng nhập hạn căn cước công dân!");
            controllerRegister.getErrorHanCCCD().setVisible(true);
            return false;
        }
        LocalDate today = LocalDate.now();
        if (hanCCCD.isBefore(today)){
            controllerRegister.getErrorHanCCCD().setText("Căn cước công dân đã hết hạn!");
            controllerRegister.getErrorHanCCCD().setVisible(true);
            return false;
        }
        controllerRegister.getErrorHanCCCD().setVisible(false);
        return true;
    }
    public String chuanHoaTen(String hoTen){
        String[] tu = hoTen.trim().toLowerCase().split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : tu){
            if (!word.isEmpty()){
                if (word.charAt(0) == 'đ') stringBuilder.append('Đ');
                else stringBuilder.append(Character.toUpperCase(word.charAt(0)));
                stringBuilder.append(word.substring(1))
                        .append(" ");
            }
        }
        return stringBuilder.toString().trim();
    }
}
