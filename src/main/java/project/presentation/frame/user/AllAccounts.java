package project.presentation.frame.user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the main class of the application
 * It is used to launch the application
 * @author Simplify members
 */
public class AllAccounts extends Application {

    /**
     * This method is Override from the class Application
     * It is used to set the root of the application
     * @param stage the stage of the application
     * @exception Exception if the fxml file is not found
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load the fxml file and create a new stage for the root
        Parent root = FXMLLoader.load(getClass().getResource("AllAccountsFrame.fxml"));

        // Create the scene with the root and set the title of the stage
        stage.setTitle("All Accounts");
        stage.setScene(new Scene(root, 1150, 760));

        stage.setResizable(false);

        stage.centerOnScreen();

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
