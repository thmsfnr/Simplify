package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import project.business.facade.UserFacade;
import project.business.models.User;

public class PersonalAccountController {

    // Instance variables
    @FXML
    private Text emailField;
    @FXML
    private Text id;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Button submitButton;
    @FXML
    private Button deleteButton;

    /**
     * This method is used to load datas of the user
     * @param event the event of loading the frame
     */
    public void consultOne(ActionEvent event) {

        UserFacade userFacade = UserFacade.getInstance();

        if (id.getText().isEmpty()) {
            return;
        }

        User user = userFacade.getById(Integer.parseInt(id.getText()));

        emailField.setText(user.getEmail());
        lastNameField.setText(user.getName());
        firstNameField.setText(user.getFirstname());
        addressField.setText(user.getAddress());
        phoneNumberField.setText(user.getPhone());

    }

    /**
     * This method is used to manage the event of the update button
     * @param event the event of the update button
     */
    public void askDelete(ActionEvent event) {

        UserFacade userFacade = UserFacade.getInstance();

        if (id.getText().isEmpty()) {
            return;
        }

        User user = userFacade.getById(Integer.parseInt(id.getText()));

        user.setEmail(emailField.getText());
        user.setName(lastNameField.getText());
        user.setFirstname(firstNameField.getText());
        user.setAddress(addressField.getText());
        user.setPhone(phoneNumberField.getText());

        userFacade.update(user);
    }

    /**
     * This method is used to manage the event of the delete button
     * @param event the event of the delete button
     */
    public void update(ActionEvent event) {

        UserFacade userFacade = UserFacade.getInstance();

        if (id.getText().isEmpty()) {
            return;
        }

        User user = userFacade.getById(Integer.parseInt(id.getText()));

        user.setAskDelete(true);

        userFacade.update(user);
    }

}
