package project.presentation.frame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the main class of the table part of the application
 * @author Simplify members
 */
public class Table extends Application{

        @Override
        public void start(Stage stage) throws Exception {
            // Load the fxml file and create a new stage for the root
            Parent root = FXMLLoader.load(getClass().getResource("TableFrame.fxml"));

            // Create the scene with the root and set the title of the stage
            stage.setTitle("Table");
            stage.setScene(new Scene(root, 800, 500));

            // Show the GUI
            stage.show();
        }

        /**
        * This method is used to launch the application
        * @param args the arguments of the application from the command line
        */
        public static void main(String[] args) {

            // Launch the application
            Application.launch(args);
        }

}