package DAO;

import JDBC.JDBCUtil;
import Model.LockTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LockTimeDAO implements interfaceDAO<LockTime> {
    public LockTimeDAO() {
    }

    @Override
    public void create(LockTime lt) {
        Connection con = JDBCUtil.getConnection();
        String sql = "insert into lockedaccount (SDT,ThoiGianKhoa)" + " values (?, ?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,lt.getSDT());
            ps.setObject(2,lt.getLockTime());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBCUtil.disconnect(con);
    }

    @Override
    public  void delete(LockTime lt) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "delete from lockedaccount where ThoiGianKhoa < ?;";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setObject(1,LocalDateTime.now());
        ps.executeUpdate();
        JDBCUtil.disconnect(con);
    }

    @Override
    public LockTime findByAttribute(String attribute, String key) {
        return null;
    }

    @Override
    public boolean existObject(String attribute, String value) throws SQLException {
        delete(null);

        Connection con = JDBCUtil.getConnection();
        String sql = "select * from lockedaccount where " + attribute + " = ? and ThoiGianKhoa > ?;";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1,value);
        ps.setObject(2,LocalDateTime.now());

        ResultSet rs = ps.executeQuery();

        boolean flag = rs.next();
        JDBCUtil.disconnect(con);
        return flag;
    }

    public String getLockTime(String sdt) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "select ThoiGianKhoa from lockedaccount where sdt = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,sdt);

        ResultSet rs = ps.executeQuery();
        rs.next();
        LocalDateTime ldt = rs.getObject(1, LocalDateTime.class);
        JDBCUtil.disconnect(con);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

        String ThoiGianConLai = ldt.format(dtf);
        return ThoiGianConLai;
    }

}
