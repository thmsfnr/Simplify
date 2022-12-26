
package project.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the methods of the signin controller
 * @author Simplify members
 */
public class SigninControllerTest {

    @Test
    void signin() {
        SigninController controller = new SigninController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.signin(null);
        });
    }

}
