
package project.business.models;

import java.sql.Date;

/**
 * This class is the model of the payment
 * @author Simplify members
 */
public class Payment {

    private int idPayment;
    private int idUser;
    private String cardNumber;
    private int idOrder;
    private String amount;
    private Date date;

    /**
     * Constructor of the class Payment
     * @param idPayment the id of the payment
     * @param idUser the id of the user
     * @param cardNumber the card number of the user
     * @param idOrder the id of the order
     * @param amount the amount of the payment
     * @param date the date of the payment
     */
    public Payment(int idPayment, int idUser, String cardNumber, int idOrder, String amount, Date date) {
        this.idPayment = idPayment;
        this.idUser = idUser;
        this.cardNumber = cardNumber;
        this.idOrder = idOrder;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Constructor of the class Payment without some parameters
     * @param idUser the id of the user
     * @param cardNumber the card number of the user
     * @param amount the amount of the payment
     * @param date the date of the payment
     */
    public Payment(int idUser, String cardNumber, String amount, Date date) {
        this.idUser = idUser;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Getter of the id of the payment
     * @return the id of the payment
     */
    public int getId() {
        return this.idPayment;
    }

    /**
     * Getter of the id of the user
     * @return the id of the user
     */
    public int getIdUser() {
        return this.idUser;
    }

    /**
     * Getter of the card number of the user
     * @return the card number of the user
     */
    public String getCardNumber() {
        return this.cardNumber;
    }

    /**
     * Getter of the id of the order
     * @return the id of the order
     */
    public int getIdOrder() {
        return this.idOrder;
    }

    /**
     * Getter of the amount of the payment
     * @return the amount of the payment
     */
    public String getAmount() {
        return this.amount;
    }

    /**
     * Getter of the date of the payment
     * @return the date of the payment
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Setter of the id of the payment
     * @param idPayment the id of the payment
     */
    public void setId(int idPayment) {
        this.idPayment = idPayment;
    }

    /**
     * Setter of the id of the user
     * @param idUser the id of the user
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Setter of the card number of the user
     * @param cardNumber the card number of the user
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Setter of the id of the order
     * @param idOrder the id of the order
     */
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    /**
     * Setter of the amount of the payment
     * @param amount the amount of the payment
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Setter of the date of the payment
     * @param date the date of the payment
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
