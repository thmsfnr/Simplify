package project.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.business.models.Table;

public class DetailsTableController{


    @FXML
    private Label tableName;

    @FXML
    private Label tableDescription;

    @FXML
    private Label booked;

    @FXML
    private Label idTable;



    @FXML
    public void setFields(Table table) {
        tableName.setText(table.getName());
        tableDescription.setText(table.getDescription());
        booked.setText(table.getBooked() ? "Réservé" : "Non réservé");
        idTable.setText(String.valueOf(table.getIdTable()));
    }


}
