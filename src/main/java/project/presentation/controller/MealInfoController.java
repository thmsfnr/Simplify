package project.presentation.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.MealFacade;
import project.business.models.Meal;
import project.business.models.Opinion;
import project.presentation.frame.MealFormFrame;
import project.presentation.frame.MealInfoFrame;
import project.utilities.Display;
import project.utilities.LocalStorage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static project.presentation.controller.Display.infoBox;

public class MealInfoController implements Initializable {

    @FXML
    private Text title_meal;

    @FXML
    private TextArea description;

    @FXML
    private TextField price;

    @FXML
    private AnchorPane anchor_opinion;

    @FXML
    private Button button_update;

    /**
     * This method is used to initialize the frame with the information of the selected meal
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal;
        try {
            meal = mealFacade.getById((Integer)LocalStorage.load("meal_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (meal != null) {
            title_meal.setText(meal.getTitle());
            description.setText(meal.getDescription());
            price.setText(String.valueOf(meal.getPrice()));
            description.setEditable(false);
            price.setEditable(false);
            ArrayList<Opinion> opinions = null;
            try {
                opinions = (ArrayList<Opinion>) mealFacade.getAllOpinionOfMeal((Integer)LocalStorage.load("meal_id"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(opinions != null) {
                ListView<Opinion> listView = new ListView<>();
                listView.getItems().addAll(opinions);
                anchor_opinion.getChildren().add(listView);
            }
        }
    }

    /**
     * This method is used to delete a meal
     * @param event the event of the button
     */
    @FXML
    public void delete(ActionEvent event) {
        MealFacade mealFacade = MealFacade.getInstance();
        Window w = button_update.getScene().getWindow();
        try {
            if(mealFacade.delete((Integer)LocalStorage.load("meal_id"))) {
                infoBox("Meal deleted successfully", null, "Success");
            }
            else {
                Display.showAlert(Alert.AlertType.ERROR, w, "meal delete failed","Meal delete failed");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to switch to the update meal frame
     * @param event the event of the button
     * @throws Exception the exception
     */
    @FXML
    private void switchToUpdateFrame(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window listeMealWindow = button_update.getScene().getWindow();

        MealFormFrame formUpdate = new MealFormFrame();
        LocalStorage.write("isUpdate", true);
        formUpdate.start(new Stage());

        // close the actual frame
        listeMealWindow.hide();
    }

    /**
     * This method is used to back to the meal list frame
     * @param event the event of the button
     * @throws Exception the exception
     */
    @FXML
    private void go_back(ActionEvent event) throws Exception {
        // Get the window of the create button
        Window mealWindow = button_update.getScene().getWindow();
        MealInfoFrame mealInfoFrame = new MealInfoFrame();
        mealInfoFrame.start(new Stage());

        // close the actual frame
        mealWindow.hide();
    }
}
