import Control.Login.Controller_Login;
import View.Popup.ManegerScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/Login/Login.fxml"));

        ManegerScene manegerScene = new ManegerScene();
        truyenDuLieuLogin(loader,manegerScene);

        Scene scene = new Scene(manegerScene.getRoot());
        stage.setScene(scene);
        stage.setTitle("Banking App");
        stage.show();
    }

    public void truyenDuLieuLogin(FXMLLoader loader,ManegerScene manegerScene) throws IOException {
        manegerScene.setLoader(loader);
        Controller_Login controller = manegerScene.getControllerOfLoader();
        controller.setManegerMainScene(manegerScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
