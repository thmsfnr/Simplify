package project.presentation.controller.cart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import project.presentation.frame.cart.CartFrame;

import java.io.IOException;

/**
 * Created by Simplify members on 02/01/22.
 * This class is the controller of the CartFrame
 * @author Simplify members
 */
public class CartController implements Initializable {

    // Instance variables
    @FXML
    private Button cartBtn;

    @FXML
    private AnchorPane contentArea;

    @FXML
    private ScrollPane cartContentArea;

    @FXML
    private Button reservationCartBtn;

    @FXML
    private Button deliveryCartBtn;

    private Boolean isVisible = false;

    /**
     * This method is used to initialize the cart frame
     * it will load the reservation cart first and it can be changed to the delivery cart
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        // switch to ReservationCartFrame
        this.isVisible = true;
        try {
            AnchorPane pane = FXMLLoader.load(CartFrame.class.getResource("CartReservationComponent.fxml"));
            cartContentArea.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to switch to the DeliveryCartFrame
     */
    @FXML
    public void switchToDeliveryCartFrame() {
        try {
            AnchorPane pane = FXMLLoader.load(CartFrame.class.getResource("CartDeliveryComponent.fxml"));
            cartContentArea.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to switch to the ReservationCartFrame
     */
    @FXML
    public void switchToReservationCartFrame() {
        try {
            AnchorPane pane = FXMLLoader.load(CartFrame.class.getResource("CartReservationComponent.fxml"));
            cartContentArea.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to show the cart frame
     * On click on the Cart button
     */
    @FXML
    public void showCartContent() {
        if (isVisible) {
            contentArea.setVisible(false);
            isVisible = false;
        } else {
            contentArea.setVisible(true);
            isVisible = true;
        }
    }
}
