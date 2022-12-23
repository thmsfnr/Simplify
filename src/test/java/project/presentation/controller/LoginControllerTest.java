
package project.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the methods of the login controller
 * @author Simplify members
 */
public class LoginControllerTest {

    @Test
    void login() {
        LoginController controller = new LoginController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.login(null);
        });
    }

}
