package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	private static final String URL = "jdbc:mariadb://localhost:3306/BankingApp",
	USER = "root",
	PASSWORD = "";
	
	 public static Connection getConnection() {
	        Connection conn = null;
	        try {
	            // Từ JDBC 4.0 trở đi không cần Class.forName nữa, chỉ cần có driver trong classpath
	            conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return conn;
	    }

	    public static void disconnect(Connection conn) {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
