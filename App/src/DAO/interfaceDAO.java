package DAO;

import JDBC.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface interfaceDAO<T> {
    public  void create(T t) throws SQLException;
    public  void delete(T t) throws SQLException;
    public T findByAttribute(String attribute, String key) throws SQLException;
    public boolean existObject(String attribute, String value) throws SQLException;
//    Tìm kiếm đối tượng bằng nhiều thuộc tính
    public static boolean existObjects(String table,List<String> attributes, List value) throws SQLException{
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM " + table + " WHERE ";
        for (int i = 0; i < attributes.size(); i++) {
            sql += attributes.get(i) + " = ?";
            if(i<attributes.size()-1) sql += " and ";
        }
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < value.size(); i++) {
            ps.setObject(i + 1, value.get(i));
        }
        ResultSet rs = ps.executeQuery();
        boolean flag = rs.next();
        ps.close();
        rs.close();
        JDBCUtil.disconnect(con);
        return flag;
    }
}
