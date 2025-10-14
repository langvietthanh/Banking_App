package Model;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

public class BaoMat {

    // Tạo mã OTP 6 chữ số
    public static String generateOTP() {
        SecureRandom rng = new SecureRandom();
        int otp = 100000 + rng.nextInt(900000);
        return String.valueOf(otp);
    }

    // Hash chuỗi bằng SHA-256 (dùng cho OTP trước khi lưu)
    public static String sha256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] d = md.digest(input.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(d);
    }

    // Hash mật khẩu với BCrypt (lưu vào DB)
    public static String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }

    // Lấy timestamp expiry
    public static LocalDateTime nowPlusMinutes(int minutes) {
        return LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(minutes);
    }

}
