package project.presentation.frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class DeliveryInfo extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("DeliveryInfoFrame.fxml"));

        stage.setTitle("Delivery Info");
        stage.show();
    }
}
