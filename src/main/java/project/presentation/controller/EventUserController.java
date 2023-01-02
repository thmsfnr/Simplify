package project.presentation.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import project.business.facade.EventFacade;
import project.business.models.Event;

import java.io.IOException;

public class EventUserController implements Initializable {

    // the area where to display the components
    @FXML
    private ScrollPane contentArea;

    /**
     * This method is used to initialize the frame
     * It displays the component of CreateTableController by default
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        Parent root = null;

        EventFacade eventFacade = EventFacade.getInstance();
        ObservableList<Event> events = eventFacade.getAllEvents();

        VBox node = new VBox();
        for (Event event : events)
        {
            try {
                FXMLLoader loader = new FXMLLoader(project.presentation.frame.EventUserFrame.class.getResource("EventDescriptionComponent.fxml"));
                root = loader.load();
                EventDescriptionController controller = loader.getController();
                //for each event create a component and place it in the content area
                controller.setFields(event);

                node.getChildren().add(root);
                node.setSpacing(10);
                node.setPadding(new Insets(10));

                // Add the FlowPane to the ScrollPane
                }
            catch(IOException e){
                throw new RuntimeException(e);
            }
            contentArea.setContent(node);
            // Activate the scroll bar
            contentArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        }
    }
}