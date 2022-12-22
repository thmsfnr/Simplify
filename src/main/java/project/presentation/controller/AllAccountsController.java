package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.Text;
import project.business.facade.UserFacade;
import project.business.models.User;

import java.util.List;

public class AllAccountsController {

    // Instance variables
    private List<User> users;
    private List<User> askedUsers;
    private User selectedUser;
    @FXML
    private ListView list;
    @FXML
    private ListView askedList;
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
    private TextField firstNameField1;
    @FXML
    private TextField lastNameField1;
    @FXML
    private TextField addressField1;
    @FXML
    private TextField phoneNumberField1;
    @FXML
    private TextField emailField1;
    @FXML
    private TextField banField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button createButton;

    /**
     * This method is used to load datas of users
     */
    public void initialize() {
        consultAll();
        consultAskDelete();
    }

    private void consultAll() {
        UserFacade userFacade = UserFacade.getInstance();

        list.getItems().clear();
        List<User> users = userFacade.getAllUser();

        this.users = users;

        for (User user : users) {
            list.getItems().add(user.toString());
        }
    }

    public void display() {

        Object clicked = list.getSelectionModel().getSelectedItem();

        String[] elements = clicked.toString().split(" ");

        for (User user : users) {

            if (user.getId() == Integer.parseInt(elements[0])) {
                this.selectedUser = user;
                lastNameField.setText(user.getName());
                firstNameField.setText(user.getFirstname());
                addressField.setText(user.getAddress());
                phoneNumberField.setText(user.getPhone());
                emailField.setText(user.getEmail());
                banField.setText(String.valueOf(user.getBan()));
            }
        }

    }

    /**
     * This method is used to manage the event of the update button
     * @param event the event of the update button
     */
    public void update(ActionEvent event) {


        Window w = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // if each field is not empty
        if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && !addressField.getText().isEmpty() && !phoneNumberField.getText().isEmpty() && !emailField.getText().isEmpty() && !banField.getText().isEmpty()) {

            UserFacade userFacade = UserFacade.getInstance();

            this.selectedUser.setFirstname(firstNameField.getText());
            this.selectedUser.setName(lastNameField.getText());
            this.selectedUser.setAddress(addressField.getText());
            this.selectedUser.setPhone(phoneNumberField.getText());
            this.selectedUser.setEmail(emailField.getText());
            if (banField.getText().equals("true")) {
                this.selectedUser.setBan(true);
            } else {
                this.selectedUser.setBan(false);
            }

            if (userFacade.update(this.selectedUser)) {
                Display.showAlert(Alert.AlertType.INFORMATION, w, "Update successful","The user has been updated");
                consultAll();
                firstNameField.clear();
                lastNameField.clear();
                addressField.clear();
                phoneNumberField.clear();
                emailField.clear();
                banField.clear();
                this.selectedUser = null;
            } else {
                Display.showAlert(Alert.AlertType.ERROR, w, "Update failed","The user has not been updated");
            }
        } else {
            Display.showAlert(Alert.AlertType.ERROR, w, "Update failed", "Please fill all the fields");
        }

    }

    /**
     * This method is used to manage the event of the delete button
     */
    public void delete() {

        Window w = deleteButton.getScene().getWindow();

        UserFacade userFacade = UserFacade.getInstance();

        if (userFacade.delete(this.selectedUser.getId())) {
            Display.showAlert(Alert.AlertType.INFORMATION, w, "Delete successful","The user has been deleted");
            consultAll();
            consultAskDelete();
            firstNameField.clear();
            lastNameField.clear();
            addressField.clear();
            phoneNumberField.clear();
            emailField.clear();
            banField.clear();
            this.selectedUser = null;
        } else {
            Display.showAlert(Alert.AlertType.ERROR, w, "Delete failed","The user has not been deleted");
        }

    }

    /**
     * This method is used to manage the event of the create button
     * @param event the event of the create button
     */
    public void create(ActionEvent event) {

        // if all fields are not empty
        if (!firstNameField1.getText().isEmpty() && !lastNameField1.getText().isEmpty() && !addressField1.getText().isEmpty() && !phoneNumberField1.getText().isEmpty() && !emailField1.getText().isEmpty() && !passwordField.getText().isEmpty()) {

            Window w = (Stage) ((Node) event.getSource()).getScene().getWindow();

            UserFacade userFacade = UserFacade.getInstance();

            User user = new User(firstNameField1.getText(), lastNameField1.getText(), addressField1.getText(), phoneNumberField1.getText(), emailField1.getText(), passwordField.getText(), false, false,2);

            if (userFacade.create(user)) {
                Display.showAlert(Alert.AlertType.INFORMATION, w, "Create successful","The user has been created");
                consultAll();
                firstNameField1.clear();
                lastNameField1.clear();
                addressField1.clear();
                phoneNumberField1.clear();
                emailField1.clear();
                passwordField.clear();
            } else {
                Display.showAlert(Alert.AlertType.ERROR, w, "Create failed","The user has not been created");
            }
        } else {
            Display.showAlert(Alert.AlertType.ERROR, createButton.getScene().getWindow(), "Create failed", "Please fill all the fields");
        }
    }

    public void askDelete() {

        Object clicked = askedList.getSelectionModel().getSelectedItem();

        String[] elements = clicked.toString().split(" ");

        for (User user : askedUsers) {

            if (user.getId() == Integer.parseInt(elements[0])) {
                this.selectedUser = user;

                delete();
            }
        }


    }

    /**
     * This method is used to select all user with askDelete = true
     */
    public void consultAskDelete() {

        UserFacade userFacade = UserFacade.getInstance();

        askedList.getItems().clear();
        List<User> askedUsers = userFacade.getAskDelete();

        this.askedUsers = askedUsers;

        for (User user : askedUsers) {
            askedList.getItems().add(user.toString());
        }



    }

}
