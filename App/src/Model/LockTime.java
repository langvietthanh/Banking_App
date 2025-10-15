package Model;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LockTime {
    private String SDT;
    private LocalDateTime LockTime;

    public LockTime(String sDT) {
        SDT = sDT;
        LockTime = congThoiGian(5);
    }

    public String getSDT() {
        return SDT;
    }

    public LocalDateTime getLockTime() {
        return LockTime;
    }

    public static LocalDateTime congThoiGian(int minutes) {
        return LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(minutes);
    }
}
