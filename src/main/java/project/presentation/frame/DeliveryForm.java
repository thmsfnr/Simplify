package project.presentation.frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeliveryForm extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DeliveryFormFrame.fxml"));

        stage.setTitle("Delivery Form");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}
