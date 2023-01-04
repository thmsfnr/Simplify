package project.presentation.controller.event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import project.business.models.Event;


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
        // print only the date
        EventDate.setText(event.getDate().toString().substring(0,10));
        EventTime.setText(event.getTime());
    }
}

