package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import project.business.facade.TableFacade;
import project.business.models.Table;

public class DetailsTableController {

    private Table table;

    @FXML
    private TextField tableName;

    @FXML
    private TextField tableDescription;

    @FXML
    private TextField reservation;

    @FXML
    private Button cancelButton;
    @FXML
    private Button updateButton;

    @FXML
    public void setTextFields(Table table) {
        this.table = table;
        tableName.setText(table.getName());
        tableDescription.setText(table.getDescription());
        reservation.setText(table.getBooked().toString());
    }

    @FXML
    public void updateTable(ActionEvent event) {
        System.out.println("Update Table button pressed!");
        TableFacade tableFacade = TableFacade.getInstance();
        Table updatedTable = new Table(table.getIdTable(), tableName.getText(), tableDescription.getText(), reservation.getText().equals("true"));
        Boolean updated = tableFacade.updateTable(updatedTable);
        if (updated) {
            Display.infoBox("Table updated Successfully!", null, "Success");
        } else {
            Display.infoBox("Update Failed!", null, "Failed");
        }
        Table newTable = tableFacade.getTableById(table.getIdTable());
        setTextFields(newTable);
    }

    @FXML
    public void closeUpdateTable(ActionEvent event) {
        System.out.println("Cancel button pressed!");
        cancelButton.getScene().getWindow().hide();
    }

}
