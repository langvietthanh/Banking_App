package DAO;

import JDBC.JDBCUtil;
import Model.NguoiDung;
import Model.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAO implements interfaceDAO<TaiKhoan> {
    @Override
    public void create(TaiKhoan account){
        Connection con = JDBCUtil.getConnection();
        String sql = "INSERT INTO TaiKhoan (SoTK, maPIN, SoDu, NgayTao)"
                + " VALUES (?,?,?,?);";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,account.getSoTaiKhoan());
            ps.setString(2,account.getMaPIN());
            ps.setLong(3,account.getSoDu());
            ps.setObject(4,account.getNgayTao());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBCUtil.disconnect(con);
    }
    @Override
    public <V> void updateAttribute(String attribute,V value,String SoTK){
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE TaiKhoan "
                +"SET "
                +attribute+" = ? "
                +"WHERE SoTK = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1,value);
            ps.setString(2,SoTK);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        JDBCUtil.disconnect(con);
    }
    @Override
    public void delete(TaiKhoan account) {
        Connection con = JDBCUtil.getConnection();

        String sql = "delete from TaiKhoan "
                + "where SoTK = ? ;";
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
    @Override
    public Object findByAttribute(String attribute,String key){
        Connection con = JDBCUtil.getConnection();
        Object value = null;
        String sql = "SELECT "+attribute+" FROM TaiKhoan WHERE SoTK = ?;";
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
    public boolean existAttribute(String attribute,String value){
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
}
