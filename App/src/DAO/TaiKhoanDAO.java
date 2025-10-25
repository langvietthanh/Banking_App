package DAO;

import JDBC.JDBCUtil;
import Model.NguoiDung;
import Model.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaiKhoanDAO implements interfaceDAO<TaiKhoan> {
    @Override
    public void create(TaiKhoan account){
        Connection con = JDBCUtil.getConnection();
        String sql = "INSERT INTO TaiKhoan (CCCD, maPIN,SoTaiKhoan,SoDu,NgayTao)"
                + " VALUES (?,?,?,?,?);";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,account.getCccd());
            ps.setString(2,account.getMaPIN());
            ps.setString(3,account.getSoTaiKhoan());
            ps.setLong(4,account.getSoDu());
            ps.setObject(5,account.getNgayTao());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBCUtil.disconnect(con);
    }

    @Override
    public void delete(TaiKhoan account) {
        Connection con = JDBCUtil.getConnection();

        String sql = "delete from TaiKhoan "
                + "where SoTaiKhoan = ? ;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, account.getSoTaiKhoan());

            ps.executeUpdate();
            ps.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
    }

    public void updateAttribute(String attribute,String value,String key){
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE TaiKhoan " +
                "SET "+attribute+" = ?"+
                "WHERE cccd = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,value);
            ps.setString(2,key);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
    }

    @Override
    public TaiKhoan findByAttribute(String attribute, String key) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        TaiKhoan taiKhoan = null;

        String sql = "SELECT * FROM TaiKhoan WHERE " + attribute + " = ?;";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,key);

        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            String cccd = rs.getString(1), maPIN = rs.getString(2), soTaiKhoan= rs.getString(3), loaiTaiKhoan = rs.getString(4);
            long soDu = rs.getLong(5);
            LocalDateTime ngayTao = rs.getObject(6, LocalDateTime.class);
            taiKhoan = new TaiKhoan(cccd, maPIN, soTaiKhoan, loaiTaiKhoan,soDu,ngayTao);
        }
        ps.close();
        JDBCUtil.disconnect(con);
        return taiKhoan;
    }

    public boolean existObject(String attribute, String value){
        Connection con = JDBCUtil.getConnection();
        boolean exist = true;
        String sql = "SELECT "+attribute+" FROM TaiKhoan WHERE "+attribute+" = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,value);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()){
                exist = false;
            }
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
        return exist;
    }

    public String findAttributeByCCCD(String attribute,String key) {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT " + attribute + " FROM taikhoan WHERE CCCD = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(attribute);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
        return "";
    }

    public static void congTienTaiKhoan(double soTien, String soTaiKhoan) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "update taikhoan set sodu = sodu + ? where sotaiKhoan = ?;";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1,soTien);
        ps.setString(2,soTaiKhoan);
        ps.executeUpdate();
        ps.close();
        JDBCUtil.disconnect(con);
    }

    public static void truTienTaiKhoan(double soTien, String soTaiKhoan) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "update taikhoan set sodu = sodu - ? where sotaiKhoan = ?;";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1,soTien);
        ps.setString(2,soTaiKhoan);
        ps.executeUpdate();
        ps.close();
        JDBCUtil.disconnect(con);
    }
}
