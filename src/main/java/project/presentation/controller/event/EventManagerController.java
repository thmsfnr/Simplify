package project.presentation.controller.event;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import project.business.facade.EventFacade;
import project.business.models.Event;
import project.presentation.frame.event.EventManagerFrame;

import java.time.LocalDate;
import project.utilities.Display;

import static project.utilities.Display.infoBox;
import static project.utilities.Display.showAlert;

public class EventManagerController implements Initializable {

    @FXML
    private TableView<Event> EventTab;

    @FXML
    private TableColumn<Event, Integer> idEvent;

    @FXML
    private TableColumn<Event, String> title;

    @FXML
    private TableColumn<Event, String> description;

    @FXML
    private TableColumn<Event, LocalDate> date;

    @FXML
    private TableColumn<Event, String> time;



    @FXML
    private void createEvent(ActionEvent event){
        try{
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(EventManagerFrame.class.getResource("EventCreateFrame.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Create Event");
            stage.setScene(new Scene(root));
            stage.show();

            stage.setOnCloseRequest(e -> {
                refreshTable();
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * This method is used to add a colomn to the table that will contain the button to update a table
     * A new frame will pop up when the button is clicked
     */
    private void addUpdateButtonToTable() {
        // Create a new column
        TableColumn<Event, Void> colBtn = new TableColumn("Update");

        // Add the button to the column
        Callback<TableColumn<Event, Void>, TableCell<Event, Void>> cellFactory = new Callback<TableColumn<Event, Void>, TableCell<Event, Void>>() {
            @Override
            public TableCell<Event, Void> call(final TableColumn<Event, Void> param) {
                final TableCell<Event, Void> cell = new TableCell<Event, Void>() {

                    private final Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Event data = getTableView().getItems().get(getIndex());
                        });
                    }

                    // The button will be displayed in the table
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // The button will be displayed with an image
                            Image image = new Image("file:src/main/resources/project/presentation/frame/update.png");
                            ImageView imageview = new ImageView(image);
                            imageview.setFitHeight(20);
                            imageview.setPreserveRatio(true);

                            btn.setPrefSize(50, 50);
                            //Setting a graphic to the button
                            btn.setGraphic(imageview);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: #c8ded0; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("updateBtn");

                            // When the button is clicked, a new frame will pop up
                            btn.setOnAction((ActionEvent event) -> {
                                Event data = getTableView().getItems().get(getIndex());
                                updateTable(event, data);
                            });

                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        // Add the column to the table
        colBtn.setCellFactory(cellFactory);

        EventTab.getColumns().add(colBtn);

    }

    /**
     * This Method is usef to handle the event of clicking on the update button
     * A new frame will pop up
     * @param event the event of clicking on the update button
     * @param data the table that will be updated
     */
    public void updateTable(ActionEvent event, Event data)
    {
        try{
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(EventManagerFrame.class.getResource("EventUpdateFrame.fxml"));
            Parent root = loader.load();
            EventUpdateController controller = loader.getController();
            controller.setTextFields(data);
            Stage stage = new Stage();
            stage.setTitle("Update Table");
            stage.setScene(new Scene(root));
            stage.show();
            // Close the frame and reload the table
            stage.setOnCloseRequest(e -> {
                refreshTable();
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to add a colomn to the table that will contain the button to delete a table
     */
    private void addDeleteButtonToTable() {
        // Create a new column
        TableColumn<Event, Void> colBtn = new TableColumn("Delete");

        // Add the button to the column
        Callback<TableColumn<Event, Void>, TableCell<Event, Void>> cellFactory = new Callback<TableColumn<Event, Void>, TableCell<Event, Void>>() {
            @Override
            public TableCell<Event, Void> call(final TableColumn<Event, Void> param) {
                final TableCell<Event, Void> cell = new TableCell<Event, Void>() {

                    private final Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Event data = getTableView().getItems().get(getIndex());
                        });
                    }

                    // The button will be displayed in the table

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // The button will be displayed with an image
                            Image image = new Image("file:src/main/resources/project/presentation/frame/delete.png");
                            ImageView imageview = new ImageView(image);
                            imageview.setFitHeight(20);
                            imageview.setPreserveRatio(true);

                            btn.setPrefSize(50, 50);
                            //Setting a graphic to the button
                            btn.setGraphic(imageview);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("deletebtn");


                            btn.setOnAction((ActionEvent event) -> {
                                Event data = getTableView().getItems().get(getIndex());
                                deleteTable(event, data);
                            });

                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        // Add the column to the table
        colBtn.setCellFactory(cellFactory);
        EventTab.getColumns().add(colBtn);
    }

    /**
     * This Method is used to handle the event of clicking on the delete button
     * @param event the event of clicking on the delete button
     * @param data the event that will be deleted
     */
    public void deleteTable(ActionEvent event, Event data) {

        // Create a new alert to confirm the deletion
        Window owner = EventTab.getScene().getWindow();

        // Confirmation of the deletion

        String deleteEvent = Display.confirmBox("Are you sure you want to delete this event?", "Delete Event");

        if (deleteEvent.equals("OK")) {

            // Call the facade to delete the table from the database
            EventFacade eventFacade = EventFacade.getInstance();
            Boolean result = eventFacade.deleteEvent(data.getIdEvent());
            if (result) {
                infoBox("Event deleted successfully!", null, "Success");
            } else {
                showAlert(Alert.AlertType.ERROR, owner, "Deletion error!",
                        "Event not deleted!");
                return;
            }
                // Refresh the table
                refreshTable();
            }
            else{
                return;
            }
        }


    /**
     * This method is used to refresh the table
     * It will be called after an update or a deletion
     */
    public void refreshTable() {
        // Call the facade to get
        EventFacade eventFacade = EventFacade.getInstance();
        ObservableList<Event> tables = eventFacade.getAllEvents();
        // Set the table
        EventTab.setItems(tables);
    }


    /**
     * This method is used to add a colomn to the table that will contain the button to see all the details of an event
     */
    private void addDetailsButtonToTable() {

        TableColumn<Event, Void> colBtn = new TableColumn("Details");
        Callback<TableColumn<Event, Void>, TableCell<Event, Void>> cellFactory = new Callback<TableColumn<Event, Void>, TableCell<Event, Void>>() {
            @Override
            public TableCell<Event, Void> call(final TableColumn<Event, Void> param) {
                final TableCell<Event, Void> cell = new TableCell<Event, Void>() {

                    private final Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Event data = getTableView().getItems().get(getIndex());
                        });
                    }


                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Image image = new Image("file:src/main/resources/project/presentation/frame/details.png");
                            ImageView imageview = new ImageView(image);
                            imageview.setFitHeight(20);
                            imageview.setPreserveRatio(true);

                            btn.setPrefSize(50, 50);
                            //Setting a graphic to the button
                            btn.setGraphic(imageview);
                            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-alignment: center; -fx-border-color: skyblue; -fx-border-width: 1px; -fx-border-radius: 5px;");
                            btn.setId("detailsbtn");
                            btn.setOnAction((ActionEvent event) -> {
                                Event data = getTableView().getItems().get(getIndex());
                                detailsTable(event, data);
                            });

                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        EventTab.getColumns().add(colBtn);

    }

    /**
     * This method is used to handle the event of clicking on the details button
     * @param event the event of clicking on the details button
     * @param eventDet the event that will be displayed
     */
    public void detailsTable(ActionEvent event, Event eventDet)
    {
        try{
            FXMLLoader loader = new FXMLLoader(EventManagerFrame.class.getResource("EventDetailsFrame.fxml"));
            Parent root = loader.load();
            EventDetailsController controller = loader.getController();

            EventFacade eventFacade = EventFacade.getInstance();
            Event data = eventFacade.getEventById(eventDet.getIdEvent());
            controller.setTextFields(data);
            Stage stage = new Stage();
            stage.setTitle("Details Event");
            stage.setScene(new Scene(root));
            stage.show();

            // Si on ferme la fenetre on recharge la table
            stage.setOnCloseRequest(e -> {
                refreshTable();
            });

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to initialize the table controller to display the table
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        EventFacade eventFacade = EventFacade.getInstance();

        // Create an array list of all the tables
        ObservableList<Event> Events = eventFacade.getAllEvents();

        // Set the table
        idEvent.setCellValueFactory(new PropertyValueFactory<Event, Integer>("idEvent"));
        title.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Event, String>("description"));
        date.setCellValueFactory(new PropertyValueFactory<Event, LocalDate>("date"));
        time.setCellValueFactory(new PropertyValueFactory<Event, String>("time"));
        EventTab.setItems(Events);
        // Add the buttons to the table
        addUpdateButtonToTable();
        addDeleteButtonToTable();
        addDetailsButtonToTable();
    }
}
