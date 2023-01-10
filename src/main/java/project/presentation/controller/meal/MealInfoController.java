package project.presentation.controller.meal;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.MealFacade;
import project.business.models.Meal;
import project.business.models.Opinion;
import project.presentation.frame.meal.MealFormFrame;
import project.presentation.frame.meal.MealInfoFrame;
import project.presentation.frame.menu.Menu;
import project.utilities.Display;
import project.utilities.LocalStorage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



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

    private static int idMeal;

    private static int idRestaurant;
    public static void setIdMeal(int idMeal) {
        MealInfoController.idMeal = idMeal;
    }

    public static void setIdRestaurant(int idRestaurant) {
        MealInfoController.idRestaurant = idRestaurant;
    }


    /**
     * This method is used to initialize the frame with the information of the selected meal
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal = mealFacade.getById(idMeal);
        if (meal != null) {
            title_meal.setText(meal.getTitle());
            description.setText(meal.getDescription());
            price.setText(String.valueOf(meal.getPrice()));
            description.setEditable(false);
            price.setEditable(false);
            ArrayList<Opinion> opinions = null;
            opinions = (ArrayList<Opinion>) mealFacade.getAllOpinionOfMeal(idMeal);

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

        if(mealFacade.delete(idMeal)) {
            Display.infoBox("Meal deleted successfully", null, "Success");
        }
        else {
            Display.showAlert(Alert.AlertType.ERROR, w, "meal delete failed","Meal delete failed");
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
        MealFormController.setIsUpdate(true);
        MealFormController.setIdMeal(idMeal);
        MealFormController.setIdRestaurant(idRestaurant);
        formUpdate.start(new Stage());

        // close the actual frame
        listeMealWindow.hide();
    }

    /**
     * This method is used to back to the meal list frame
     * @param event the event of the button
     * @throws Exception the exception
     */
    public void go_back(ActionEvent event) throws Exception {
        Window owner = button_update.getScene().getWindow();
        project.presentation.frame.menu.Menu menu = new Menu();
        menu.start(new Stage());
        owner.hide();
    }

    @FXML
    public void addReservation(){

    }
}
