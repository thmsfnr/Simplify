package project.presentation.controller.reservation;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import project.business.facade.NotificationFacade;
import project.business.facade.ReservationFacade;
import project.business.models.Notification;
import project.business.models.Reservation;
import project.presentation.frame.reservation.ReservationFormFrame;
import project.utilities.Display;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


public class ReservationController implements Initializable {
    @FXML
    private TableView<Reservation> tabReservation;
    @FXML
    private TableColumn<Reservation, Integer> idOrder;
    @FXML
    private TableColumn<Reservation, Integer> idRestaurant;

    @FXML
    private TableColumn<Reservation, Date> date;

    @FXML
    private TableColumn<Reservation, Integer> state;

    @FXML
    private Button button_create;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ReservationFacade reservationFacade = ReservationFacade.getInstance();
        ObservableList<Reservation> reservations;
        try {
            reservations = reservationFacade.getAllReservationsOfUser((Integer) LocalStorage.load("user_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        idOrder.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        idRestaurant.setCellValueFactory(new PropertyValueFactory<>("idRestaurant"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        state.setCellValueFactory(new PropertyValueFactory<>("idState"));
        tabReservation.setItems(reservations);
        addDeleteButtonToTable();
    }

    private void addDeleteButtonToTable() {
        // Create a new column
        TableColumn<Reservation, Void> colBtn = new TableColumn("Cancel reservation");

        // Add the button to the column
        Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Reservation, Void> call(final TableColumn<Reservation, Void> param) {
                final TableCell<Reservation, Void> cell = new TableCell<>() {

                    private final Button btn = new Button();
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Reservation reservation = getTableView().getItems().get(getIndex());
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
                                Reservation reservation = getTableView().getItems().get(getIndex());
                                cancelReservation(event, reservation);
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
        tabReservation.getColumns().add(colBtn);
    }
    public void cancelReservation(ActionEvent event, Reservation reservation) {

        // Create a new alert to confirm the deletion
        Window owner = tabReservation.getScene().getWindow();

        // Call the facade to cancel the reservation from the database
        ReservationFacade reservationFacade = ReservationFacade.getInstance();
        Boolean result = reservationFacade.cancelReservation(reservation);
        if (result) {
            Display.infoBox("Reservation canceled successfully!", null, "Success");
            NotificationFacade notificationFacade = NotificationFacade.getInstance();
            try {
                Notification notification = new Notification((Integer) LocalStorage.load("user_id"), "Reservation canceled", "Your reservation at " + reservation.getIdRestaurant() + " has been canceled");
                notificationFacade.createNotification(notification);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        // Refresh the table
        refreshTable();
    }

    private void refreshTable() {
        // Call the facade to get
        ReservationFacade reservationFacade = ReservationFacade.getInstance();
        ObservableList<Reservation> Reservations = null;
        try {
            Reservations = reservationFacade.getAllReservationsOfUser((Integer) LocalStorage.load("user_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Set the table
        tabReservation.setItems(Reservations);
    }
    @FXML
    private void switchToFormFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window listeReservationWindow = button_create.getScene().getWindow();

        ReservationFormFrame formCreate = new ReservationFormFrame();
        formCreate.start(new Stage());

        // close the actual frame
        listeReservationWindow.hide();
    }
}
