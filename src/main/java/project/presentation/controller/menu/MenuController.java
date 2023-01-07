
package project.presentation.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import project.presentation.controller.cart.CartController;
import project.presentation.controller.notification.NotificationCenterController;
import project.presentation.controller.opinion.OpinionUserController;
import project.presentation.controller.payment.PaymentUserController;
import project.presentation.controller.reservation.ReservationController;
import project.presentation.controller.reservation.ReservationFormController;
import project.presentation.controller.restaurant.RestaurantListController;
import project.presentation.controller.user.PersonalAccountController;
import project.presentation.frame.cart.CartFrame;
import project.presentation.frame.notification.NotificationCenterFrame;
import project.presentation.frame.opinion.OpinionAdmin;
import project.presentation.frame.opinion.OpinionUser;
import project.presentation.frame.payment.PaymentAdmin;
import project.presentation.frame.payment.PaymentUser;
import project.presentation.frame.reservation.ReservationFormFrame;
import project.presentation.frame.reservation.ReservationFrame;
import project.presentation.frame.restaurant.RestaurantList;
import project.presentation.frame.user.AllAccounts;
import project.presentation.frame.user.Login;
import project.presentation.frame.user.PersonalAccount;
import project.utilities.UserStorage;

/**
 * Created by Simplify members on 07/01/23.
 * This class is the controller of the menu frame
 * @author Simplify members
 */
public class MenuController {

    private int idUser;
    private int idRole;

    @FXML
    private Button restaurantList;
    @FXML
    private Button reservationFrame;
    @FXML
    private Button paymentAdmin;
    @FXML
    private Button allAccounts;
    @FXML
    private Button notificationCenterFrame;
    @FXML
    private Button opinionAdmin;
    @FXML
    private Button opinionUser;
    @FXML
    private Button paymentUser;
    @FXML
    private Button reservationFormFrame;
    @FXML
    private Button personalAccount;
    @FXML
    private Button cartFrame;
    @FXML
    private Button logout;


    /**
     * This method is used to manage the start of the frame
     * If the user is not logged in, it will redirect him to the login frame
     */
    public void initialize() throws Exception {
        try {
            String user = UserStorage.load();
            String[] userArray = user.split(",");
            this.idUser = Integer.parseInt(userArray[0]);
            this.idRole = Integer.parseInt(userArray[1]);

            if (this.idRole == 1) { // User
                paymentAdmin.setVisible(false);
                allAccounts.setVisible(false);
                opinionAdmin.setVisible(false);
                paymentAdmin.setVisible(false);
            } else if (this.idRole == 2) { // Manager
                paymentAdmin.setVisible(false);
                cartFrame.setVisible(false);
                allAccounts.setVisible(false);
                opinionAdmin.setVisible(false);
                paymentUser.setVisible(false);
                paymentAdmin.setVisible(false);
            } else if (this.idRole == 3) { // Admin
                cartFrame.setVisible(false);
                paymentUser.setVisible(false);
            }

        } catch (Exception e) {
            return;
        }
    }



    /**
     * This method is used to switch the allAccounts frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToAllAccounts(ActionEvent event) throws Exception {
        Window owner = allAccounts.getScene().getWindow();
        AllAccounts allAccounts = new AllAccounts();
        allAccounts.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the opinionAdmin frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToOpinionAdmin(ActionEvent event) throws Exception {
        Window owner = opinionAdmin.getScene().getWindow();
        OpinionAdmin opinionAdmin = new OpinionAdmin();
        opinionAdmin.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the opinionUser frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToOpinionUser(ActionEvent event) throws Exception {
        OpinionUserController.setIdUser(this.idUser);
        Window owner = opinionUser.getScene().getWindow();
        OpinionUser opinionUser = new OpinionUser();
        opinionUser.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the reservationFrame frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToReservationFrame(ActionEvent event) throws Exception {
        ReservationController.setIdUser(this.idUser);
        Window owner = reservationFrame.getScene().getWindow();
        ReservationFrame reservationFrame = new ReservationFrame();
        reservationFrame.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the reservationFormFrame frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToReservationFormFrame(ActionEvent event) throws Exception {
        ReservationFormController.setIdUser(this.idUser);
        Window owner = reservationFormFrame.getScene().getWindow();
        ReservationFormFrame reservationFormFrame = new ReservationFormFrame();
        reservationFormFrame.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the notificationCenterFrame frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToNotificationCenterFrame(ActionEvent event) throws Exception {
        NotificationCenterController.setIdUser(this.idUser);
        Window owner = notificationCenterFrame.getScene().getWindow();
        NotificationCenterFrame notificationCenterFrame = new NotificationCenterFrame();
        notificationCenterFrame.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the paymentAdmin frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToPaymentAdmin(ActionEvent event) throws Exception {
        Window owner = paymentAdmin.getScene().getWindow();
        PaymentAdmin paymentAdmin = new PaymentAdmin();
        paymentAdmin.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the paymentUser frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToPaymentUser(ActionEvent event) throws Exception {
        PaymentUserController.setIdUser(this.idUser);
        Window owner = paymentUser.getScene().getWindow();
        PaymentUser paymentUser = new PaymentUser();
        paymentUser.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the restaurantList frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToRestaurantList(ActionEvent event) throws Exception {
        RestaurantListController.setIdUser(this.idUser);
        Window owner = restaurantList.getScene().getWindow();
        RestaurantList restaurantList = new RestaurantList();
        restaurantList.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the personalAccount frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToPersonalAccount(ActionEvent event) throws Exception {
        PersonalAccountController.setIdUser(this.idUser);
        Window owner = personalAccount.getScene().getWindow();
        PersonalAccount personalAccount = new PersonalAccount();
        personalAccount.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to switch the cartFrame frame
     * @param event the event of the switch button
     */
    @FXML
    private void switchToCartFrame(ActionEvent event) throws Exception {
        CartController.setIdUser(this.idUser);
        Window owner = cartFrame.getScene().getWindow();
        CartFrame cartFrame = new CartFrame();
        cartFrame.start(new Stage());
        owner.hide();
    }

    /**
     * This method is used to logout the user
     * @param event the event of the switch button
     */
    @FXML
    private void logout(ActionEvent event) throws Exception {
        UserStorage.delete();
        Window owner = logout.getScene().getWindow();
        Login login = new Login();
        login.start(new Stage());
        owner.hide();
    }

}
