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
        String sql = "insert into NguoiDung (Password, HoTen, NgaySinh, CCCD, SDT, Email, DiaChi,HanCCCD)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,nd.getCccd());
            ps.setString(2,nd.getPassword());
            ps.setString(3,nd.getHoTen());
            ps.setObject(4,nd.getNgaySinh());
            ps.setString(5,nd.getSoDienThoai());
            ps.setString(6,nd.getEmail());
            ps.setString(7,nd.getDiaChi());
            ps.setObject(8,nd.getHanCCCD());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBCUtil.disconnect(con);
    }

    @Override
    public <V> void updateAttribute(String attribute,V value,String CCCD){
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE NguoiDung "
                +"SET "
                +attribute+" = ? "
                +"WHERE CCCD = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1,value);
            ps.setString(2,CCCD);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
    }

    @Override
    public void delete(NguoiDung nd) {
        Connection con = JDBCUtil.getConnection();

        String sql = "delete from NguoiDung "
                + "where CCCD = ? ;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nd.getCccd());

            ps.executeUpdate();
            ps.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
    }
    @Override
    public Object findByAttribute(String attribute,String key){
        Connection con = JDBCUtil.getConnection();
        Object value = null;
        String sql = "SELECT "+attribute+" FROM NguoiDung WHERE CCCD = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                value = rs.getObject(attribute);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
        return value;
    }

    public NguoiDung Dang_Nhap(String sdt, String password) {
        Connection con = JDBCUtil.getConnection();
        String sql = "select * from nguoidung where SDT = ? and Password = ?;";
        NguoiDung nd = null;
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,sdt);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String cccd = rs.getString(1);
                String Password = rs.getString(2);
                String hoTen= rs.getString(3);
                LocalDate ngaySinh=rs.getObject(4,LocalDate.class);
                String soDienThoai= rs.getString(5);
                String email= rs.getString(6);
                String diaChi= rs.getString(7);
                LocalDate hanCCCD= rs.getObject(8,LocalDate.class);
                LocalDateTime ngayDangKi = rs.getObject(9,LocalDateTime.class);
                nd = new  NguoiDung();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBCUtil.disconnect(con);
        return nd;
    }

    public boolean Ton_Tai_Nguoi_Dung(String sdt) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "select * from nguoidung where SDT = ?;";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,sdt);
        ResultSet rs = ps.executeQuery();
        JDBCUtil.disconnect(con);
        return rs.next();
    }
}
