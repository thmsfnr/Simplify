package project.presentation.controller.reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.NotificationFacade;
import project.business.facade.ReservationFacade;
import project.business.facade.RestaurantFacade;
import project.business.models.*;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.presentation.frame.reservation.ReservationFormFrame;
import project.presentation.frame.reservation.ReservationFrame;
import project.utilities.Display;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static project.utilities.Display.infoBox;

public class ReservationInfoController implements Initializable {
    public static Reservation reservationSelected;

    @FXML
    private Text idOrderT;

    @FXML
    private DatePicker date;

    @FXML
    private Text restaurant;

    @FXML
    private Text state;

    @FXML
    private TableView<Meal> tabMeals;

    @FXML
    private TableColumn<Meal, Integer> idMeal;

    @FXML
    private TableColumn<Meal, String> title;

    @FXML
    private TableColumn<Meal, String> description;

    @FXML
    private TableColumn<Meal, Double> price;

    @FXML
    private TableView<Table> tabTables;

    @FXML
    private TableColumn<Table, Integer> idTable;

    @FXML
    private TableColumn<Table, String> name;

    @FXML
    private TableColumn<Table, String> tableDescription;

    @FXML
    private Button button_edit_reservation;

    @FXML
    private Button button_cancel_reservation;

    @FXML
    private Button button_delete_reservation;

    @FXML
    private Button button_accept_reservation;

    public static Boolean isManager = true;

    public static Boolean isAdmin = true;


    /**
     * This method is used to initialize the controller
     * @param url the url
     * @param resourceBundle the resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(isAdmin || isManager){
            button_edit_reservation.setVisible(false);
            button_edit_reservation.setDisable(true);
            button_cancel_reservation.setDisable(true);
            button_cancel_reservation.setVisible(false);
            button_delete_reservation.setVisible(true);
            button_delete_reservation.setDisable(false);
            button_accept_reservation.setVisible(true);
            button_accept_reservation.setDisable(false);
        }else{
            button_edit_reservation.setVisible(true);
            button_edit_reservation.setDisable(false);
            button_cancel_reservation.setDisable(false);
            button_cancel_reservation.setVisible(true);
            button_delete_reservation.setVisible(false);
            button_delete_reservation.setDisable(true);
            button_accept_reservation.setVisible(false);
            button_accept_reservation.setDisable(true);
        }
        ReservationFacade reservationFacade = ReservationFacade.getInstance();
        //set idOrder
        idOrderT.setText(String.valueOf(reservationSelected.getIdOrder()));
        //set date picker
        date.setValue(reservationSelected.getDate().toLocalDate());
        //set restaurant
        RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
        try {
            Restaurant restaurantObject = restaurantFacade.getRestaurantById(reservationSelected.getIdRestaurant());
            restaurant.setText(restaurantObject.getName());
        } catch (RestaurantNotFoundException e) {
            throw new RuntimeException(e);
        } catch (AccessDatabaseException e) {
            throw new RuntimeException(e);
        }
        //set state
        if(reservationSelected.getState() == State.IN_PREPARATION || reservationSelected.getState() == State.WAITING_FOR_VALIDATION || reservationSelected.getState() == State.ON_DELIVERY) {
            //color of the text is yellow
            state.setStyle("-fx-fill: #b36a03");
            if(reservationSelected.getState() == State.IN_PREPARATION) {
                state.setText("In preparation");
            } else if(reservationSelected.getState() == State.WAITING_FOR_VALIDATION) {
                state.setText("Waiting for validation");
            } else if(reservationSelected.getState() == State.ON_DELIVERY) {
                state.setText("In progress");
            }
        }else if (reservationSelected.getState() == State.CANCELLED){
            //color of the text is red
            state.setStyle("-fx-fill: #930000");
            state.setText("Cancelled");
        }
        else{
            //color of the text is green
            state.setStyle("-fx-fill: #007500");
            state.setText("Validated");
        }
        //initialize the table of meals
        ObservableList<Meal> listeMeals = FXCollections.observableList(reservationFacade.getMealsOfReservation(reservationSelected.getIdOrder()));
        if(listeMeals != null){
            idMeal.setCellValueFactory(new PropertyValueFactory<>("idMeal"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            tabMeals.setItems(listeMeals);
        }
        //initialize the table of tables
        try {
            ObservableList<Table> listeTables = FXCollections.observableList(reservationFacade.getTablesOfReservation(reservationSelected.getIdOrder()));
            if(listeTables != null){
                idTable.setCellValueFactory(new PropertyValueFactory<>("idTable"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                tableDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                tabTables.setItems(listeTables);
            }
        } catch (AccessDatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to switch to the form reservation page
     * @param event the event button
     * @throws Exception
     */
    @FXML
    public void switchToFormFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window reservationInfoWindow = button_edit_reservation.getScene().getWindow();

