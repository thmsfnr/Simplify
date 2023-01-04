package project.presentation.controller.table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import project.business.facade.TableFacade;
import project.utilities.Display;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the controller of the create table component
 * It is used to create a table
 * @author Simplify members
 */

public class CreateTableController {


    // Instance variables
    @FXML
    private Button submitButton;
    @FXML
    private TextField tableName;
    @FXML
    private TextArea tableDescription;


    /**
     * This method is used to create a table
     * @param event the event of the button validation of creation
     */
    @FXML
    public void CreateTable(ActionEvent event) {
        System.out.println("Valider La création de la table button pressed!");

        // Get the window of the submit button
        Window owner = submitButton.getScene().getWindow();
        // if the email field is empty show an alert
        if (tableName.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter the name of the table");
            return;
        }

        // if the password field is empty show an alert
        if (tableDescription.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
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

        // if the user is not created show an alert
        if (created) {
            Display.infoBox("Table created Successfully!", null, "Success");
        } else {
            Display.infoBox("Creation Failed!", null, "Failed");
        }
    }

}
