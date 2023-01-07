
package project;

import javafx.application.Application;
import javafx.stage.Stage;
import project.presentation.frame.menu.Menu;
import project.presentation.frame.user.Login;
import project.utilities.UserStorage;

/**
 * This class is the main class of the project
 * It is used to launch the application
 * @author Simplify members
 */
public class Main extends Application {

    /**
     * This method is Override from the class Application
     * It is used to set the root of the application
     * @param stage the stage of the application
     * @exception Exception if the fxml file is not found
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            UserStorage.load();
            Menu menu = new Menu();
            menu.start(new Stage());
        } catch (Exception e) {
            Login login = new Login();
            login.start(new Stage());
        }
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

