
package project.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Date;

/**
 * This class is used to test the methods of the verify payment class
 * @author Simplify members
 */
public class VerifyPaymentTest {

    @Test
    void verifyPayment() throws InterruptedException {
        Date date = new Date(2020, 12, 12);
        Boolean result = VerifyPayment.verifyPayment("1234567890123456", date, "122", "jhdsfjsdf","10$");
        Assertions.assertTrue(result);
    }

}
