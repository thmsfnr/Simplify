package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.MealFacade;
import project.business.models.Meal;
import project.presentation.frame.MealFormFrame;
import project.presentation.frame.MealInfoFrame;
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
    }

    @FXML
    public void selection(){
        Object clicked = liste_meal.getSelectionModel().getSelectedItem();
        String[] elements = clicked.toString().split(" ");
        try {
            switchToInfoFrame(Integer.parseInt(elements[0]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void switchToCreateFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window listeMealWindow = button_create.getScene().getWindow();

        MealFormFrame formCreate = new MealFormFrame();
        LocalStorage.write("isUpdate", false);
        formCreate.start(new Stage());

        // close the actual frame
        listeMealWindow.hide();
    }

    @FXML
    private void switchToInfoFrame(int idMeal) throws Exception {
        // Get the window of the create button
        Window listeMealWindow = button_create.getScene().getWindow();

        MealInfoFrame infoFrame = new MealInfoFrame();
        LocalStorage.write("meal_id", idMeal);
        infoFrame.start(new Stage());

        // close the actual frame
        listeMealWindow.hide();
    }

}
