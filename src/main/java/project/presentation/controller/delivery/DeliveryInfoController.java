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
import project.presentation.frame.menu.Menu;
import project.utilities.Display;

import java.util.List;

/**
 * This class is the controller of the delivery info component
 * It is used to display the delivery info
 * @author Simplify members
 */
public class DeliveryInfoController {

    private Delivery delivery;
    private int idRole;
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

    /**
     * This method is called at the start of the frame
     * @param delivery the delivery
     * @param idRole the role
     * @param meals a list of meals
     */
    @FXML
    public void initialize(Delivery delivery, int idRole, List<Meal> meals) {
        this.delivery = delivery;
        this.idRole = idRole;
        idDeliveryLabel.setText(String.valueOf(delivery.getIdDelivery()));
        dateLabel.setText(String.valueOf(delivery.getDate()));
        idRestaurantLabel.setText(String.valueOf(delivery.getIdRestaurant()));
        idUserLabel.setText(String.valueOf(delivery.getIdUser()));
        stateChoiceBox.setValue(delivery.getState());
        stateChoiceBox.getItems().addAll(State.values());

        if(this.idRole != 1) {
            stateChoiceBox.setDisable(false);
            changeStateButton.setDisable(false);
        }
        else{
            stateChoiceBox.setDisable(true);
            changeStateButton.setDisable(true);
            changeStateButton.setVisible(false);
        }

        try {
            if(meals == null){
                DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();
                meals = deliveryFacade.getAllMealOfDelivery(delivery.getIdDelivery());
            }
            mealsList.getItems().addAll(meals);
        }
        catch(AccessDatabaseException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured while loading the meals, please retry later");
        }
    }

    /**
     * This method is used to change tha stat of a delivery
     * @param actionEvent the event
     */
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

    /**
     * This method is used to go back to the delivery list
     * @param actionEvent the event
     */
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

    /**
     * This method is used to go back to the menu
     * @param event the event
     */
    public void returnToMenu(ActionEvent event) {
        Window window = backButton.getScene().getWindow();
        try{
            Menu menu = new Menu();
            menu.start(new Stage());
        }
        catch(Exception e) {
            e.printStackTrace();
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured while loading the menu, please retry later");
        }
        finally {
            window.hide();
        }
    }

    /**
     * allows to create the delivery and display his informations
     * @param delivery
     * @param meals
     */
    public void createDelivery(Delivery delivery, List<Meal> meals, int idRole){
        DeliveryFacade deliveryFacade = DeliveryFacade.getInstance();
        try {
            deliveryFacade.createDelivery(delivery, meals);
            initialize(delivery, idRole, meals);
        }
        catch(AccessDatabaseException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured while creating the delivery, please retry later");
        }
        backButton.setOnAction(this::returnToMenu);
    }

}
