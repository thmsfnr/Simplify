
package project.presentation.controller.opinion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.OpinionFacade;
import project.business.facade.PaymentFacade;
import project.business.models.Opinion;
import project.presentation.frame.menu.Menu;

import java.util.List;

/**
 * Created by Simplify members on 28/12/22.
 * This class is the controller of the OpinionAdminFrame
 * @author Simplify members
 */
public class OpinionAdminController {

    // Instance variables
    private List<Opinion> opinions;
    @FXML
    private ListView list;
    @FXML
    private Button back;

    /**
     * This method is used to initialize the list of opinions
     */
    public void initialize() {
        OpinionFacade opinionFacade = OpinionFacade.getInstance();

        list.getItems().clear();
        List<Opinion> opinions = opinionFacade.getAllOpinions();

        this.opinions = opinions;

        for (Opinion opinion : opinions) {
            list.getItems().add(opinion.toString());
        }
    }

    /**
     * This method is used to delete an opinion
     */
    public void delete() {
        Object clicked = list.getSelectionModel().getSelectedItem();
        String[] elements = clicked.toString().split(" ");

        for (Opinion opinion : opinions) {
            if (opinion.getIdOpinion() == Integer.parseInt(elements[0])) {
                OpinionFacade opinionFacade = OpinionFacade.getInstance();
                if (opinionFacade.deleteOpinion(opinion.getIdOpinion())) {
                    initialize();
                }
            }
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
