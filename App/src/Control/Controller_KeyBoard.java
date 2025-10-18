package Control;

import Model.TaiKhoan;
import View.Popup.alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_KeyBoard {
    public boolean flag = false;
    private TaiKhoan taiKhoanNguon;
    @FXML
    private TextField textInput;

    public Controller_KeyBoard() throws IOException {
    }

    @FXML
    private void handleKeyPress(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        String key = btn.getText();
        String text = textInput.getText();

        switch (key) {
            case "←":
                if (!text.isEmpty()) {
                    textInput.setText(text.substring(0, text.length() - 1));
                    chuanHoa();
                }
                break;
            case "Enter":
                if (!text.isEmpty()) {
                    int modeSoDu = taiKhoanNguon.kiemTraSoDu(getValue());
                    if (modeSoDu == 0) {
                        flag = true;
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();
                    }
                    else {
                        xuatThongBaoSoDu(modeSoDu);
                    }
                    break;
                }
            case "000":
                if(!text.isEmpty()) {
                    textInput.appendText("000");
                    chuanHoa();
                }
                break;
            default:
                textInput.appendText(key);
                chuanHoa();
        }
        chuanHoa();
    }



    public void chuanHoa() {
        // Xóa dấu chấm cũ
        String input = textInput.getText();
        input = input.replaceAll("\\.", "");
        if (input.isEmpty()) return;

        // Nếu có phần thập phân
        String integerPart = input;

        // Đảo chuỗi để dễ nhóm
        StringBuilder sb = new StringBuilder(integerPart).reverse();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sb.length(); i++) {
            if (i > 0 && i % 3 == 0) result.append(".");
            result.append(sb.charAt(i));
        }

        textInput.setText(result.reverse().toString());
    }

    public double getValue() {
        String raw = textInput.getText();
        if (raw.isEmpty()) return 0.0;
        // Loại bỏ tất cả dấu '.' để tránh lỗi "multiple points"
        raw = raw.replaceAll("\\.", "");
        return Double.parseDouble(raw);
    }
//123456789456

    public void setTaiKhoanNguon(TaiKhoan taiKhoanNguon) {
        this.taiKhoanNguon = taiKhoanNguon;
    }


    public void clear() {
        textInput.clear();
    }

    private void xuatThongBaoSoDu(int modSoDu) {
        switch (modSoDu) {
            case 1:
                alert.ERROR("Số dư không đủ","Số dư không đủ đê thực hiện giao dịch");
                break;
            case 2:
                alert.ERROR("Số tiền duy trì tài khoản","Vui lòng đảm bảo tài khoản còn đủ 50,000VND");
        }
    }

    public boolean getFlag() {
        return flag;
    }
}
