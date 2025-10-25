package Control.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Controller_Success {

    public void handleSuccess(ActionEvent actionEvent ) throws IOException, SQLException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Login/Login.fxml")));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Banking App");
        stage.show();
    }
}
