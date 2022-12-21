
package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.UserFacade;
import project.business.models.User;
import project.presentation.frame.Menu;
import project.presentation.frame.Signin;

import static project.presentation.controller.Display.infoBox;
import static project.presentation.controller.Display.showAlert;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the controller of the login frame
 * It is used to call the facade and to manage the events of the frame
 * @author Simplify members
 */
public class LoginController {

    // Instance variables
    @FXML
    private TextField emailIdField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Button switchButton;

    /**
     * This method is used to manage the event of the login button
     * @param event the event of the submit button
     */
    @FXML
    public void login(ActionEvent event) throws Exception {
        // Get the window of the submit button
        Window owner = submitButton.getScene().getWindow();

        // if the email field is empty show an alert
        if (emailIdField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }

        // if the password field is empty show an alert
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        // Get the email and the password fields from the frame
        String emailId = emailIdField.getText();
        String password = passwordField.getText();

        // Call the facade to get the user
        UserFacade userFacade = UserFacade.getInstance();

        // try to login the user
        User user = userFacade.login(emailId, password);

        if (user != null) {

            Localstorage.write(user);
            switchToMenu((Stage) submitButton.getScene().getWindow());

        } else {
            infoBox("Login Failed!", null, "Failed");
        }
    }

    @FXML
    private void switchToSignin(ActionEvent event) throws Exception {

        // Get the window of the submit button
        Window owner = switchButton.getScene().getWindow();

        // Load the login frame
        Signin login = new Signin();
        login.start(new Stage());

        // close the actual frame
        owner.hide();
    }

    private void switchToMenu(Stage stage) throws Exception {
        // open the menu frame
        Menu menu = new Menu();
        menu.start(stage);
    }

}
