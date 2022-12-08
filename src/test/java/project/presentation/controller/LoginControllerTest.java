
package project.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginControllerTest {

    /**
     * This method is used to test the login method of the controller
     */
    @Test
    void login() {
        LoginController controller = new LoginController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.login(null);
        });
    }

}
