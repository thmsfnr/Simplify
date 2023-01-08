package project.presentation.frame.reservation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.presentation.controller.placement.PlacementController;
import project.presentation.controller.reservation.ReservationFormController;

public class ReservationFormFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // stage.setResizable(false); //pour pas maximiser une fenetre
        // Load the fxml file and create a new stage for the root
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReservationFormFrame.fxml"));
        Parent root = fxmlLoader.load();
        // Create the scene with the root and set the title of the stage
        stage.setTitle("New reservation");
        stage.setScene(new Scene(root, 1920, 1080));

        // Show the GUI
        stage.show();
    }
    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}
