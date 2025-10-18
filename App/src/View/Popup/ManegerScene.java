package View.Popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManegerScene {
    private Parent root;
    private FXMLLoader loader;

    public ManegerScene() {
        this.loader = null;
    }

    public void change(ActionEvent actionEvent, String sceneTitle) throws IOException {
        Scene scene;
        if(root.getScene() == null) scene = new Scene(root);
        else scene = root.getScene();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(sceneTitle);
        stage.show();
    }

    public void back_next(FXMLLoader loader) throws IOException {
//        loarder chắc chắn đã được load()
        Scene scene = root.getScene();
        Stage stage = (Stage)scene.getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) throws IOException {
        this.loader = loader;
        if(loader.getRoot() != null){
            this.root = (Parent) loader.getRoot();
        }
        else{// Chỉ load một lần duy nhất
            Parent root = loader.load();
            setRoot(root);
        }
    }

}
