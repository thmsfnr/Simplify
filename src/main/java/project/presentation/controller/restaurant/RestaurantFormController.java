package project.presentation.controller.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.RestaurantFacade;
import project.business.models.Restaurant;
import project.business.models.User;
import project.exceptions.AccessDatabaseException;
import project.exceptions.UserNotFoundException;
import project.presentation.frame.restaurant.RestaurantList;
import project.utilities.Display;

import java.util.List;

/**
 * This class is the controller of the restaurant form component
 * @author Simplify members
 */
public class RestaurantFormController {

    private boolean isUpdate = false;
    private int idRestaurant;
    @FXML
    private TextField nameField;

    @FXML
    private TextArea addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private ChoiceBox<Integer> nbOfStarsChoice;

    @FXML
    private TextField emailField;

    @FXML
    private Spinner<Integer> lengthSpinner;

    @FXML
    private Spinner<Integer> widthSpinner;

    @FXML
    private ChoiceBox<String> managerChoice;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    /**
     * This method is called at the start of the frame
     */
    @FXML
    private void initialize() {
        RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();

        try{
            List<User> managers = restaurantFacade.getAllManagers();
            for(User manager : managers){
                managerChoice.getItems().add(manager.getEmail());
            }
        }
        catch(AccessDatabaseException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured in the back, retry later please");
        }


        // Initialize the choice box
        nbOfStarsChoice.getItems().addAll(1, 2, 3);
        nbOfStarsChoice.setValue(1);

        // Initialize the spinner
        lengthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        widthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }

    /**
     * This method is used to create a restaurant
     * @param event the event
     */
    @FXML
    public void create(ActionEvent event){

        Window owner = saveButton.getScene().getWindow();

        //Verification of the fields
        if (nameField.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a name");
            return;
        }
        if (addressField.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter an address");
            return;
        }
        if (phoneField.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a phone number");
            return;
        }
        if (emailField.getText().isEmpty()) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter an email");
            return;
        }
        if (managerChoice.getValue() == null) {
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please select a manager");
            return;
        }

        // Get the values of the fields
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        int nbOfStars = nbOfStarsChoice.getValue();
        String email = emailField.getText();
        int length = lengthSpinner.getValue();
        int width = widthSpinner.getValue();
        String manager = managerChoice.getValue();


        RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();

        try{
            User respo = restaurantFacade.getManagerByEmail(manager);
            Restaurant restaurant = new Restaurant(this.idRestaurant,name, address, nbOfStars, length, width, phone,email, respo.getId());
            if(isUpdate){
                restaurantFacade.updateRestaurant(restaurant);
            }
            else{
                restaurantFacade.createRestaurant(restaurant);
            }
        }
        catch(UserNotFoundException e){
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "The manager doesn't exist");
            return;
        }
        catch(AccessDatabaseException e){
            Display.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "An error occured in the back, retry later please");
            return;
        }

        String messageInfo = (isUpdate) ? "Restaurant updated successfully" : "Restaurant created successfully";
        Display.showAlert(Alert.AlertType.INFORMATION, owner, "Form Success!",
                messageInfo);

        // Close the window
        switchToList(owner);

    }

    /**
     * This method is used to switch to the restaurant list frame
     * @param owner the owner of the frame
     */
    private void switchToList(Window owner){
        try{
            RestaurantList list = new RestaurantList();
            list.start(new Stage());
            owner.hide();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to set up fields of a restaurant
     * @param restaurant a restaurant
     */
    public void setFields(Restaurant restaurant){
        this.isUpdate = true;
        this.idRestaurant = restaurant.getIdRestaurant();
        nameField.setText(restaurant.getName());
        addressField.setText(restaurant.getAddress());
        phoneField.setText(restaurant.getPhoneNumber());
        nbOfStarsChoice.setValue(restaurant.getNbOfStars());
        emailField.setText(restaurant.getEmail());
        lengthSpinner.getValueFactory().setValue(restaurant.getLength());
        widthSpinner.getValueFactory().setValue(restaurant.getWidth());

        try{
            RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
            User manager = restaurantFacade.getUserById(restaurant.getIdManager());
            managerChoice.setValue(manager.getEmail());
        }
        catch(UserNotFoundException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "Failed to find the manager. Retry later please");
        }
        catch(AccessDatabaseException e){
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured in the back, retry later please");
        }
    }

    /**
     * This method is used to go back to the restaurant list frame
     * @param actionEvent an action event
     */
    @FXML
    public void switchToList(ActionEvent actionEvent) {
        Window owner = backButton.getScene().getWindow();
        switchToList(owner);
    }

}
