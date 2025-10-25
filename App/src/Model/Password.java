package Model;

public class Password {
    private String Pass;
    private String hashedPass;

    public Password() {
    }

    public Password(String pass) {
        Pass = pass;
    }
    
    public String getPass() {
        return Pass;
    }
    
    public void setPass(String pass) {
        Pass = pass;
    }
    
    public int checkPass(String passChecker) {
        //----------Độ dài 8-------------
        if (Pass.length()<8)return 1;
        
        //-----------kiểm tra định dạng mật khẩu --------
        boolean hasUpper = Pass.matches(".*[A-Z].*");
        boolean hasLower = Pass.matches(".*[a-z].*");
        boolean hasDigit = Pass.matches(".*[0-9].*");
        boolean hasSpecial = Pass.matches(".*[@!%&*$#].*");
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial)return 2;
        //------------kiểm tra nhập lại mật khẩu có đúng không-------
        if(!Pass.equals(passChecker) )return 3;

        return 0;//0
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public void hashPass() {
        this.hashedPass = BaoMat.hashPassword(Pass);
    }
}
