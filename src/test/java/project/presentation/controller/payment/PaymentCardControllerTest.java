
package project.presentation.controller.payment;

import javafx.event.ActionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.presentation.controller.payment.PaymentCardController;

/**
 * This class is used to test the methods of the payment card controller
 * @author Simplify members
 */
public class PaymentCardControllerTest {

    @Test
    void paymentCard() {
        PaymentCardController controller = new PaymentCardController();

        Assertions.assertThrows(NullPointerException.class, () -> {
            ActionEvent event = new ActionEvent();
            controller.create(event);
        });
    }

}
