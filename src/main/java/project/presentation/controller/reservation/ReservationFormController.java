package project.presentation.controller.reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project.business.facade.MealFacade;
import project.business.facade.ReservationFacade;
import project.business.facade.RestaurantFacade;
import project.business.models.Meal;
import project.business.models.Reservation;
import project.business.models.Restaurant;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
    private TableView<Meal> tabMeal;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*

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
        ObservableList<Meal> meals;

        tabRestaurant.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Restaurant restaurant = tabRestaurant.getSelectionModel().getSelectedItem();
                try {
                    meals = FXCollections.observableList(mealFacade.getAllMealsOfRestaurant(restaurant.getIdRestaurant()));
                    idMeal.setCellValueFactory(new PropertyValueFactory<>("idMeal"));
                    title.setCellValueFactory(new PropertyValueFactory<>("title"));
                    description.setCellValueFactory(new PropertyValueFactory<>("description"));
                    price.setCellValueFactory(new PropertyValueFactory<>("price"));
                    tabMeal.setItems(meals);
                } catch (RestaurantNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (AccessDatabaseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }*/
    }
}
