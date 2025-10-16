package View.Popup;

import javafx.scene.control.Label;

public class label {
    public static void ERROR(Label label, String text) {
        label.setText(text);
        label.setVisible(true);
    }
    public static void setVisible(Label label,boolean flag) {
        label.setVisible(flag);
    }
}
