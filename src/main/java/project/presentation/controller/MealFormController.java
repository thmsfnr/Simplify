package project.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.MealFacade;
import project.business.models.Meal;
import project.presentation.frame.MealFrame;
import project.utilities.Display;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MealFormController implements Initializable {
    @FXML
    private Label label_failure;
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private TextField price;
    @FXML
    private Button button_submit;

    private Boolean isUpdate;

    /**
     * This method is used to initialize the frame with the information of the meal if it is an update
     * @param url the url
     * @param resourceBundle the resource bundle
     */
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

    /**
     * This method is used to submit the form
     * @param event the event of the button
     */
    @FXML
    public void submit_modification(ActionEvent event){
        if(title.getText().isEmpty() || price.getText().isEmpty()) {
            label_failure.setText("Please fill the fields title and price");
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

    /**
     * This method is used to create a meal
     * @throws IOException
     */
    public void create() throws IOException {
        MealFacade mealFacade = MealFacade.getInstance();
        Window w = button_submit.getScene().getWindow();
        //generate an id
        Meal meal = new Meal((Integer) LocalStorage.load("restaurant_id") ,description.getText(),title.getText(), Double.parseDouble(price.getText()));
        if(mealFacade.create(meal)){
            Display.showAlert(Alert.AlertType.INFORMATION, w, "create successful","Meal created successfully");
            try {
            switchToMealFrame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            Display.showAlert(Alert.AlertType.ERROR, w, "create failed","Meal create failed");
        }

    }

    /**
     * This method is used to update a meal
     * @throws IOException
     */
    public void update() throws IOException {
        MealFacade mealFacade = MealFacade.getInstance();
        Window w = button_submit.getScene().getWindow();
        Meal meal = new Meal((Integer)LocalStorage.load("meal_id"),(Integer)LocalStorage.load("restaurant_id"), description.getText(),title.getText(), Double.parseDouble(price.getText()));
        if(mealFacade.update(meal)){
            Display.showAlert(Alert.AlertType.INFORMATION, w, "Update successful","Meal updated successfully");
        }else{
            Display.showAlert(Alert.AlertType.ERROR, w, "Update failed","Meal update failed");
        }
        try {
            switchToMealFrame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to switch to the meal frame
     * @throws Exception if the frame is not found
     */
    @FXML
    private void switchToMealFrame() throws Exception {
        // Get the window of the create button
        Window mealFormWindow = button_submit.getScene().getWindow();

        MealFrame mealFrame = new MealFrame();
        mealFrame.start(new Stage());

        // close the actual frame
        mealFormWindow.hide();
    }

}
