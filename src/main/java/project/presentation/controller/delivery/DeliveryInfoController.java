package project.presentation.controller.delivery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.DeliveryFacade;
import project.business.models.Delivery;
import project.business.models.Meal;
import project.business.models.State;
import project.exceptions.AccessDatabaseException;
import project.presentation.frame.delivery.DeliveryList;
import project.utilities.Display;

import java.util.List;

public class DeliveryInfoController {
    private Delivery delivery;
    private boolean isManager = false;

    @FXML
    private Label idDeliveryLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private Label idRestaurantLabel;

    @FXML
    private Label idUserLabel;

    @FXML
    private ChoiceBox<State> stateChoiceBox;

    @FXML
    private Button changeStateButton;

    @FXML
    private ListView<Meal> mealsList;

    @FXML
    private Button backButton;


    @FXML
    public void initialize(Delivery delivery, boolean isManager) {
        this.delivery = delivery;
        this.isManager = isManager;
        idDeliveryLabel.setText(String.valueOf(delivery.getIdDelivery()));
        dateLabel.setText(String.valueOf(delivery.getDate()));
        idRestaurantLabel.setText(String.valueOf(delivery.getIdRestaurant()));
        idUserLabel.setText(String.valueOf(delivery.getIdUser()));
        stateChoiceBox.setValue(delivery.getState());
        stateChoiceBox.getItems().addAll(State.values());

        if(this.isManager){
            stateChoiceBox.setDisable(false);
            changeStateButton.setDisable(false);
        }
        else{
            stateChoiceBox.setDisable(true);
            changeStateButton.setDisable(true);
            changeStateButton.setVisible(false);
        }


        try {
            DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();
            List<Meal> meals = deliveryFacade.getAllMealOfDelivery(delivery.getIdDelivery());
            mealsList.getItems().addAll(meals);
        }
        catch(AccessDatabaseException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured while loading the meals, please retry later");
        }
    }


    @FXML
    public void changeState(ActionEvent actionEvent) {
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();
        try {
            String state = stateChoiceBox.getValue().toString().replace("_", " ");
            state = state.substring(0, 1).toUpperCase() + state.substring(1).toLowerCase();
            System.out.println(state);
            deliveryFacade.changeStateOfDelivery(delivery.getIdDelivery(), state);
            Display.showAlert(Alert.AlertType.INFORMATION, null, "Success", "The state of the delivery has been changed");
        }
        catch(AccessDatabaseException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured while changing the state of the delivery, please retry later");
        }
    }

    @FXML
    public void returnToList(ActionEvent actionEvent) {
        try{
            Window window = backButton.getScene().getWindow();
            DeliveryList deliveryList = new DeliveryList();
            deliveryList.start(new Stage());
            window.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
