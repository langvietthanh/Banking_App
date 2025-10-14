package test;

import DAO.NguoiDungDAO;
import Model.NguoiDung;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//CCCD, HoTen, NgaySinh,QuocTich, SDT, Email,HanCCCD, DiaChi
public class Test {
    public static void main(String[] args) throws ParseException {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.plusDays(1));
    }
}
