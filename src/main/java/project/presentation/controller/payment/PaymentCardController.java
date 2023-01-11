
package project.presentation.controller.payment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.business.facade.PaymentFacade;
import project.business.models.Delivery;
import project.business.models.Meal;
import project.presentation.controller.delivery.DeliveryInfoController;
import project.presentation.frame.delivery.DeliveryInfo;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static project.utilities.Display.showAlert;

/**
 * Controller for the payment card form
 */
public class PaymentCardController {
    private Delivery delivery;
    private List<Meal> meals;
    private int idRole;

    @FXML
    private TextField nameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField expirationField;
    @FXML
    private TextField pukField;
    @FXML
    private Button createButton;

    /**
     * Create a new payment
     * @param event the event
     * @throws InterruptedException if the method is interrupted
     */
    public void create(ActionEvent event) throws InterruptedException {

        Window owner = createButton.getScene().getWindow();

        // if fields are empty show an alert
        if (nameField.getText().isEmpty() || numberField.getText().isEmpty() || expirationField.getText().isEmpty() || pukField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter all the fields");
            return;
        }

        PaymentFacade paymentFacade = PaymentFacade.getInstance();

        // convert the expiration date to a date
        Date expirationDate = Date.valueOf(expirationField.getText());

        // verify the payment
        if (paymentFacade.verify(nameField.getText(), expirationDate, numberField.getText(), pukField.getText())) {
            // Later it will redirect to an order resume page
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Payment success",
                    "Payment success");

            try {
                FXMLLoader loader = new FXMLLoader(DeliveryInfo.class.getResource("DeliveryInfoFrame.fxml"));
                Parent root = loader.load();
                DeliveryInfoController controller = loader.getController();
                controller.createDelivery(delivery, meals, idRole);
                Stage stage = new Stage();
                stage.setTitle("Delivery Info");
                stage.setScene(new Scene(root));
                stage.show();
                owner.hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Payment problem",
                    "Payment failed");
        }

    }


    @FXML
    public void initialize(Delivery delivery, List<Meal> meals, int idRole) {
        this.delivery = delivery;
        this.meals = meals;
        this.idRole = idRole;
    }

}
