package project.presentation.controller.delivery;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import project.business.facade.DeliveryFacade;
import project.business.facade.UserFacade;
import project.business.models.Restaurant;
import project.business.models.User;

public class DeliveryFormController {

    private User user;
    private Restaurant restaurant;

    @FXML
    private Button submitButton;

    @FXML
    private TextArea addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField firstnameField;


    @FXML
    public void initialize(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
        addressField.setText(user.getAddress());
        phoneField.setText(user.getPhone());
        nameField.setText(user.getName());
        firstnameField.setText(user.getFirstname());
    }



}
