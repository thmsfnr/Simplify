
package project.presentation.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.presentation.controller.payment.PaymentUserController;

/**
 * This class is used to test the methods of the payment user controller
 */
public class PaymentUserControllerTest {

    @Test
    void paymentUser() {
        PaymentUserController controller = new PaymentUserController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            controller.initialize();
        });
    }

}
