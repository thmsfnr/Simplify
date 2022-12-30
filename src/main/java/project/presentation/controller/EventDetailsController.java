package project.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.business.models.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class is the controller of the event details component
 * It is used to display the details of an event
 * @author Simplify members
 */
public class EventDetailsController {

    @FXML
    private Label EventTitle;


    @FXML
    private Label EventId;

    @FXML
    private Label EventDescription;

    @FXML
    private Label EventDate;

    @FXML
    private Label EventTime;


    public void setTextFields(Event event) {
        EventId.setText(String.valueOf(event.getIdEvent()));
        EventTitle.setText(event.getTitle());
        EventDescription.setText(event.getDescription());
        String timestamp = event.getDate().getYear() + "/" + event.getDate().getMonth() + "/" + event.getDate().getDay() + " " + event.getDate().getHours() + ":" + event.getDate().getMinutes();
        EventDate.setText(timestamp);
        EventTime.setText(event.getTime());
    }

}

