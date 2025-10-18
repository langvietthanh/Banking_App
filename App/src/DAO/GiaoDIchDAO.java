package DAO;

import JDBC.JDBCUtil;
import Model.GiaoDich;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GiaoDIchDAO implements interfaceDAO<GiaoDich> {

    @Override
    public void create(GiaoDich giaoDich) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "INSERT INTO GiaoDich (maGiaoDich, taiKhoanNguon, taiKhoanDich, soTien, thoiGianGiaoDich, noiDung)"
                + " VALUES (?,?,?,?,?,?);";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,giaoDich.getMaGD());
        ps.setString(2,giaoDich.getSoTaiKhoanNguon());
        ps.setString(3,giaoDich.getSoTaiKhoanDich());
        ps.setDouble(4,giaoDich.getSoTien());
        ps.setObject(5,giaoDich.getThoiGianGD());
        ps.setString(6,giaoDich.getNoiDung());
        ps.executeUpdate();
        ps.close();
        JDBCUtil.disconnect(con);
    }

    @Override
    public void delete(GiaoDich giaoDich) throws SQLException {

    }

    @Override
    public GiaoDich findByAttribute(String attribute, String key) throws SQLException {
        return null;
    }

    @Override
    public boolean existObject(String attribute, String value) throws SQLException {
        return false;
    }
}
