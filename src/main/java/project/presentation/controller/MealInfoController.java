package project.presentation.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.MealFacade;
import project.business.models.Meal;
import project.business.models.Opinion;
import project.presentation.frame.MealFormFrame;
import project.utilities.LocalStorage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MealInfoController implements Initializable {

    @FXML
    private Label title;

    @FXML
    private TextArea description;

    @FXML
    private TextField price;

    @FXML
    private ImageView image;

    @FXML
    private AnchorPane anchor_opinion;

    @FXML
    private Button button_update;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal = null;
        try {
            meal = mealFacade.getById((Integer)LocalStorage.load("meal_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (meal != null) {
            title.setText(meal.getTitle());
            description.setText(meal.getDescription());
            price.setText(String.valueOf(meal.getPrice()));
            description.setEditable(false);
            price.setEditable(false);
            //image.setImage(meal.getImage());
            ArrayList<Opinion> opinions = null;
            try {
                opinions = (ArrayList<Opinion>) mealFacade.getAllOpinionOfMeal((Integer)LocalStorage.load("meal_id"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(opinions != null) {
                //create the container
                VBox layout = new VBox(opinions.size());
                for (Opinion opinion : opinions) {
                        Label userLabel = new Label(String.valueOf(opinion.getIdUser()));
                        TextArea description = new TextArea(opinion.getComment());
                        description.setEditable(false);
                        layout.getChildren().addAll(userLabel, description);
                }
                anchor_opinion.getChildren().add(layout);
            }
        }
    }

    public void delete(ActionEvent event) {
        MealFacade mealFacade = MealFacade.getInstance();
        try {
            if(mealFacade.delete((Integer)LocalStorage.load("meal_id"))) {
                System.out.println("Meal deleted");
            }
            else {
                System.out.println("Meal not deleted");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
}
