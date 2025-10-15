package View;

import Control.Controller_ForgotPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class scene {
    public static void change(ActionEvent actionEvent, String nameFileFXML, String sceneTitle) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Controller_ForgotPassword.class.getResource(nameFileFXML)));

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(sceneTitle);
        stage.show();
    }
}
