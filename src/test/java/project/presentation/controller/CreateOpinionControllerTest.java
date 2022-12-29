
package project.presentation.controller;

import javafx.event.ActionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the methods of the create opinion controller
 * @author Simplify members
 */
public class CreateOpinionControllerTest {

    @Test
    void createOpinion() {
        CreateOpinionController controller = new CreateOpinionController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            ActionEvent event = new ActionEvent();
            controller.create(event);
        });
    }

}
