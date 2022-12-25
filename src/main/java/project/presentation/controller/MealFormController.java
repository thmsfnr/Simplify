package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import project.business.facade.MealFacade;
import project.business.models.Meal;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MealFormController implements Initializable {
    @FXML
    private Label label_success;
    @FXML
    private Label label_failure;
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private TextField price;
    @FXML
    private Button button_create;

    private Boolean isUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            //load variable to configure the form

            isUpdate = (Boolean) LocalStorage.load("isUpdate");
            if (isUpdate) {
                if(LocalStorage.load("meal_id") != null) {
                    MealFacade mealFacade = MealFacade.getInstance();
                    Meal meal = mealFacade.getById((Integer)LocalStorage.load("meal_id"));
                    if (meal != null) {
                        title.setText(meal.getTitle());
                        description.setText(meal.getDescription());
                        price.setText(String.valueOf(meal.getPrice()));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void submit_modification(ActionEvent event){
        if(title.getText().isEmpty() || price.getText().isEmpty()) {
            label_failure.setText("Please fill the fields title and price");
            label_success.setText("");
        }else{
            if(isUpdate){
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    create();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void create() throws IOException {
        MealFacade mealFacade = MealFacade.getInstance();
        //generate an id
        Meal meal = new Meal((Integer) LocalStorage.load("restaurant_id") ,description.getText(),title.getText(), Double.parseDouble(price.getText()));
        if(mealFacade.create(meal)){
            label_success.setText("Meal created successfully");
            label_failure.setText("");
        }else{
            label_failure.setText("Meal creation failed");
            label_success.setText("");
        }
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public void update() throws IOException {
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal = new Meal((Integer)LocalStorage.load("meal_id"),(Integer)LocalStorage.load("restaurant_id"), description.getText(),title.getText(), Double.parseDouble(price.getText()));
        System.out.println(meal);
        if(mealFacade.update(meal)){
            label_success.setText("Meal updated successfully");
            label_failure.setText("");
        }else{
            label_failure.setText("Meal update failed");
            label_success.setText("");
        }
    }

}
