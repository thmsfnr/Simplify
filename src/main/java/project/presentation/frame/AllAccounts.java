package project.presentation.frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        stage.setScene(new Scene(root, 1000, 1000));

        // open the controller
        /*PersonalAccountController controller = new PersonalAccountController();
        controller.consultOne();*/

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
