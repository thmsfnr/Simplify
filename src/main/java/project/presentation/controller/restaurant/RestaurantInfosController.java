package project.presentation.controller.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.RestaurantFacade;
import project.business.models.Opinion;
import project.business.models.Restaurant;
import project.business.models.User;
import project.exceptions.AccessDatabaseException;
import project.exceptions.UserNotFoundException;
import project.presentation.controller.meal.MealController;
import project.presentation.controller.opinion.CreateOpinionController;
import project.presentation.controller.placement.PlacementController;
import project.presentation.controller.reservation.ReservationController;
import project.presentation.frame.meal.MealFrame;
import project.presentation.frame.opinion.CreateOpinion;
import project.presentation.frame.placement.Placement;
import project.presentation.frame.reservation.ReservationFrame;
import project.presentation.frame.restaurant.RestaurantList;
import project.utilities.Display;

import java.util.List;

public class RestaurantInfosController {


    private Restaurant restaurant;
    private User manager;
    private int idUser;
    private int idRole;

    @FXML
    private Label nameOutput;
    @FXML
    private Label addressOutput;
    @FXML
    private Label phoneOutput;
    @FXML
    private Label emailOutput;
    @FXML
    private Label managerOutput;
    @FXML
    private Label nbOfStarsOutput;
    @FXML
    private Label widthOutput;
    @FXML
    private Label lengthOutput;
    @FXML
    private Label nbOfTablesOutput;
    @FXML
    private ListView<String> opinionList;
    @FXML
    private Button backButton;

    /**
     * This method is used at the start of the frame
     * @param restaurant the restaurant to display
     * @param idUser the id of the user
     * @param idRole the id of the role
     */
    public void initialize(Restaurant restaurant, int idUser, int idRole) {
        this.idUser = idUser;
        this.idRole = idRole;
        RestaurantFacade restaurantFacade = RestaurantFacade.getInstance();
        this.restaurant = restaurant;
        try {
            this.manager = restaurantFacade.getUserById(restaurant.getIdManager());
            managerOutput.setText(manager.getName()+" "+manager.getFirstname());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "The manager doesn't exist");
        } catch (AccessDatabaseException e) {
            e.printStackTrace();
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured in the back, retry later please");
        }

        try{
            List<Opinion> opinions = restaurantFacade.getOpinionsOfRestaurant(restaurant.getIdRestaurant());
            for(Opinion opinion : opinions){
                opinionList.getItems().add("idUser :"+opinion.getIdUser()+" and the comment is: "+ opinion.getComment());
            }
        }
        catch (AccessDatabaseException e){
            e.printStackTrace();
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured in the back, retry later please");
        }

        try{
            int numberTables = restaurantFacade.countOfTablesOfRestaurant(restaurant.getIdRestaurant());
            nbOfTablesOutput.setText(String.valueOf(numberTables));
        }
        catch (AccessDatabaseException e){
            e.printStackTrace();
            Display.showAlert(Alert.AlertType.ERROR, null, "Error", "An error occured in the back, retry later please");
        }

        nameOutput.setText(restaurant.getName());
        addressOutput.setText(restaurant.getAddress());
        phoneOutput.setText(restaurant.getPhoneNumber());
        emailOutput.setText(restaurant.getEmail());
        nbOfStarsOutput.setText(String.valueOf(restaurant.getNbOfStars()));
        widthOutput.setText(String.valueOf(restaurant.getWidth()));
        lengthOutput.setText(String.valueOf(restaurant.getLength()));
    }

    /**
     * This method is used to go back to the restaurant list
     * @param event the event of the button
     */
    @FXML
    public void switchToList(ActionEvent event){
        try{
            Window window = backButton.getScene().getWindow();
            RestaurantList list = new RestaurantList();
            list.start(new Stage());
            window.hide();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void switchToReservationFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window restaurantInfoWindow = backButton.getScene().getWindow();
        ReservationFrame reservationFrame = new ReservationFrame();
        ReservationController.setIdUser(this.manager.getId());
        ReservationController.setIdRole(this.manager.getRole());
        ReservationController.setIdRestaurant(this.restaurant.getIdRestaurant());
        reservationFrame.start(new Stage());

        // close the actual frame
        restaurantInfoWindow.hide();
    }

    @FXML
    public void switchToCreateOpinionFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window restaurantInfoWindow = backButton.getScene().getWindow();
        CreateOpinion createOpinionFrame = new CreateOpinion();
        CreateOpinionController.setIdUser(this.idUser);
        createOpinionFrame.start(new Stage());
        // close the actual frame
        restaurantInfoWindow.hide();
    }

    @FXML
    public void switchToPlacementFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window restaurantInfoWindow = backButton.getScene().getWindow();
        Placement placementFrame = new Placement();
        PlacementController.setIsReservation(false);
        PlacementController.setIdRestaurant(this.restaurant.getIdRestaurant());
        placementFrame.start(new Stage());

        // close the actual frame
        restaurantInfoWindow.hide();
    }

    @FXML
    public void switchToMealFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window restaurantInfoWindow = backButton.getScene().getWindow();
        MealFrame mealFrame = new MealFrame();
        MealController.setIdRestaurant(this.restaurant.getIdRestaurant());
        mealFrame.start(new Stage());

        // close the actual frame
        restaurantInfoWindow.hide();
    }
}
