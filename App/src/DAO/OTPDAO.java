package DAO;
import JDBC.JDBCUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OTPDAO{
    // Lưu OTP hash
    public void saveOtp(String sdt, String otpHash, LocalDateTime expiresAt) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "INSERT INTO otp_verify (sdt,otp_hash, created_at, expires_at, used) VALUES ( ?, ?, ?, ?, false)";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, sdt);
            p.setString(2, otpHash);
            p.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            p.setTimestamp(4, Timestamp.valueOf(expiresAt));
            p.executeUpdate();
        }
        JDBCUtil.disconnect( con);
    }

    // Kiểm tra OTP hợp lệ: tìm bản ghi chưa used, chưa hết hạn, và otp_hash khớp
    public boolean verifyOtp(String sdt, String otpHash) throws SQLException {
        deleteExpiredOtps();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT SDT FROM otp_verify WHERE sdt=? AND otp_hash=? AND used=false AND expires_at > ? ORDER BY expires_at DESC LIMIT 1";
        PreparedStatement p =  con.prepareStatement(sql);
        p.setString(1, sdt);
        p.setString(2, otpHash);
        p.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            String SDT = rs.getString(1);
            // Đánh dấu đã dùng
            markOtpUsed(SDT);
            JDBCUtil.disconnect( con);
            return true;
        }
        JDBCUtil.disconnect( con);
        return false;

    }

    private void markOtpUsed(String sdt) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE otp_verify SET used=true WHERE sdt=?";
        PreparedStatement p =  con.prepareStatement(sql);
        p.setString(1, sdt);
        p.executeUpdate();
        JDBCUtil.disconnect( con);
    }

    // Xóa OTP cũ (tùy chọn)
    public void deleteExpiredOtps() throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "DELETE FROM otp_verify WHERE expires_at < ?";
        PreparedStatement p =  con.prepareStatement(sql);
        p.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        p.executeUpdate();
        JDBCUtil.disconnect( con);
    }
}

