package project.presentation.controller.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import project.business.facade.EventFacade;
import project.business.models.Event;
import project.business.models.Table;
import project.utilities.Display;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class is the controller of the event manager component
 * It is used to manage an event
 * @author Simplify members
 */
public class EventUpdateController {

    private Event event;
    @FXML
    private TextField EventTitle;

    @FXML
    private TextField EventDescription;

    @FXML
    private DatePicker EventDate;

    @FXML
    private TextField EventTime;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    /**
     * This method is used to set the fields of an event
     * @param event the event
     */
    @FXML
    public void setTextFields(Event event) {
        this.event = event;
        EventTitle.setText(event.getTitle());
        EventDescription.setText(event.getDescription());
        EventDate.setValue(event.getDate().toLocalDateTime().toLocalDate());
        EventTime.setText(event.getTime());
    }


    /**
     * This method is used to update a table
     * @param event the event of the button validation of update
     */
    @FXML
    public void updateEvent(ActionEvent event) {
        EventFacade eventFacade= EventFacade.getInstance();

        // Get the window of the submit button
        Window owner = updateButton.getScene().getWindow();

        // Verify if the atributes are not empty
        if (EventTitle.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter the title of the event");
            return;
        }
        if (EventDescription.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a description");
            return;
        }
        if (EventDate.getValue() == null) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose a date for the event");
            return;
        }

        if (EventTime.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a time for the event");
            return;
        }

        // Verify if the time is in the correct format
        if (!EventTime.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a valid time");
            return;
        }
        String[] time = EventTime.getText().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a valid time");
            return;
        }

        // Verify if the date is not in the past
        LocalDateTime dateNow = LocalDateTime.of(EventDate.getValue(), LocalDateTime.now().toLocalTime());
        if (dateNow.isBefore(LocalDateTime.now())) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose a date in the future");
            return;
        }
        LocalDateTime date = LocalDateTime.of(EventDate.getValue().getYear(), EventDate.getValue().getMonth(), EventDate.getValue().getDayOfMonth(), hour, minute);
        Timestamp timestamp = Timestamp.valueOf(date);

        Event updatedEvent = new Event(this.event.getIdEvent(), EventTitle.getText(), EventDescription.getText(), timestamp);
        Boolean updated = eventFacade.updateEvent(updatedEvent);
        if (updated) {
            Display.infoBox("Event updated Successfully!", null, "Success");
            this.event = updatedEvent;
            setTextFields(updatedEvent);
        } else {
            Display.infoBox("Update Failed!", null, "Failed");
        }
    }

    /**
     * This method is used to cancel the update of a table
     * @param event the event of the button cancel
     */
    @FXML
    public void closeUpdateEvent(ActionEvent event) {
        setTextFields(this.event);
    }

}
