import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        Đọc File giao diện
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/Login/Login.fxml"));
//        Nội dung bên trong của sổ
        Scene scene = new Scene(loader.load());
//        Stage là khung cửa sổ
        stage.setScene(scene);
        stage.setTitle("Banking App");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
