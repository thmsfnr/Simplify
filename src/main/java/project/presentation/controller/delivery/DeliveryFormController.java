package project.presentation.controller.delivery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.models.*;
import project.presentation.controller.payment.PaymentCardController;
import project.utilities.Display;

import java.util.List;

/**
 * This class is the controller of the delivery form component
 * It is used to create a delivery
 * @author Simplify members
 */
public class DeliveryFormController {
    private User user;
    private Restaurant restaurant;
    private List<Meal> meals;
    @FXML
    private Button submitButton;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField firstnameField;

    /**
     * This method is called at the start of the frame
     * @param user the user
     * @param restaurant the restaurant
     * @param meals a list of meals
     */
    @FXML
    public void initialize(User user, Restaurant restaurant, List<Meal> meals) {
        this.user = user;
        this.restaurant = restaurant;
        this.meals = meals;
        addressField.setText(user.getAddress());
        phoneField.setText(user.getPhone());
        nameField.setText(user.getName());
        firstnameField.setText(user.getFirstname());
    }

    /**
     * This method is used to launch payment frame
     * @param event the event
     */
    @FXML
    public void pay(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();
        Delivery delivery = new Delivery();
        delivery.setIdUser(user.getId());
        delivery.setIdRestaurant(restaurant.getIdRestaurant());
        delivery.setState(State.WAITING_FOR_VALIDATION);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaymentCard.fxml"));
            Parent root = loader.load();
            PaymentCardController paymentCardController = loader.getController();
            paymentCardController.initialize(delivery, meals, user.getRole());
            Stage stage = new Stage();
            stage.setTitle("Payment");
            stage.setScene(new Scene(root));
            owner.hide();
        }
        catch (Exception e){
            e.printStackTrace();
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occurred, you can't pay");
        }
    }

}
