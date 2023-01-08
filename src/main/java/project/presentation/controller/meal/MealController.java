package project.presentation.controller.meal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.MealFacade;
import project.business.models.Meal;
import project.presentation.frame.meal.MealFormFrame;
import project.presentation.frame.meal.MealInfoFrame;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MealController implements Initializable {
    @FXML
    private ListView liste_meal;
    @FXML
    private Button button_create;
    private String[] elementsSelected;

    /**
     * This method is used to initialize the frame with the list of the meals of the restaurant
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MealFacade mealFacade = MealFacade.getInstance();
        ArrayList<Meal> meals = null;
        try {
            meals = (ArrayList<Meal>) mealFacade.getAllMeal((Integer)LocalStorage.load("restaurant_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(meals != null) {
            for (Meal meal : meals) {
                liste_meal.getItems().add(meal.toString());
            }
        }
        else {
            liste_meal.getItems().add("No meal found");
        }
        //listener to get the selected meal
        liste_meal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           selection();
        });
    }

    /**
     * This method is used to detect the selected meal in the list view
     */
    @FXML
    private void selection(){
        Object clicked = liste_meal.getSelectionModel().getSelectedItem();
        elementsSelected = clicked.toString().split(" ");
        try {
            switchToInfoFrame(Integer.parseInt(elementsSelected[0]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method is used to switch to the meal create frame
     * @param event the event of the button
     * @throws Exception the exception
     */
    @FXML
    private void switchToCreateFrame(ActionEvent event) throws Exception {
        elementsSelected = null;
        // Get the window of the create button
        Window listeMealWindow = button_create.getScene().getWindow();

        MealFormFrame formCreate = new MealFormFrame();
        LocalStorage.write("isUpdate", false);
        formCreate.start(new Stage());

        // close the actual frame
        listeMealWindow.hide();
    }


    /**
     * This method is used to switch to the meal info frame
     * @param idMeal the id of the meal selected
     * @throws Exception the exception
     */
    @FXML
    private void switchToInfoFrame(int idMeal) throws Exception {
        elementsSelected = null;
        // Get the window of the create button
        Window listeMealWindow = button_create.getScene().getWindow();

        MealInfoFrame infoFrame = new MealInfoFrame();
        LocalStorage.write("meal_id", idMeal);
        infoFrame.start(new Stage());

        // close the actual frame
        listeMealWindow.hide();
    }
}
