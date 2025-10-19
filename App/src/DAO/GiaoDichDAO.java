package DAO;

import JDBC.JDBCUtil;
import Model.GiaoDich;
import Model.GiaoDichTaiKhoanNguon;
import Model.LoaiGiaoDich;
import Model.TaiKhoan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GiaoDichDAO implements interfaceDAO<GiaoDich> {

    final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

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
        ps.setObject(5,giaoDich.getThoiGianGiaoDich());
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

    public ObservableList<GiaoDichTaiKhoanNguon> getAllGiaoDich(String soTaiKhoan) throws SQLException {
        ObservableList<GiaoDichTaiKhoanNguon> list =  FXCollections.observableArrayList();
        Connection con = JDBCUtil.getConnection();
        String sql = "select thoiGianGiaoDich,taiKhoanDich,soTien,noiDung from GiaoDich where taiKhoanNguon = ? or taiKhoanDich = ? order by thoiGianGiaoDich desc ;";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, soTaiKhoan);
        ps.setString(2, soTaiKhoan);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LocalDateTime thoiGianGiaoDich = rs.getObject(1, LocalDateTime.class);

            String soTaiKhoanDich = rs.getString(2);
            TaiKhoan taiKhoanDich = taiKhoanDAO.findByAttribute("soTaiKhoan",soTaiKhoanDich);

            LoaiGiaoDich loaiGiaoDich = LoaiGiaoDich.xacDinhLoaiGiaoDich(taiKhoanDich.kiemTraSoTaiKhoan(soTaiKhoan));

            double soTien = rs.getDouble(3);

            String noiDung = rs.getString(4);

            list.add(new GiaoDichTaiKhoanNguon(thoiGianGiaoDich,loaiGiaoDich,taiKhoanDich,soTien,noiDung));
        }
        JDBCUtil.disconnect(con);
        return  list;
    }
}
