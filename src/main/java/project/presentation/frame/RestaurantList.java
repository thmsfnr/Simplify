package project.presentation.frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RestaurantList extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RestaurantListFrame.fxml"));

        stage.setTitle("Restaurant List");
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
