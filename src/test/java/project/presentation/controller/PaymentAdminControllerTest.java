
package project.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the methods of the payment admin controller
 */
public class PaymentAdminControllerTest {

    @Test
    void paymentAdmin() {
        PaymentAdminController controller = new PaymentAdminController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
    }
}
