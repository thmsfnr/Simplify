package project.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import project.business.facade.MealFacade;
import project.business.models.Meal;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MealController implements Initializable {

    @FXML
    private ListView liste_meal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MealFacade mealFacade = MealFacade.getInstance();
        ArrayList<Meal> meals = (ArrayList<Meal>) mealFacade.getAllMeal(1);
        if(meals != null) {
            for (Meal meal : meals) {
                liste_meal.getItems().add(meal.getTitle());
            }
        }
        else {
            liste_meal.getItems().add("No meal found");
        }
    }
}
