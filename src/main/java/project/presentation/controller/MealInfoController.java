package project.presentation.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import project.business.facade.MealFacade;
import project.business.models.Meal;
import project.business.models.Opinion;


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

    private static int numMealSelec = 2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MealFacade mealFacade = MealFacade.getInstance();
        Meal meal = mealFacade.getById(numMealSelec);
        if (meal != null) {
            title.setText(meal.getTitle());
            description.setText(meal.getDescription());
            price.setText(String.valueOf(meal.getPrice()));
            description.setEditable(false);
            price.setEditable(false);
            //image.setImage(meal.getImage());
            ArrayList<Opinion> opinions = (ArrayList<Opinion>) mealFacade.getAllOpinionOfMeal(numMealSelec);
            if(opinions != null) {
                //create the container
                VBox layout = new VBox(opinions.size());
                for (Opinion opinion : opinions) {
                        Label userLabel = new Label(String.valueOf(opinion.getIdUser()));
                        TextArea description = new TextArea(opinion.getDescription());
                        description.setEditable(false);
                        layout.getChildren().addAll(userLabel, description);
                }
                anchor_opinion.getChildren().add(layout);
            }
        }
    }
    public void update(ActionEvent event) {
       //change scene to updateFrame
        System.out.println("update");
    }

    public void delete(ActionEvent event) {
        MealFacade mealFacade = MealFacade.getInstance();
        if(mealFacade.delete(numMealSelec)){
            System.out.println("Meal deleted");
        }
        else {
            System.out.println("Meal not deleted");
        }
    }
}
