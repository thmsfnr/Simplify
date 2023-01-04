package project.presentation.controller.table;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import project.business.models.Table;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the controller of the details table component
 * It is used to display the details of a table
 * @author Simplify members
 */
public class DetailsTableController{

    // Instance variables
    @FXML
    private Label tableName;

    @FXML
    private Label tableDescription;

    @FXML
    private Label booked;

    @FXML
    private Label idTable;


    /**
     * This method is used to display the details of a table
     * @param table the table to display
     */
    @FXML
    public void setFields(Table table) {
        // Set the fields of the frame with the table's attributes
        tableName.setText(table.getName());
        tableDescription.setText(table.getDescription());
        booked.setText(table.getBooked() ? "Réservé" : "Non réservé");
        idTable.setText(String.valueOf(table.getIdTable()));
    }


}
