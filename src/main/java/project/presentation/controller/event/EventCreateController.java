package project.presentation.controller.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.EventFacade;
import project.business.models.Event;
import project.utilities.Display;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is the controller of the event creation component
 * It is used to create an event
 * @author Simplify members
 */
public class EventCreateController {

    @FXML
    private TextField EventTitle;

    @FXML
    private TextField EventDescription;

    @FXML
    private DatePicker EventDate;

    @FXML
    private TextField EventTime;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;


    /**
     * This method is called when the user clicks on the "Create" button
     * @param event the event
     */
    @FXML
    void CreateEvent(ActionEvent event) {

        System.out.println("Create Event button pressed!");

        EventFacade eventFacade= EventFacade.getInstance();

        // Get the window of the submit button
        Window owner = createButton.getScene().getWindow();

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
                    "Please choose a time for the event");
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

        Event toCreateEvent = new Event(0, EventTitle.getText(), EventDescription.getText(), timestamp);
        Boolean created = eventFacade.createEvent(toCreateEvent);
        if (created) {
            // Vider les champs
            EventTitle.setText("");
            EventDescription.setText("");
            EventDate.setValue(null);
            EventTime.setText("");
            Display.infoBox("Event Created Successfully!", null, "Success");
        } else {
            Display.infoBox("Creation Failed!", null, "Failed");
        }
    }

    /**
     * This method is called when the user clicks on the "Cancel" button
     * @param event the event
     */
    @FXML
    void closeCreateEvent(ActionEvent event) {
        System.out.println("Cancel button pressed!");

        // if the field is empty, close the window
        if (EventTitle.getText().isEmpty() && EventDescription.getText().isEmpty() && EventDate.getValue() == null && EventTime.getText().isEmpty()) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } else {
            //vider les champs
            EventTitle.setText("");
            EventDescription.setText("");
            EventDate.setValue(null);
            EventTime.setText("");
        }
    }

}
