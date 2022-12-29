
package project.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import project.business.facade.OpinionFacade;
import project.business.facade.PaymentFacade;
import project.business.models.Opinion;
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

}
