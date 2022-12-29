
package project.persistence.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.business.models.Payment;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class is the PostGresPaymentDAOTest
 * It is used to test the methods of the DAO to get the informations of the payment
 * @author Simplify members
 */
public class PostGresPaymentDAOTest {

    @Test
    void connection_DB() {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        assertNotNull(connection);
    }

    @Test
    void create_success() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        Date date = new Date(2020, 12, 12);
        Payment payment = new Payment(7, "1564176", "10$", date);
        Boolean result = paymentDAO.create(payment);
        Assertions.assertTrue(result);
    }

    @Test
    void create_fail() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        Date date = new Date(2020, 12, 12);
        Payment payment = new Payment(0, "1564176", "10$", date);
        Boolean result = paymentDAO.create(payment);
        Assertions.assertFalse(result);
    }

    @Test
    void getAll() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        List<Payment> payments = paymentDAO.getAllPayments();
        Assertions.assertTrue(payments.size() != 0);
    }

    @Test
    void getPaymentOfUser_success() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        List<Payment> payments = paymentDAO.getAllPaymentsOfUser(7);
        Assertions.assertTrue(payments.size() != 0);
    }

    @Test
    void getPaymentOfUser_fail() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        List<Payment> payments = paymentDAO.getAllPaymentsOfUser(0);
        Assertions.assertTrue(payments.size() == 0);
    }

    @Test
    void delete() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        Boolean result = paymentDAO.delete(7);
        Assertions.assertTrue(result);
    }

}
