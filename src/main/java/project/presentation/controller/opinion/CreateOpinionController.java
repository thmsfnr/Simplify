
package project.presentation.controller.opinion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.OpinionFacade;
import project.business.models.Opinion;
import project.business.models.Restaurant;
import project.business.models.User;
import project.presentation.controller.restaurant.RestaurantInfosController;
import project.presentation.frame.menu.Menu;
import project.presentation.frame.restaurant.RestaurantInfos;

import static project.utilities.Display.showAlert;

/**
 * Controller for the opinion form
 */
public class CreateOpinionController {

    @FXML
    private TextArea content;
    @FXML
    private Button createButton;

    private static int idUser;

    public static void setIdUser(int idUser) {
        CreateOpinionController.idUser = idUser;
    }
    /**
     * Create a new opinion
     * @param event the event
     * @throws InterruptedException if the method is interrupted
     */
    public void create(ActionEvent event) throws InterruptedException {

        Window owner = createButton.getScene().getWindow();

        // if fields are empty show an alert
        if (content.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a content");
            return;
        }

        OpinionFacade opinionFacade = OpinionFacade.getInstance();

        // Create the opinion
        Opinion opinion = new Opinion(idUser, content.getText());

        // Add the opinion to the database
        if (opinionFacade.createOpinion(opinion)) {
            // Later it will redirect to the previous page
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Opinion success",
                    "Opinion success");

        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Opinion problem",
                    "Opinion failed");
        }

    }

    /**
     * This method is used to manage the event of the back button
     * @param event the event of the back button
     */
    public void backToMenu(ActionEvent event) throws Exception {
        Window owner = createButton.getScene().getWindow();
        project.presentation.frame.menu.Menu menu = new Menu();
        menu.start(new Stage());
        owner.hide();
    }

}
