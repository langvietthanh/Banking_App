package DAO;

import JDBC.JDBCUtil;
import Model.NguoiDung;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class NguoiDungDAO implements interfaceDAO<NguoiDung>{

    @Override
    public void create(NguoiDung nd) {
        Connection con = JDBCUtil.getConnection();
        String sql = "insert into NguoiDung (UserID, TenDangNhap, Password, HoTen, NgaySinh, CCCD, SDT, Email, DiaChi,HanCCCD,NgayDangKi)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1,nd.getUserID());
            ps.setString(2,nd.getTenDangNhap());
            ps.setString(3,nd.getPassword());
            ps.setString(4,nd.getHoTen());
            ps.setObject(5, nd.getNgaySinh());
            ps.setString(6,nd.getCccd());
            ps.setString(7,nd.getSoDienThoai());
            ps.setString(8,nd.getEmail());
            ps.setString(9,nd.getDiaChi());
            ps.setObject(10,nd.getHanCCCD());
            ps.setObject(11,nd.getNgayDangKi());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBCUtil.disconnect(con);
    }

    @Override
    public void update(NguoiDung nd, Long ID) {
        Connection con = JDBCUtil.getConnection();
//        UserID, TenDangNhap, Password, HoTen, NgaySinh, CCCD, SDT, Email, DiaChi,HanCCCD,NgayDangKi
        String sql = "update nguoidung "
                + "set "
                + "TenDangNhap = ? , "
                + "Password =? ,"
                + "HoTen =? ,"
                + "NgaySinh =? ,"
                + "SDT = ? ,"
                + "Email =? ,"
                + "DiaChi = ? ,"
                + "HanCCCD = ? ,"
                + "NgayDangKi = ? "
                + "where UserID = "+ ID+" or CCCD = ? ;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,nd.getTenDangNhap());
            ps.setString(2,nd.getPassword());
            ps.setString(3,nd.getHoTen());
            ps.setObject(4, nd.getNgaySinh());
            ps.setString(5,nd.getSoDienThoai());
            ps.setString(6,nd.getEmail());
            ps.setString(7,nd.getDiaChi());
            ps.setObject(8,nd.getHanCCCD());
            ps.setObject(9,nd.getNgayDangKi());
            ps.setString(10,nd.getCccd());

            ps.execute();
            ps.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
    }

    @Override
    public void delete(NguoiDung nd) {
        Connection con = JDBCUtil.getConnection();

        String sql = "delete from nguoidung "
                + "where CCCD = ? or UserID = ? ;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nd.getCccd());
            ps.setLong(2, nd.getUserID());

            ps.execute();
            ps.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
    }

    @Override
    public <V> ArrayList<NguoiDung> findByAttribute(String Attribute, V Val) {
        Connection con = JDBCUtil.getConnection();
        ArrayList<NguoiDung> list = new ArrayList<>();
        String sql = "select * from nguoidung "
                + "where " + Attribute + " = ? ;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, Val);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long userID = rs.getLong(1);
                String tendangnhap= rs.getString(2)
                        , password = rs.getString(3)
                        , hoTen =  rs.getString(4) ;
                LocalDate ngaySinh = rs.getObject(5, LocalDate.class);
                String cccd = rs.getString(6)
                        , soDienThoai = rs.getString(7)
                        , email = rs.getString(8)
                        , diaChi =  rs.getString(9);
                LocalDate hanCCCD =  rs.getObject(10,LocalDate.class);
                LocalDateTime ngayDangKi =  rs.getObject(11, LocalDateTime.class);


                NguoiDung nd = new NguoiDung(userID,tendangnhap,password,hoTen,ngaySinh,cccd,soDienThoai,email,diaChi,hanCCCD,ngayDangKi);

                list.add(nd);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
        return list;
    }
}
