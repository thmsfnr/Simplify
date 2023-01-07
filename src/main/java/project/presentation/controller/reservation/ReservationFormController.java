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
import project.business.facade.ReservationFacade;
import project.business.facade.RestaurantFacade;
import project.business.models.Meal;
import project.business.models.Reservation;
import project.business.models.Restaurant;
import project.exceptions.AccessDatabaseException;
import project.exceptions.MealNotFoundException;
import project.exceptions.RestaurantNotFoundException;
import project.presentation.frame.placement.Placement;
import project.presentation.frame.table.Table;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReservationFormController implements Initializable {
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
    private Button button_reserve_meal;

    @FXML
    private Button button_reserve_table;

    @FXML
    private Button button_confirm_reservation;

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
                ObservableList<Meal> meals;
                meals = FXCollections.observableList(mealFacade.getAllMeal(restaurant.getIdRestaurant()));
                idMeal.setCellValueFactory(new PropertyValueFactory<>("idMeal"));
                title.setCellValueFactory(new PropertyValueFactory<>("title"));
                description.setCellValueFactory(new PropertyValueFactory<>("description"));
                price.setCellValueFactory(new PropertyValueFactory<>("price"));
                tabMeals.setItems(meals);

                //initialize the table
                initializeAnchorTable(restaurant.getIdRestaurant());
            }
        });
    }

    private void initializeAnchorTable(int idRestaurant) {
        Parent root = null;
        try {
            LocalStorage.write("restaurant_id",idRestaurant);
            LocalStorage.write("isReservation",true);
            root = FXMLLoader.load(Placement.class.getResource("PlacementFrame.fxml"));
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
        if (listView_reservations.getItems().size() > 0) {
            if (mealSelected.getIdRestaurant() != ((Meal) listView_reservations.getItems().get(0)).getIdRestaurant()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("You can't reserve a meal from another restaurant");
                alert.showAndWait();
            } else {
                listView_reservations.getItems().add(mealSelected);
            }
        }
    }
    /*
    @FXML
    private void selection_table(){
        Table tableSelected = tabTables.getSelectionModel().getSelectedItem();
        //add table to listView_reservation
        listView_reservations.getItems().add(tableSelected);
    }*/

    @FXML
    public void confirm_reservation() {
        /*
        //get value of date picker
        LocalDate dateReservation = datePicker.getValue();
        //get value of list view
        ObservableList<Object> reservationsObject = listView_reservations.getItems();
        //if it's a meal
        for (Object reservationObject : reservationsObject) {
            if (reservationObject instanceof Meal) {
                Meal meal = (Meal) reservationObject;
                //Reservation(int idRestaurant, int idUser, Date date, List<Integer>tables, Map<Integer,Integer> meals)
                Reservation reservation = null;
                try {
                    reservation = new Reservation(meal.getIdRestaurant(),(Integer) LocalStorage.load("user_id"),dateReservation);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                reservation.setIdMeal(meal.getIdMeal());
                reservation.setIdUser(LocalStorage.getInstance().getUser().getIdUser());
                reservation.setDateReservation(Date.valueOf(dateReservation));
                ReservationFacade reservationFacade = ReservationFacade.getInstance();
                try {
                    reservationFacade.addReservation(reservation);
                } catch (AccessDatabaseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }*/
    }
}
