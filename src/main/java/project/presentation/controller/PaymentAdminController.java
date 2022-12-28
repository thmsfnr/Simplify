
package project.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import project.business.facade.PaymentFacade;
import project.business.models.Payment;
import java.util.List;

/**
 * This class is the controller of the payment historic
 * It is used by an admin to consult all the payment historic
 * @author Simplify members
 */
public class PaymentAdminController {

    // Instance variables
    private List<Payment> payments;
    @FXML
    private ListView list;

    /**
     * This method is used to initialize the list of payments
     */
    public void initialize() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();

        list.getItems().clear();
        List<Payment> payments = paymentFacade.getAllPayments();

        this.payments = payments;

        for (Payment payment : payments) {
            list.getItems().add(payment.toString());
        }
    }

    /**
     * This method is used to delete a payment
     */
    public void delete() {
        Object clicked = list.getSelectionModel().getSelectedItem();
        String[] elements = clicked.toString().split(" ");

        for (Payment payment : payments) {
            if (payment.getId() == Integer.parseInt(elements[0])) {
                PaymentFacade paymentFacade = PaymentFacade.getInstance();
                if (paymentFacade.deletePayment(payment.getId())) {
                    initialize();
                }
            }
        }
    }

}
