package project.presentation.controller.reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import project.business.facade.MealFacade;
import project.business.facade.NotificationFacade;
import project.business.facade.ReservationFacade;
import project.business.facade.RestaurantFacade;
import project.business.models.*;
import project.exceptions.AccessDatabaseException;
import project.exceptions.MealNotFoundException;
import project.exceptions.RestaurantNotFoundException;
import project.presentation.controller.placement.PlacementController;
import project.presentation.frame.placement.Placement;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

import static project.utilities.Display.infoBox;

public class ReservationFormController implements Initializable {
    private int idRestaurantAttribut;
    @FXML
    private TableView<Restaurant> tabRestaurant;
    @FXML
    private TableColumn<Restaurant, Integer> idRestaurant;
    @FXML
    private TableColumn<Restaurant, String> name;

    @FXML
    private TableColumn<Restaurant, String> address;

    @FXML
    private TableColumn<Restaurant, Integer> nbOfStars;

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
    private DatePicker datePicker;

    @FXML
    private ListView<Object> listView_reservations;

    @FXML
    private AnchorPane anchorTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
        ObservableList<Restaurant> restaurants;

        try {
            // get all restaurants
            restaurants = FXCollections.observableList(restaurantFacade.getAllRestaurants());
            idRestaurant.setCellValueFactory(new PropertyValueFactory<>("idRestaurant"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            nbOfStars.setCellValueFactory(new PropertyValueFactory<>("nbOfStars"));
            tabRestaurant.setItems(restaurants);
        } catch (RestaurantNotFoundException e) {
            throw new RuntimeException(e);
        } catch (AccessDatabaseException e) {
            throw new RuntimeException(e);
        }

        // get all meals of the restaurant
        MealFacade mealFacade = MealFacade.getInstance();
        tabRestaurant.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Restaurant restaurant = tabRestaurant.getSelectionModel().getSelectedItem();
                idRestaurantAttribut = restaurant.getIdRestaurant();
                ObservableList<Meal> meals;
                meals = FXCollections.observableList(mealFacade.getAllMeal(idRestaurantAttribut));
                idMeal.setCellValueFactory(new PropertyValueFactory<>("idMeal"));
                title.setCellValueFactory(new PropertyValueFactory<>("title"));
                description.setCellValueFactory(new PropertyValueFactory<>("description"));
                price.setCellValueFactory(new PropertyValueFactory<>("price"));
                tabMeals.setItems(meals);

                //initialize the table
                initializeAnchorTable();
            }
        });
    }

    //fonction to return the actual instance of the controller if it exists
    private void initializeAnchorTable() {
        Parent root = null;
        try {
            PlacementController.isReservation = true;
            PlacementController.idRestaurant = idRestaurantAttribut;
            FXMLLoader fxmlLoader = new FXMLLoader(Placement.class.getResource("PlacementFrame.fxml"));
            root = fxmlLoader.load();
            PlacementController placementController = fxmlLoader.getController();
            placementController.setReservationFormController(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        anchorTable.getChildren().setAll(root);
    }

    @FXML
    private void selection_meal() {
        Meal mealSelected = tabMeals.getSelectionModel().getSelectedItem();
        //add meal to listView_reservation
        //verification if the id of restaurant for the selected meal is the same as the id of restaurant of meals in listView_reservation
        if(mealSelected != null){
            if (listView_reservations.getItems().size() > 0) {
                boolean mealFounded = false;
                //research firt meal in listView_reservation
                int i = 0;
                while (i < listView_reservations.getItems().size()-1 && !mealFounded) {
                    if(listView_reservations.getItems().get(i) instanceof Meal){
                        mealFounded = true;
                    }else{
                        i++;
                    }
                }
                if (mealFounded && mealSelected.getIdRestaurant() != ((Meal) listView_reservations.getItems().get(i)).getIdRestaurant()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("You can't reserve a meal from another restaurant");
                    alert.showAndWait();
                } else {
                    listView_reservations.getItems().add(mealSelected);
                }
            } else {
                listView_reservations.getItems().add(mealSelected);
            }
        }
    }

    @FXML
    public void selection_table(Table tableSelected) {
        //add table to listView_reservation
        //verification if the id of restaurant for the selected meal is the same as the id of restaurant of meals in listView_reservation
        if(tableSelected !=null){
            if (listView_reservations.getItems().size() > 0) {
                boolean tableFounded = false;
                //research firt table in listView_reservation
                int i = 0;
                while (i < listView_reservations.getItems().size()-1 && !tableFounded) {
                    if(listView_reservations.getItems().get(i) instanceof Table){
                        tableFounded = true;
                    }else{
                        i++;
                    }
                }
                if (tableFounded && tableSelected.getIdRestaurant() != (((Table) listView_reservations.getItems().get(i)).getIdRestaurant())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("You can't reserve a table from another restaurant");
                    alert.showAndWait();
                } else {
                    listView_reservations.getItems().add(tableSelected);
                }
            }else {
                listView_reservations.getItems().add(tableSelected);
            }
        }
    }

    @FXML
    public void delete_selected_object(){
        Object objectSelected = listView_reservations.getSelectionModel().getSelectedItem();
        if(objectSelected != null){
            listView_reservations.getItems().remove(objectSelected);
        }
    }

    @FXML
    public void confirm_reservation() {
        //get value of date picker
        LocalDate dateReservation = datePicker.getValue();
        if(dateReservation == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please fill the fields date");
            alert.showAndWait();
        }else {
            Date date = Date.valueOf(dateReservation);
            //get value of list view
            ObservableList<Object> reservationsObject = listView_reservations.getItems();
            Reservation reservation = null;
            try {
                reservation = new Reservation(idRestaurantAttribut, (Integer) LocalStorage.load("user_id"), date);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (reservationsObject.size() > 0) {
                for (Object object : reservationsObject) {
                    if (object instanceof Meal) {
                        Meal meal = (Meal) object;
                        reservation.setIdRestaurant(meal.getIdRestaurant());
                        if (reservation.getMeals().containsKey(meal.getIdMeal())) {
                            reservation.getMeals().put(meal.getIdMeal(), reservation.getMeals().get(meal.getIdMeal()) + 1);
                        } else {
                            reservation.addMeal(meal.getIdMeal(), 1);
                        }
                    } else if (object instanceof Table) {
                        Table table = (Table) object;
                        reservation.setIdRestaurant(table.getIdRestaurant());
                        if (!reservation.getTables().contains(table.getIdTable())) {
                            reservation.addTable(table.getIdTable());
                        }
                    }
                }
            }
            ReservationFacade reservationFacade = ReservationFacade.getInstance();
            try {
                if(reservationFacade.createReservation(reservation)){
                    infoBox("Order created successfully", null, "Success");
                    NotificationFacade notificationFacade = NotificationFacade.getInstance();
                    Notification notification = null;
                    notification = new Notification(reservation.getIdUser(), "A new reservation is created", "Your reservation at " + reservation.getIdRestaurant() + " has been created");
                    notificationFacade.createNotification(notification);
                }else{
                    infoBox("Order not created", null, "Failed");
                }
            } catch (AccessDatabaseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
