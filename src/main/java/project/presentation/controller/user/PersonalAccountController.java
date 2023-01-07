
package project.presentation.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.UserFacade;
import project.business.models.User;
import project.presentation.frame.menu.Menu;
import project.utilities.Display;
import java.io.*;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the controller of the personal account frame
 * It is used to call the facade and to manage the events of the frame
 * @author Simplify members
 */
public class PersonalAccountController {

    // Instance variables
    private User user;
    private static int idUser;
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
    @FXML
    private Button back;

    /**
     * This method is used to pass the user id to the controller
     * @param idUser the id of the user
     */
    public static void setIdUser(int idUser) {
        PersonalAccountController.idUser = idUser;
    }

    /**
     * This method is used to manage the event at the initialization of the frame
     * @throws IOException
     */
    public void initialize() throws IOException {
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getById(this.idUser);
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
            Display.showAlert(Alert.AlertType.CONFIRMATION, updateButton.getScene().getWindow(), "Success", "Your account has been updated");
        } else {
            Display.showAlert(Alert.AlertType.ERROR, updateButton.getScene().getWindow(), "Error", "Your account has not been updated");
        }
    }

    /**
     * This method is used to manage the event of the askDelete button
     * @param event the event of the askDelete button
     */
    public void askDelete(ActionEvent event) {
        UserFacade userFacade = UserFacade.getInstance();
        this.user.setAskDelete(true);

        if (userFacade.update(user)) {
            Display.showAlert(Alert.AlertType.CONFIRMATION, deleteButton.getScene().getWindow(), "Success", "Your request has been sent");
        } else {
            Display.showAlert(Alert.AlertType.ERROR, deleteButton.getScene().getWindow(), "Error", "Your request has not been sent");
        }
    }

    /**
     * This method is used to manage the event of the back button
     * @param event the event of the back button
     */
    public void backToMenu(ActionEvent event) throws Exception {
        Window owner = back.getScene().getWindow();
        Menu menu = new Menu();
        menu.start(new Stage());
        owner.hide();
    }

}
