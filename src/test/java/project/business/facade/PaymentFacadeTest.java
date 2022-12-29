package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.business.models.Payment;
import java.sql.Date;
import java.util.List;

/**
 * Created by Simplify members on 29/12/22.
 * This is the Test class of the facade of the payment
 * It is used to test the methods of the DAO to get the informations of the payment
 * @author Simplify members
 */
public class PaymentFacadeTest {

    @Test
    public void verify() throws InterruptedException {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();
        Date date = new Date(2022, 12, 29);
        Boolean result = paymentFacade.verify("165265265", date, "123","test");
        Assertions.assertTrue(result);
    }

    @Test
    public void createPayment_success() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();
        Payment payment = new Payment(7,"165265265","10$",new Date(System.currentTimeMillis()));
        Boolean result = paymentFacade.createPayment(payment);
        Assertions.assertTrue(result);
    }

    @Test
    public void createPayment_fail() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();
        Payment payment = new Payment(0,"165265265","10$",new Date(System.currentTimeMillis()));
        Boolean result = paymentFacade.createPayment(payment);
        Assertions.assertFalse(result);
    }

    @Test
    public void getAllPayment() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();
        List<Payment> payments = paymentFacade.getAllPayments();
        Assertions.assertTrue(payments.size() != 0);
    }

    @Test
    public void getPaymentOfUser_success() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();
        List<Payment> payments = paymentFacade.getAllPaymentsOfUser(7);
        Assertions.assertTrue(payments.size() != 0);
    }

    @Test
    public void getPaymentOfUser_fail() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();
        List<Payment> payments = paymentFacade.getAllPaymentsOfUser(0);
        Assertions.assertTrue(payments.size() == 0);
    }

    @Test
    public void deletePayment() {
        PaymentFacade paymentFacade = PaymentFacade.getInstance();
        Boolean result = paymentFacade.deletePayment(7);
        Assertions.assertTrue(result);
    }

}
