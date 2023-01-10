
package project.presentation.frame.restaurant;

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
public class RestaurantList extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RestaurantListFrame.fxml"));
        stage.setTitle("Restaurant List");
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }

    /**
     * This method is used to launch the application
     * @param args the arguments of the application from the command line
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
