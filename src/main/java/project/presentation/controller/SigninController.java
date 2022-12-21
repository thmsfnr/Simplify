package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.UserFacade;
import project.business.models.User;
import project.presentation.frame.Login;

import java.io.IOException;

/**
 * Created by Simplify members on 21/12/22.
 * This class is the controller of the signin frame
 * It is used to call the facade and to manage the events of the frame
 * @author Simplify members
 */
public class SigninController {

    // Instance variables
    @FXML
    private TextField emailField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Button switchButton;

    /**
     * This method is used to manage the event of the signin button
     * @param event the event of the submit button
     */
    public void signin(ActionEvent event) throws Exception {

        // Get the window of the submit button
        Window owner = submitButton.getScene().getWindow();

        // if the email field is empty show an alert
        if (emailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email");
            return;
        }

        // if the password field is empty show an alert
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        // if the first name field is empty show an alert
        if (firstNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your first name");
            return;
        }

        // if the last name field is empty show an alert
        if (lastNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your last name");
            return;
        }

        // if the address field is empty show an alert
        if (addressField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your address");
            return;
        }

        // if the phone number field is empty show an alert
        if (phoneNumberField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your phone number");
            return;
        }

        // Get the email and the password fields from the frame
        String email = emailField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();

       User  user = new User(email, password, firstName, lastName, address, phoneNumber, false, false,1);

         // Call the facade to create the user
        UserFacade userFacade = UserFacade.getInstance();

        if (userFacade.create(user)) {
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                    "Welcome " + firstName + "!");
            // clear the fields
            emailField.clear();
            passwordField.clear();
            firstNameField.clear();
            lastNameField.clear();
            addressField.clear();
            phoneNumberField.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Registration Failed!",
                    "Please try again!");
        }


    }

    @FXML
    private void switchToLogin(ActionEvent event) throws Exception {

        // Get the window of the submit button
        Window owner = switchButton.getScene().getWindow();

        // Load the login frame
        Login login = new Login();
        login.start(new Stage());

        // close the actual frame
        owner.hide();
    }

    /**
     * This method is used before the login button is clicked and it shows an alert
     * it is used to show if the fields are empty
     * @param alertType the type of the alert
     * @param owner the owner of the alert
     * @param title the title of the alert
     * @param message the message of the alert
     */
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
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
