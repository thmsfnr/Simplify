
package project.utilities;

import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * This class is used to verify the payment
 * @author Simplify members
 */
public class VerifyPayment {

    /**
     * This method is used to verify the payment
     * @param numberCard the number of the card
     * @param expirationDate the date of the payment
     * @param puk the puk of the card
     * @param nameOnCard the name of the card
     * @param amount the amount of the payment
     * @return true if the payment is verified, false otherwise
     */
    public static boolean verifyPayment(String numberCard, Date expirationDate, String puk, String nameOnCard, String amount) throws InterruptedException {
        sleep(2000);
        return true;
    }

}
