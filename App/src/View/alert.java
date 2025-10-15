package View;

import javafx.scene.control.Alert;

public class alert {
    public static void ERROR(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public static void INFORMATION(String Title, String Message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setContentText(Message);
        alert.showAndWait();
    }
}
