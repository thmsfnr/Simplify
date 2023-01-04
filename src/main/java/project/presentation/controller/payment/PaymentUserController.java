
package project.presentation.controller.payment;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import project.business.facade.PaymentFacade;
import project.business.models.Payment;
import java.util.List;

/**
 * This class is the controller of the user payment consultation
 * It is used by a user to consult his payment historic
 * @author Simplify members
 */
public class PaymentUserController {

    // Instance variables
    private List<Payment> payments;
    @FXML
    private ListView list;

    /**
     * This method is used to initialize the list of payments
     */
    public void initialize() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();

        int id = 7; // To modify later with the local storage use

        list.getItems().clear();
        List<Payment> payments = paymentFacade.getAllPaymentsOfUser(id);

        this.payments = payments;

        for (Payment payment : payments) {
            list.getItems().add(payment.toString());
        }
    }

}
