package project.persistence.product.abstr;

import project.business.models.Delivery;
import project.exceptions.AccessDatabaseException;

import java.util.List;

/**
 * Created by Simplify members on 02/01/23.
 * This interface is the DAO of the delivery
 * It is used to call the methods of the database to get the informations of the delivery
 * @author Simplify members
 */
public abstract class DeliveryDAO {

    /**
     * This method is used to create a delivery
     * @param delivery the delivery to create
     * @return true if the delivery is created, false otherwise
     */
    public abstract boolean createDelivery(Delivery delivery) throws AccessDatabaseException;


    /**
     * This method is used to get all the deliveries that exist in the database
     * @return a list of all the deliveries
     */
    public abstract List<Delivery> getAllDeliveries() throws AccessDatabaseException;


    /**
     * This method is used to get all the deliveries of a user
     * @param id the id of the user
     * @return the list of the deliveries of the user
     */
    public abstract List<Delivery> getAllDeliveriesOfUser(int id) throws AccessDatabaseException;


    /**
     * This method is used to get a delivery by its id
     * @param deliveryId the id of the delivery
     * @return the delivery
     */
    public abstract Delivery getDeliveryById(int deliveryId) throws AccessDatabaseException;


    /**
     * This method is used to update a delivery
     * @param delivery the delivery to update
     * @return  true if the delivery is updated, false otherwise
     */
    public abstract boolean updateDelivery(Delivery delivery) throws AccessDatabaseException;


    /**
     * This method is used to delete a delivery
     * @param id the id of the delivery to delete
     * @return true if the delivery is deleted, false otherwise
     */
    public abstract void deleteDelivery(int id) throws AccessDatabaseException;


    /**
     * This method is used to change the state of a delivery
     * @param idDelivery the id of the delivery
     * @param state the new state of the delivery
     */
    public abstract void changeStateOfDelivery(int idDelivery, String state) throws AccessDatabaseException;
}
