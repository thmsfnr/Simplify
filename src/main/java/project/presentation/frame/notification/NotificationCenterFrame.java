package project.presentation.frame.notification;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NotificationCenterFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // stage.setResizable(false); //pour pas maximiser une fenetre
        // Load the fxml file and create a new stage for the root
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NotificationCenterFrame.fxml"));
        Parent root = fxmlLoader.load();

        // Create the scene with the root and set the title of the stage
        stage.setTitle("All notifications");
        stage.setScene(new Scene(root, 1150, 760));


        // Show the stage in the centre of the screen
        stage.centerOnScreen();

        // set on not resizable window
        stage.setResizable(false);

        // Show the GUI
        stage.show();
    }
    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}
