package project.presentation.controller.reservation;

import javafx.collections.FXCollections;
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
import project.business.models.*;
import project.exceptions.AccessDatabaseException;
import project.business.models.Notification;
import project.business.models.Reservation;
import project.presentation.controller.user.PersonalAccountController;
import project.presentation.frame.menu.Menu;
import project.presentation.frame.reservation.ReservationFormFrame;
import project.presentation.frame.reservation.ReservationInfoFrame;
import project.utilities.Display;
import project.utilities.LocalStorage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ReservationController implements Initializable {

    private static int idUser;
    @FXML
    private TableView<Reservation> tabReservation_in_progress;
    @FXML
    private TableView<Reservation> tabReservation_carried_out;
    @FXML
    private TableView<Reservation> tabReservation_cancelled;
    @FXML
    private TableColumn<Reservation, Integer> idOrder_in_progress;
    @FXML
    private TableColumn<Reservation, Integer> idRestaurant_in_progress;
    @FXML
    private TableColumn<Reservation, Date> date_in_progress;
    @FXML
    private TableColumn<Reservation, State> state_in_progress;
    @FXML
    private TableColumn<Reservation, Integer> idOrder_carried_out;
    @FXML
    private TableColumn<Reservation, Integer> idRestaurant_carried_out;
    @FXML
    private TableColumn<Reservation, Date> date_carried_out;
    @FXML
    private TableColumn<Reservation, Integer> idOrder_cancelled;
    @FXML
    private TableColumn<Reservation, Integer> idRestaurant_cancelled;
    @FXML
    private TableColumn<Reservation, Date> date_cancelled;

    private TableColumn<Reservation, Integer> idRestaurantT;
    @FXML
    private TableColumn<Reservation, Date> date;
    @FXML
    private TableColumn<Reservation, Integer> state;

    @FXML
    private Button button_create;
    @FXML
    private Button back;

    public static Boolean isManager = true;

    public static Boolean isAdmin = true;

    public static int idRestaurant = 1;

    /**
     * This method is used to pass the user id to the controller
     * @param idUser the id of the user
     */
    public static void setIdUser(int idUser) {
        ReservationController.idUser = idUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReservationFacade reservationFacade = ReservationFacade.getInstance();
        ObservableList<Reservation> reservations;

        if(isManager || isAdmin){
            reservations = FXCollections.observableArrayList(reservationFacade.getAllReservations(idRestaurant));
        }else{
            reservations = FXCollections.observableList(reservationFacade.getAllReservationsOfUser(idUser));
        }

        setTables(reservations);
        addDeleteButtonToTable();
        // add a listener to the tabReservation_in_progress to get the selected reservation
        tabReservation_in_progress.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Get the selected reservation
                Reservation reservation = null;
                reservation = tabReservation_in_progress.getSelectionModel().getSelectedItem();
                try {
                    switchToInfoFrame(reservation);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // add a listener to the tabReservation_carried_out to get the selected reservation
        tabReservation_carried_out.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Get the selected reservation
                Reservation reservation = null;
                reservation = tabReservation_carried_out.getSelectionModel().getSelectedItem();
                try {
                    switchToInfoFrame(reservation);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // add a listener to the tabReservation_in_progress to get the selected reservation
        tabReservation_cancelled.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Get the selected reservation
                Reservation reservation = null;
                reservation = tabReservation_cancelled.getSelectionModel().getSelectedItem();
                try {
                    switchToInfoFrame(reservation);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void setTables(ObservableList<Reservation> reservations) {
        // list tab
        List<Reservation> reservations_in_progress = new ArrayList<>();
        List<Reservation> reservations_carried_out = new ArrayList<>();
        List<Reservation> reservations_cancelled = new ArrayList<>();

        // organisation of reservations
        for(Reservation reservation : reservations){
            if(reservation.getState() == State.IN_PREPARATION || reservation.getState() == State.WAITING_FOR_VALIDATION || reservation.getState() == State.ON_DELIVERY){
                reservations_in_progress.add(reservation);
            }
            else if (reservation.getState() == State.CANCELLED){
                reservations_cancelled.add(reservation);
            }
            else{
                reservations_carried_out.add(reservation);
            }
        }

        // tab reservation in progress
        idOrder_in_progress.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        idRestaurant_in_progress.setCellValueFactory(new PropertyValueFactory<>("idRestaurant"));
        date_in_progress.setCellValueFactory(new PropertyValueFactory<>("date"));
        state_in_progress.setCellValueFactory(new PropertyValueFactory<>("State"));
        tabReservation_in_progress.setItems(FXCollections.observableList(reservations_in_progress));



        //tab reservation carried out
        idOrder_carried_out.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        idRestaurant_carried_out.setCellValueFactory(new PropertyValueFactory<>("idRestaurant"));
        date_carried_out.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabReservation_carried_out.setItems(FXCollections.observableList(reservations_carried_out));

        // tab reservation cancelled
        idOrder_cancelled.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        idRestaurant_cancelled.setCellValueFactory(new PropertyValueFactory<>("idRestaurant"));
        date_cancelled.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabReservation_cancelled.setItems(FXCollections.observableList(reservations_cancelled));

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
        tabReservation_in_progress.getColumns().add(colBtn);
    }
    public void cancelReservation(ActionEvent event, Reservation reservation) {
        // Create a new alert to confirm the deletion
        Window owner = tabReservation_in_progress.getScene().getWindow();

        // Call the facade to cancel the reservation from the database
        ReservationFacade reservationFacade = ReservationFacade.getInstance();
        Boolean result = reservationFacade.cancelReservation(reservation);
        if (result) {
            //get table of reservations
            try {
                ArrayList<Table> tables = (ArrayList<Table>) reservationFacade.getTablesOfReservation(reservation.getIdOrder());
                for (Table table : tables) {
                    table.setBooked(false);
                }
            } catch (AccessDatabaseException e) {
                throw new RuntimeException(e);
            }
            Display.infoBox("Reservation canceled successfully!", null, "Success");
            NotificationFacade notificationFacade = NotificationFacade.getInstance();

            Notification notification = new Notification(idUser, "Reservation canceled", "Your reservation at " + reservation.getIdRestaurant() + " has been canceled");
            notificationFacade.createNotification(notification);

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
        ObservableList<Reservation> reservations = null;
        reservations = FXCollections.observableList(reservationFacade.getAllReservationsOfUser(idUser));
        // Set the table
        setTables(reservations);
    }
    @FXML
    public void switchToFormFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window listeReservationWindow = button_create.getScene().getWindow();

        ReservationFormFrame formCreate = new ReservationFormFrame();
        ReservationFormController.isUpdate = false;
        formCreate.start(new Stage());

        // close the actual frame
        listeReservationWindow.hide();
    }

    @FXML
    public void switchToInfoFrame(Reservation reservation) throws Exception {
        // Get the window of the create button
        Window listeReservationWindow = button_create.getScene().getWindow();

        ReservationInfoFrame reservationInfoFrame = new ReservationInfoFrame();
        ReservationInfoController.reservationSelected = reservation;
        reservationInfoFrame.start(new Stage());

        // close the actual frame
        listeReservationWindow.hide();
    }

    /**
     * This method is used to manage the event of the back button
     * @param event the event of the back button
     */
    public void backToMenu(ActionEvent event) throws Exception {
        Window owner = back.getScene().getWindow();
        project.presentation.frame.menu.Menu menu = new Menu();
        menu.start(new Stage());
        owner.hide();
    }
}
