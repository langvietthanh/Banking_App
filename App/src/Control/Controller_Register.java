package Control;

import Model.NguoiDung;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import DAO.NguoiDungDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.lang.String;
import java.time.Period;
import java.util.concurrent.ScheduledExecutorService;

public class Controller_Register {
    @FXML private TextField textField_hoten;
    @FXML private TextField textField_cccd;
    @FXML private TextField textField_sdt;
    @FXML private TextField textField_mk;
    @FXML private TextField textField_mk2;
    @FXML private TextField textField_email;
    @FXML private TextField textField_diaChi;
    @FXML private DatePicker datePicker_ngaySinh;
    @FXML private DatePicker datePicker_hanCCCD;

    @FXML private Label errorHoTen;
    @FXML private Label errorCCCD;
    @FXML private Label errorSDT;
    @FXML private Label errorMK;
    @FXML private Label errorMK2;
    @FXML private Label errorDiaChi;
    @FXML private Label errorEmail;
    @FXML private Label errorNgaySinh;
    @FXML private Label errorHanCCCD;

    private final NguoiDungDAO ndd = new NguoiDungDAO();
    private boolean flag = true;

    public void handleRegister(ActionEvent actionEvent ) throws IOException, SQLException {
        flag = true;
        String hoTen = textField_hoten.getText();
        String cccd = textField_cccd.getText();
        String sdt = textField_sdt.getText();
        String passWord = textField_mk.getText();
        String email = textField_email.getText();
        String diaChi = textField_diaChi.getText();
        LocalDate ngaySinh = datePicker_ngaySinh.getValue();
        LocalDate hanCCCD = datePicker_hanCCCD.getValue();

        checkThongTin(hoTen,cccd,sdt,passWord,email,diaChi,ngaySinh,hanCCCD);
        if (!flag) return;
        checkExist(cccd,sdt,email);
        if (!flag) return;
        hoTen = chuanHoaTen(hoTen);
        diaChi = chuanHoaTen(diaChi);
        NguoiDung nd = new NguoiDung(cccd,passWord,hoTen,ngaySinh,sdt,email,diaChi,hanCCCD);
        ndd.create(nd);
    }
    //---------Methob Thông báo lỗi-----------
    private void checkThongTin(String hoTen,String cccd,String sdt,String mk,String email,String diaChi,LocalDate ngaySinh,LocalDate hanCCCD){
        checkHoTen(hoTen);
        checkCCCD(cccd);
        checkSDT(sdt);
        checkMK(mk);
        checkEMAIL(email);
        checkDiaChi(diaChi);
        checkNgaySinh(ngaySinh);
        checkHanCCCD(hanCCCD);
    }

