
package project.presentation.controller.payment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.PaymentFacade;
import project.business.models.Payment;
import project.presentation.frame.menu.Menu;

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
    @FXML
    private Button back;

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

    /**
     * This method is used to manage the event of the back button
     * @param event the event of the back button
     */
    public void backToMenu(ActionEvent event) throws Exception {
        Window owner = back.getScene().getWindow();
        project.presentation.frame.menu.Menu menu = new Menu();
        menu.start(new Stage());
        owner.hide();
    }

}