        ReservationFormFrame reservationFormFrame = new ReservationFormFrame();
        ReservationFormController.setIsUpdate(true);
        ReservationFormController.reservationSelected = reservationSelected;
        reservationFormFrame.start(new Stage());

        // close the actual frame
        reservationInfoWindow.hide();
    }

    /**
     * This method is used to switch to the reservation list page
     * @throws Exception
     */
    private void switchToReservationFrame() throws Exception {
        // Get the window of the create button
        Window reservationInfoWindow = button_edit_reservation.getScene().getWindow();

        ReservationFrame reservationFrame = new ReservationFrame();
        reservationFrame.start(new Stage());

        // close the actual frame
        reservationInfoWindow.hide();
    }

    /**
     * This method is used to accept the selected reservation
     * @param event the event of the button
     */
    @FXML
    public void accept_reservation(ActionEvent event){
        if(isAdmin || isManager){
            ReservationFacade reservationFacade = ReservationFacade.getInstance();
            if(reservationFacade.acceptReservation(reservationSelected)){
                infoBox("Order validate successfully", null, "Success");
                NotificationFacade notificationFacade = NotificationFacade.getInstance();
                Notification notification = null;
                notification = new Notification(reservationSelected.getIdUser(), "Your reservation n°" + reservationSelected.getIdOrder()+" is accepted", "Your reservation at " + reservationSelected.getIdRestaurant() + " has been accepted");
                notificationFacade.createNotification(notification);
            }else{
                infoBox("Order not validated", null, "Failed");
            }
        }
    }

    /**
     * This method is used to delete the selected reservation
     * @param event the event of the button
     */
    @FXML
    public void delete_reservation(ActionEvent event){
        if(isAdmin || isManager){
            ReservationFacade reservationFacade = ReservationFacade.getInstance();
            if(reservationFacade.deleteReservation(reservationSelected.getIdOrder())){
                infoBox("Order delete successfully", null, "Success");
                NotificationFacade notificationFacade = NotificationFacade.getInstance();
                Notification notification = null;
                notification = new Notification(reservationSelected.getIdUser(), "Your reservation n°" + reservationSelected.getIdOrder()+" is deleted", "Your reservation at " + reservationSelected.getIdRestaurant() + " has been deleted");
                notificationFacade.createNotification(notification);
            }else{
                infoBox("Order not deleted", null, "Failed");
            }
        }
    }

    /**
     * This method is used to cancel the selected reservation
     */
    @FXML
    public void cancel(){
        Window owner = button_edit_reservation.getScene().getWindow();
        // Call the facade to cancel the reservation from the database
        ReservationFacade reservationFacade = ReservationFacade.getInstance();
        if (reservationFacade.cancelReservation(reservationSelected)) {
            //get table of reservations
            try {
                ArrayList<Table> tables = (ArrayList<Table>) reservationFacade.getTablesOfReservation(reservationSelected.getIdOrder());
                for (Table table : tables) {
                    table.setBooked(false);
                }
            } catch (AccessDatabaseException e) {
                throw new RuntimeException(e);
            }
            Display.infoBox("Reservation canceled successfully!", null, "Success");
            NotificationFacade notificationFacade = NotificationFacade.getInstance();
            try {
                Notification notification = new Notification((Integer) LocalStorage.load("user_id"), "Reservation canceled", "Your reservation at " + reservationSelected.getIdRestaurant() + " has been canceled");
                notificationFacade.createNotification(notification);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        try {
            switchToReservationFrame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
