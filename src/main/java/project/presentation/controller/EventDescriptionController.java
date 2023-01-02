package project.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import project.business.models.Event;

public class EventDescriptionController {

    @FXML
    private Label EventTitle;

    @FXML
    private Label EventDescription;

    @FXML
    private Label EventDate;

    @FXML
    private Label EventTime;


    // a function to set all the fields
    public void setFields(Event event){
        EventTitle.setText(event.getTitle());
        EventDescription.setText(event.getDescription());
        EventDate.setText(event.getDate().toString().substring(0,10));
        EventTime.setText(event.getTime());
    }
}
