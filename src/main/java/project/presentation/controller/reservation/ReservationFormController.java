package project.presentation.controller.reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.ReservationFacade;
import project.business.facade.RestaurantFacade;
import project.business.models.Reservation;
import project.business.models.Restaurant;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.presentation.frame.menu.Menu;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ReservationFormController implements Initializable {

    private static int idUser;
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
    private Button button_reserve_meal;
    @FXML
    private Button button_reserve_table;
    @FXML
    private Button back;

    /**
     * This method is used to pass the user id to the controller
     * @param idUser the id of the user
     */
    public static void setIdUser(int idUser) {
        ReservationFormController.idUser = idUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
        ObservableList<Restaurant> restaurants;

        try {
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
