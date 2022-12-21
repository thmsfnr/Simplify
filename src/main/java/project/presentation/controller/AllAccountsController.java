package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import project.business.facade.UserFacade;
import project.business.models.User;

import java.util.List;

public class AllAccountsController {

    /*
    consultAll(ActionEvent event) : void
update(ActionEvent event) : void
delete(ActionEvent event) : void
create(ActionEvent event) : void
consultAskDelete(ActionEvent event) : void
     */

    // Instance variables
    private List<User> users;
    @FXML
    private ListView list;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField banField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;

    /**
     * This method is used to load datas of users
     * @param event the event of loading the frame
     */
    public void initialize() {

        UserFacade userFacade = UserFacade.getInstance();

        list.getItems().clear();
        List<User> users = userFacade.getAllUser();

        this.users = users;

        for (User user : users) {
            list.getItems().add(user.getEmail() + " - " + user.getFirstname() + " " + user.getName());
        }
    }

    public void display(ActionEvent event) {

        System.out.println("clicked on " + list.getSelectionModel().getSelectedItem());



    }

    /**
     * This method is used to manage the event of the update button
     * @param event the event of the update button
     */
    public void update(ActionEvent event) {

    }

    /**
     * This method is used to manage the event of the delete button
     * @param event the event of the delete button
     */
    public void delete(ActionEvent event) {

    }

}
