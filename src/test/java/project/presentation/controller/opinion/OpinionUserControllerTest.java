
package project.presentation.controller.opinion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.presentation.controller.opinion.OpinionUserController;

/**
 * This class is used to test the methods of the opinion user controller
 * @author Simplify members
 */
public class OpinionUserControllerTest {

    @Test
    void opinionUser() {
        OpinionUserController controller = new OpinionUserController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
    }

}
