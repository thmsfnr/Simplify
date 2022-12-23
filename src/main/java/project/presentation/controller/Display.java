
package project.presentation.controller;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * Created by Simplify members on 22/12/22.
 * This class is used to display alerts and information boxes
 * @author Simplify members
 */
public class Display {

    /**
     * This method is after the login button is clicked and it shows an alert
     * it is used to show the result of the login
     * @param infoMessage the message of the alert
     * @param headerText the header of the alert
     * @param title the title of the alert
     */
    public static void infoBox(String infoMessage, String headerText, String title) {
        // Create the alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        // Set the parameters of the alert
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    /**
     * This method is used before the login button is clicked and it shows an alert
     * it is used to show if the fields are empty
     * @param alertType the type of the alert
     * @param owner the owner of the alert
     * @param title the title of the alert
     * @param message the message of the alert
     */
    static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        // Create the alert
        Alert alert = new Alert(alertType);

        // Set the parameters of the alert
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}