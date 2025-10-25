package Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManegerScene {
    private Parent root;
    private FXMLLoader currentLoader, backLoarder;

    public ManegerScene() {
        this.currentLoader = null;
    }

    public void changeWithOldStage(ActionEvent actionEvent, String sceneTitle) throws IOException {
        Scene scene;
        if(root.getScene() == null) scene = new Scene(root);
        else scene = root.getScene();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(sceneTitle);
        stage.show();
    }

    public void changeWithNewStage(String sceneTitle) throws IOException {
        Scene scene;
        if(root.getScene() == null) scene = new Scene(root);
        else scene = root.getScene();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(sceneTitle);
        stage.show();
    }

    public void back(ActionEvent actionEvent) throws IOException {
//        loarder chắc chắn đã được load()
        Parent root = backLoarder.getRoot();
        Scene scene = root.getScene();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public FXMLLoader getCurrentLoader() {
        return currentLoader;
    }

    public void setCurrentLoader(FXMLLoader currentLoader) throws IOException {
        this.currentLoader = currentLoader;
        if(currentLoader.getRoot() != null){
            this.root = (Parent) currentLoader.getRoot();
        }
        else{// Chỉ load một lần duy nhất
            Parent root = currentLoader.load();
            setRoot(root);
        }

    }

    public void setBackLoarder(FXMLLoader backLoarder) {
        this.backLoarder = backLoarder;
    }

    public <T>  T getControllerOfLoader(){
        return currentLoader.getController();
    }

}
