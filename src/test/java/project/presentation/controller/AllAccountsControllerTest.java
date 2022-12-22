
package project.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the methods of the all accounts controller
 * @author Simplify members
 */
public class AllAccountsControllerTest {

    @Test
    void allAccounts() {
        AllAccountsController controller = new AllAccountsController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
    }

}
