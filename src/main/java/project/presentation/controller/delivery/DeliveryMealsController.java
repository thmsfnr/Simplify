package project.presentation.controller.delivery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.DeliveryFacade;
import project.business.models.Meal;
import project.business.models.Restaurant;
import project.exceptions.AccessDatabaseException;
import project.presentation.controller.cart.CartController;
import project.presentation.frame.cart.CartFrame;
import project.utilities.CartStorage;
import project.utilities.Display;

import java.io.IOException;
import java.util.List;

public class DeliveryMealsController {
    private Restaurant restaurant;
    private int idUser;

    @FXML
    private ListView<Meal> list_meals;

    @FXML
    private Button submitButton;


    @FXML
    public void initialize(Restaurant restaurant, int idUser) {
        this.idUser = idUser;
        this.restaurant = restaurant;
        try{
            DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();
            List<Meal> meals = deliveryFacade.getAllMealsOfRestaurant(this.restaurant.getIdRestaurant());
            ObservableList<Meal> observable_list = FXCollections.observableArrayList(meals);
            list_meals.setItems(observable_list);
            list_meals.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        } catch (AccessDatabaseException e) {
            e.printStackTrace();
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured, please retry later");
        }
    }


    @FXML
    public void addToCart(ActionEvent actionEvent) {
        Window owner = list_meals.getScene().getWindow();
        List<Meal> selected_meals = list_meals.getSelectionModel().getSelectedItems();
        String meals = "";
        for(Meal meal : selected_meals){
            meals += "cart_meal="+meal.getIdMeal()+",qte=1 ";
        }

        try{
            CartStorage.write("cartStorage", meals);

            FXMLLoader loader = new FXMLLoader(CartFrame.class.getResource("CartFrame.fxml"));
            Parent root = loader.load();
            CartController cartController = loader.getController();
            cartController.setIdUser(this.idUser);
            cartController.initialize(null, null, this.restaurant);
            Stage stage = new Stage();
            stage.setTitle("Cart");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
            owner.hide();

        }
        catch(IOException e){
            e.printStackTrace();
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured, please retry later");
        }
    }
}
