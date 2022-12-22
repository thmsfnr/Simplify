package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import project.business.facade.TableFacade;

import static project.presentation.controller.LoginController.infoBox;
import static project.presentation.controller.LoginController.showAlert;

public class CreateTableController {


    @FXML
    private Button submitButton;
    @FXML
    private TextField tableName;
    @FXML
    private TextArea tableDescription;


    @FXML
    public void CreateTable(ActionEvent event) {
        System.out.println("Valider La création de la table button pressed!");

        // Get the window of the submit button
        Window owner = submitButton.getScene().getWindow();
        // if the email field is empty show an alert
        if (tableName.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter the name of the table");
            return;
        }

        // if the password field is empty show an alert
        if (tableDescription.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a description");
            return;
        }

        // Get the email and the password fields from the frame
        String name = tableName.getText();
        String description = tableDescription.getText();

        // Call the facade to get
        TableFacade tableFacade = TableFacade.getInstance();

        // try to login the user
        Boolean created = tableFacade.createTable(name, description);

        if (created) {
            infoBox("Table created Successfully!", null, "Success");
        } else {
            infoBox("Creation Failed!", null, "Failed");
        }
    }

}
