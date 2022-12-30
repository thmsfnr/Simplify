package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import project.business.facade.EventFacade;
import project.business.models.Event;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

        String[] time = EventTime.getText().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a valid time");
            return;
        }

        // Verify if the date is not in the past
        /*
        LocalDateTime dateNow = LocalDateTime.of(EventDate.getValue(), LocalDateTime.now().toLocalTime());
        if (dateNow.isBefore(LocalDateTime.now())) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please choose a date in the future");
            return;
        }
        LocalDateTime date = LocalDateTime.of(EventDate.getValue().getYear(), EventDate.getValue().getMonth(), EventDate.getValue().getDayOfMonth(), hour, minute);
        Timestamp timestamp = Timestamp.valueOf(date);
        */
        LocalDateTime date = LocalDateTime.of(EventDate.getValue().getYear(), EventDate.getValue().getMonth(), EventDate.getValue().getDayOfMonth(), hour, minute);
        Timestamp timestamp = Timestamp.valueOf(date);

        Event toCreateEvent = new Event(0, EventTitle.getText(), EventDescription.getText(), timestamp);

        Boolean created = eventFacade.createEvent(toCreateEvent);
        if (created) {
            Display.infoBox("Event Created Successfully!", null, "Success");
        } else {
            Display.infoBox("Creation Failed!", null, "Failed");
        }
    }

    @FXML
    void closeCreateEvent(ActionEvent event) {
        System.out.println("Cancel button pressed!");
        cancelButton.getScene().getWindow().hide();
    }

}
