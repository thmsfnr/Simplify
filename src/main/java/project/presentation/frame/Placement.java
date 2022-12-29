package project.presentation.frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Placement extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("PlacementFrame.fxml"));

        stage.setMaximized(true);
        stage.setTitle("Placement");
        stage.setScene(new Scene(root));
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
