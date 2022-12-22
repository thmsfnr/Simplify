
package project.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
