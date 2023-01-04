
package project.persistence.product.abstr;

import project.business.models.Payment;
import java.util.List;

/**
 * Created by Simplify members on 27/12/22.
 * This interface is the DAO of the payment
 * It is used to call the methods of the database to get the informations of the payment
 * @author Simplify members
 */
public abstract class PaymentDAO {

    /**
     * This method is used to create a payment
     * @param payment the payment to create
     * @return true if the payment is created, false otherwise
     */
    public abstract Boolean create(Payment payment);

    /**
     * This method is used to delete a payment
     * @param id the id of the payment to delete
     * @return true if the payment is deleted, false otherwise
     */
    public abstract Boolean delete(int id);

    /**
     * This method is used to get all the payments
     * @return the list of the payments
     */
    public abstract List<Payment> getAllPayments();

    /**
     * This method is used to get all the payments of a user
     * @param id the id of the user
     * @return the list of the payments of the user
     */
    public abstract List<Payment> getAllPaymentsOfUser(int id);
}