    private void checkExist(String cccd,String sdt,String email){
        checkExistCCCD(cccd);
        checkExistSDT(sdt);
        checkExistEMAIL(email);
    }
    //------------check name--------------
    private void checkHoTen(String hoTen){
        checkHoTenEmpty(hoTen);
        if (!flag) return;
        checkHoTenRegex(hoTen);
        if (!flag) return;
    }
    private void checkHoTenEmpty(String hoTen){
        if (hoTen.trim().isEmpty()) {
            errorHoTen.setText("Vui lòng nhập họ và tên!");
            errorHoTen.setVisible(true);
            errorHoTen.setText("Vui lòng nhập họ và tên!");
            errorHoTen.setVisible(true);
            flag = false;
        } else {
            errorHoTen.setVisible(false);
        }
    }
    private void checkHoTenRegex(String hoTen){
        for (char c:hoTen.toCharArray()){
            if (!Character.isLetter(c) && c != ' '){
                errorHoTen.setText("Vui lòng nhập lại họ tên!");
                errorHoTen.setVisible(true);
                flag = false;
                return;
            }
        }
        errorHoTen.setVisible(false);
    }
    //--------------check CCCD---------
    private void checkCCCD(String cccd){
        if (cccd.trim().isEmpty()) {
            errorCCCD.setText("Vui lòng nhập căn cước công nhân!");
            errorCCCD.setVisible(true);
            flag = false;
            return;
        }
        if(!cccd.matches("\\d+") || cccd.length()!=12){
            errorCCCD.setText("Vui lòng nhập lại căn cước công dân!");
            errorCCCD.setVisible(true);
            flag = false;
            return;
        }
        errorCCCD.setVisible(false);
    }
    private void checkExistCCCD(String cccd){
        boolean exist = ndd.existAttribute("CCCD",cccd);
        if (exist){
            errorCCCD.setText("Căn cước công dân đã tồn tại!");
            errorCCCD.setVisible(true);
            flag = false;
            return;
        }
        errorCCCD.setVisible(false);
    }
    //---------------check SDT------------
    private void checkSDT(String sdt){
        if (sdt.trim().isEmpty()) {
            errorSDT.setText("Vui lòng nhập số điện thoại!");
            errorSDT.setVisible(true);
            flag = false;
            return;
        }
        if(!sdt.matches("\\d+") || sdt.length()!=11){
            errorSDT.setText("Vui lòng nhập lại số điện thoại!");
            errorSDT.setVisible(true);
            flag = false;
            return;
        }
        errorSDT.setVisible(false);
    }
    private void checkExistSDT(String sdt){
        boolean exist  = ndd.existAttribute("SDT" , sdt);
        if (exist){
            errorSDT.setText("Số điện thoại đã tồn tại!");
            errorSDT.setVisible(true);
            flag = false;
            return;
        }
        errorSDT.setVisible(false);
    }
    //-----------------check Mat Khau------------
    private void checkMK(String mk){
        //----------Độ dài 8-------------
        if (mk.length()<8){
            errorMK.setText("Mật khẩu tối thiểu 8 kí tự!");
            errorMK.setVisible(true);
            errorMK2.setVisible(true);
            flag = false;
            return;
        }
        //-----------kiểm tra định dạng mật khẩu --------
        boolean hasUpper = mk.matches(".*[A-Z].*");
        boolean hasLower = mk.matches(".*[a-z].*");
        boolean hasDigit = mk.matches(".*[0-9].*");
        boolean hasSpecial = mk.matches(".*[@!%&*$#].*");
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial){
            errorMK.setText("Mật khẩu phải có ít nhất 1 kí tự in hoa, 1 kí tự thường, 1 số, 1 kí tự đặc biệt!");
            errorMK.setVisible(true);
            errorMK2.setVisible(true);
            flag = false;
            return;
        }
        //------------kiểm tra nhập lại mật khẩu có đúng không-------
        if(!mk.equals(textField_mk2.getText())){
            errorMK.setVisible(true);
            errorMK2.setVisible(true);
            flag = false;
            return;
        }
        errorMK.setVisible(false);
        errorMK2.setVisible(false);
    }
    //-----------------check email---------------
    private void checkEMAIL(String email){
        if (email.trim().isEmpty()){
            errorEmail.setText("Vui lòng nhập email!");
            errorEmail.setVisible(true);
            flag = false;
            return;
        }
        String regex = "^[a-zA-Z0-9]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(regex)){
            errorEmail.setText("Vui lòng nhập lại email!");
            errorEmail.setVisible(true);
            flag = false;
            return;
        }
        errorEmail.setVisible(false);
    }
    private void checkExistEMAIL(String email){
        boolean exist  = ndd.existAttribute("Email" , email);
        if (exist){
            errorEmail.setText("Email đã tồn tại!");
            errorEmail.setVisible(true);
            flag = false;
            return;
        }
        errorEmail.setVisible(false);
    }
    //----------check địa chỉ -------------
    private void checkDiaChi(String diaChi){
        if (diaChi.trim().isEmpty()){
            errorDiaChi.setText("Vui lòng nhập địa chỉ!");
            errorDiaChi.setVisible(true);
            flag = false;
            return;
        }
        errorDiaChi.setVisible(false);
    }
    //----------------check ngày sinh--------------
    private void checkNgaySinh(LocalDate ngaySinh){
        if (ngaySinh == null){
            errorNgaySinh.setText("Vui lòng nhập ngày sinh!");
            errorNgaySinh.setVisible(true);
            flag = false;
            return;
        }
        //-------- Kiểm tra xem đủ 18 tuổi không----------
        LocalDate today = LocalDate.now();
        Period age = Period.between(ngaySinh,today);
        if (age.getYears()<18){
            errorNgaySinh.setText("Bạn chưa đủ tuổi để tạo tài khoản!");
            errorNgaySinh.setVisible(true);
            flag = false;
            return;
        }
        errorNgaySinh.setVisible(false);
    }
    //----------------check Hạn CCCD------------
    private void checkHanCCCD(LocalDate hanCCCD){
        if (hanCCCD == null){
            errorHanCCCD.setText("Vui lòng nhập hạn căn cước công dân!");
            errorHanCCCD.setVisible(true);
            flag = false;
            return;
        }
        LocalDate today = LocalDate.now();
        if (hanCCCD.isBefore(today)){
            errorHanCCCD.setText("Căn cước công dân đã hết hạn!");
            errorHanCCCD.setVisible(true);
            flag = false;
            return;
        }
        errorHanCCCD.setVisible(false);
    }
    private String chuanHoaTen(String hoTen){
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

