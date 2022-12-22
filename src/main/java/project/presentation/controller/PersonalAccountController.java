package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import project.business.facade.UserFacade;
import project.business.models.User;

import java.io.*;

import static project.presentation.controller.Display.showAlert;

public class PersonalAccountController {

    // Instance variables
    private User user;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;

    public void initialize() throws IOException {
        UserFacade userFacade = UserFacade.getInstance();

        int id = Localstorage.load();

        User user = userFacade.getById(id);

        this.user = user;

        lastNameField.setText(user.getName());
        firstNameField.setText(user.getFirstname());
        addressField.setText(user.getAddress());
        phoneNumberField.setText(user.getPhone());
    }

    /**
     * This method is used to manage the event of the update button
     * @param event the event of the update button
     */
    public void update(ActionEvent event) {

        UserFacade userFacade = UserFacade.getInstance();

        this.user.setFirstname(firstNameField.getText());
        this.user.setName(lastNameField.getText());
        this.user.setAddress(addressField.getText());
        this.user.setPhone(phoneNumberField.getText());

        if (userFacade.update(this.user)) {
            showAlert(Alert.AlertType.CONFIRMATION, updateButton.getScene().getWindow(), "Success", "Your account has been updated");
        } else {
            showAlert(Alert.AlertType.ERROR, updateButton.getScene().getWindow(), "Error", "Your account has not been updated");
        }
    }

    /**
     * This method is used to manage the event of the delete button
     * @param event the event of the delete button
     */
    public void askDelete(ActionEvent event) {

        UserFacade userFacade = UserFacade.getInstance();

        this.user.setAskDelete(true);

        if (userFacade.update(user)) {
            showAlert(Alert.AlertType.CONFIRMATION, deleteButton.getScene().getWindow(), "Success", "Your request has been sent");
        } else {
            showAlert(Alert.AlertType.ERROR, deleteButton.getScene().getWindow(), "Error", "Your request has not been sent");
        }
    }

}
