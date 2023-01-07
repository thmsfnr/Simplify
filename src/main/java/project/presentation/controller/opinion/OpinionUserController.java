
package project.presentation.controller.opinion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.OpinionFacade;
import project.business.models.Opinion;
import project.presentation.controller.notification.NotificationCenterController;
import project.presentation.frame.menu.Menu;
import project.utilities.Display;

import java.util.List;

/**
 * Created by Simplify members on 28/12/22.
 * This class is the controller of the OpinionUserFrame
 * @author Simplify members
 */
public class OpinionUserController {

    // Instance variables
    private static int idUser;
    private List<Opinion> opinions;
    private Opinion selectedOpinion;
    @FXML
    private ListView list;
    @FXML
    private TextArea content;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button back;

    /**
     * This method is used to pass the user id to the controller
     * @param idUser the id of the user
     */
    public static void setIdUser(int idUser) {
        OpinionUserController.idUser = idUser;
    }

    /**
     * This method is used to load opinions
     */
    public void initialize() {
        OpinionFacade opinionFacade = OpinionFacade.getInstance();

        list.getItems().clear();
        List<Opinion> opinions = opinionFacade.getAllOpinionsOfUser(idUser);

        this.opinions = opinions;

        for (Opinion opinion : opinions) {
            list.getItems().add(opinion.toString());
        }
    }

    /**
     * This method is used to load the selected opinion
     */
    public void display() {
        Object clicked = list.getSelectionModel().getSelectedItem();
        String[] elements = clicked.toString().split(" ");

        for (Opinion opinion : opinions) {
            if (opinion.getIdOpinion() == Integer.parseInt(elements[0])) {
                this.selectedOpinion = opinion;
                content.setText(opinion.getComment());
            }
        }
    }

    /**
     * This method is used to manage the event of the update button
     * @param event the event of the update button
     */
    public void update(ActionEvent event) {
        Window w = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // if each field is not empty
        if (!content.getText().isEmpty()) {
            OpinionFacade opinionFacade = OpinionFacade.getInstance();

            this.selectedOpinion.setComment(content.getText());

            if (opinionFacade.updateOpinion(this.selectedOpinion)) {
                Display.showAlert(Alert.AlertType.INFORMATION, w, "Update successful","The opinion has been updated");
                initialize();
                content.clear();
                this.selectedOpinion = null;
            } else {
                Display.showAlert(Alert.AlertType.ERROR, w, "Update failed","The opinion has not been updated");
            }
        } else {
            Display.showAlert(Alert.AlertType.ERROR, w, "Update failed", "Please fill the content");
        }
    }

    /**
     * This method is used to manage the event of the delete button
     */
    public void delete() {
        Window w = deleteButton.getScene().getWindow();
        OpinionFacade opinionFacade = OpinionFacade.getInstance();

        if (opinionFacade.deleteOpinion(this.selectedOpinion.getIdOpinion())) {
            Display.showAlert(Alert.AlertType.INFORMATION, w, "Delete successful","The opinion has been deleted");
            initialize();
            content.clear();
            this.selectedOpinion = null;
        } else {
            Display.showAlert(Alert.AlertType.ERROR, w, "Delete failed","The opinion has not been deleted");
        }
    }

    /**
     * This method is used to manage the event of the back button
     * @param event the event of the back button
     */
    public void backToMenu(ActionEvent event) throws Exception {
        Window owner = back.getScene().getWindow();
        project.presentation.frame.menu.Menu menu = new Menu();
        menu.start(new Stage());
        owner.hide();
    }

}
