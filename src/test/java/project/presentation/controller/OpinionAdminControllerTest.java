
package project.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.presentation.controller.opinion.OpinionAdminController;

/**
 * This class is used to test the methods of the opinion admin controller
 * @author Simplify members
 */
public class OpinionAdminControllerTest {

    @Test
    void opinionAdmin() {
        OpinionAdminController controller = new OpinionAdminController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
    }

}
