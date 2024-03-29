
package project.presentation.controller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.presentation.controller.user.PersonalAccountController;

/**
 * This class is used to test the methods of the personal account controller
 */
public class PersonalAccountControllerTest {

    @Test
    void personalAccount() {
        PersonalAccountController controller = new PersonalAccountController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
    }

}
