package project.presentation.controller.event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import project.business.models.Event;

/**
 * This class is the controller of the event description component
 * It is used to display the description of an event
 * @author Simplify members
 */
public class EventDescriptionController {

    @FXML
    private Label EventTitle;

    @FXML
    private Label EventDescription;

    @FXML
    private Label EventDate;

    @FXML
    private Label EventTime;


    /**
     * This method is used to set all the fields of the event
     * @param event the event
     */
    public void setFields(Event event){
        EventTitle.setText(event.getTitle());
        EventDescription.setText(event.getDescription());
        EventDate.setText(event.getDate().toString().substring(0,10));
        EventTime.setText(event.getTime());
    }

}
