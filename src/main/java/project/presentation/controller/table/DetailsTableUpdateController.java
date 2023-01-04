package project.presentation.controller.table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import project.business.facade.TableFacade;
import project.business.models.Table;
import project.utilities.Display;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the controller of the update table component
 * It is used to update a table
 * @author Simplify members
 */
public class DetailsTableUpdateController {

    // Instance variables
    private Table table;

    @FXML
    private TextField tableName;

    @FXML
    private TextField tableDescription;

    @FXML
    private ChoiceBox<String> bookedChoice;

    private String[] booked = {"Non réservé", "Réservé"};


    @FXML
    private Button cancelButton;
    @FXML
    private Button updateButton;

    /**
     * This method is used to set the fields of the frame with the table's attributes
     * @param table the table to display
     */
    @FXML
    public void setTextFields(Table table) {
        this.table = table;
        tableName.setText(table.getName());
        tableDescription.setText(table.getDescription());
        bookedChoice.getItems().addAll(booked);
        bookedChoice.setValue(table.getBooked() ? "Réservé" : "Non réservé");
    }


    /**
     * This method is used to update a table
     * @param event the event of the button validation of update
     */
    @FXML
    public void updateTable(ActionEvent event) {
        System.out.println("Update Table button pressed!");
        TableFacade tableFacade = TableFacade.getInstance();

        // Get the window of the submit button
        Window owner = updateButton.getScene().getWindow();

        // Verify if the atributes are not empty
        if (tableName.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter the name of the table");
            return;
        }
        if (tableDescription.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a description");
            return;
        }
        if (bookedChoice.getValue() == null) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose if the table is booked or not");
            return;
        }

        Table updatedTable = new Table(table.getIdTable(), tableName.getText(), tableDescription.getText(), bookedChoice.getValue().equals("Réservé"), -1, -1);
        Boolean updated = tableFacade.updateTable(updatedTable);
        if (updated) {
            Display.infoBox("Table updated Successfully!", null, "Success");
        } else {
            Display.infoBox("Update Failed!", null, "Failed");
        }
        Table newTable = tableFacade.getTableById(table.getIdTable());
        setTextFields(newTable);
    }

    /**
     * This method is used to cancel the update of a table
     * @param event the event of the button cancel
     */
    @FXML
    public void closeUpdateTable(ActionEvent event) {
        System.out.println("Cancel button pressed!");
        cancelButton.getScene().getWindow().hide();
    }

}
