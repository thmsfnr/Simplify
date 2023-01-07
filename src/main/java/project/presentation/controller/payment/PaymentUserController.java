
package project.presentation.controller.payment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.PaymentFacade;
import project.business.models.Payment;
import project.presentation.controller.notification.NotificationCenterController;
import project.presentation.frame.menu.Menu;

import java.util.List;

/**
 * This class is the controller of the user payment consultation
 * It is used by a user to consult his payment historic
 * @author Simplify members
 */
public class PaymentUserController {

    // Instance variables
    private static int idUser;
    private List<Payment> payments;
    @FXML
    private ListView list;
    @FXML
    private Button back;

    /**
     * This method is used to pass the user id to the controller
     * @param idUser the id of the user
     */
    public static void setIdUser(int idUser) {
        PaymentUserController.idUser = idUser;
    }

    /**
     * This method is used to initialize the list of payments
     */
    public void initialize() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();

        int id = idUser;

        list.getItems().clear();
        List<Payment> payments = paymentFacade.getAllPaymentsOfUser(id);

        this.payments = payments;

        for (Payment payment : payments) {
            list.getItems().add(payment.toString());
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
