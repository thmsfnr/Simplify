package project.presentation.frame.delivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeliveryList extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DeliveryListFrame.fxml"));
        stage.setTitle("Delivery List");
        stage.setScene(new Scene(root, 1150, 760));
        // Show the stage in the centre of the screen
        stage.centerOnScreen();
        // set on not resizable window
        stage.setResizable(false);
        stage.show();
    }
}
