
package project.presentation.controller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.presentation.controller.user.SigninController;

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
