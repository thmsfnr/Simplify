package project.presentation.frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RestaurantInfos extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("/project/presentation/view/RestaurantInfos.fxml"));
            primaryStage.setTitle("Restaurant Infos");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        }

        public static void main(String[] args) {
            Application.launch(args);
        }
}
