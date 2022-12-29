
package project.business.facade;

import project.business.models.Payment;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.PaymentDAO;
import project.utilities.VerifyPayment;
import java.sql.Date;
import java.util.List;

/**
 * Created by Simplify members on 27/12/22.
 * This interface is the facade of the payment
 * It is used to manage the payment
 * @author Simplify members
 */
public class PaymentFacade {

    // Instance variables
    private static PaymentFacade instance;
    private PaymentDAO paymentDAO;

    /**
     * Constructor of the class PaymentFacade
     */
    private PaymentFacade() {
        // Get the DAO
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        // Get the UserDAO
        this.paymentDAO = factory.getPaymentDAO();
    }

    /**
     * This method is used to get the instance of the class PaymentFacade
     * @return the instance of the class PaymentFacade
     */
    public static PaymentFacade getInstance() {
        if (instance == null) {
            instance = new PaymentFacade();
        }
        return instance;
    }

    /**
     * This method is used to verify the payment
     * @param numberCard the number of the card
     * @param expirationDate the expiration date of the card
     * @param puk the puk of the card
     * @param nameOnCard the name on the card
     * @return true if the payment is verified, false otherwise
     */
    public boolean verify(String numberCard, Date expirationDate, String puk, String nameOnCard) throws InterruptedException {
        String amount = "10$";
        if (VerifyPayment.verifyPayment(numberCard, expirationDate, puk, nameOnCard, amount)) {

            // Create the payment (to complete later with the use of the local storage)
            Payment payment = new Payment(7,numberCard,amount,new Date(System.currentTimeMillis()));
            if (createPayment(payment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to create a payment
     * @param payment the payment to create
     * @return true if the payment is created, false otherwise
     */
    private boolean createPayment(Payment payment) {
        return paymentDAO.create(payment);
    }

    /**
     * This method is used to delete a payment
     * @param id the id of the payment to delete
     * @return true if the payment is deleted, false otherwise
     */
    public boolean deletePayment(int id) {
        return paymentDAO.delete(id);
    }

    /**
     * This method is used to get all the payments
     * @return the list of all the payments
     */
    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }

    /**
     * This method is used to get all the payments of a user
     * @param id the id of the user
     * @return the list of the payments of the user
     */
    public List<Payment> getAllPaymentsOfUser(int id) {
        return paymentDAO.getAllPaymentsOfUser(id);
    }

}
