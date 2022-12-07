
package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import project.business.facade.UserFacade;
import project.business.models.User;

public class LoginController {

    @FXML
    private TextField emailIdField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;

    @FXML
    public void login(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();

        if (emailIdField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }

        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = emailIdField.getText();
        String password = passwordField.getText();

        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.login(emailId, password);
        if (user != null) {
            infoBox("Login Successful!", null, "Success");
        } else {
            infoBox("Login Failed!", null, "Failed");
        }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
