package project.persistence.product.abstr;

import project.business.models.Delivery;
import project.business.models.Meal;
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
     * @param meals the meals of the delivery
     * @return true if the delivery is created, false otherwise
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public abstract boolean createDelivery(Delivery delivery, List<Meal> meals) throws AccessDatabaseException;


    /**
     * This method is used to get all the deliveries that exist in the database
     * @return a list of all the deliveries
     * @throws AccessDatabaseException
     */
    public abstract List<Delivery> getAllDeliveries() throws AccessDatabaseException;


    /**
     * This method is used to get all the deliveries of a user
     * @param id the id of the user
     * @return the list of the deliveries of the user
     * @throws AccessDatabaseException
     */
    public abstract List<Delivery> getAllDeliveriesOfUser(int id) throws AccessDatabaseException;


    /**
     * This method is used to get a delivery by its id
     * @param deliveryId the id of the delivery
     * @return the delivery
     * @throws AccessDatabaseException
     */
    public abstract Delivery getDeliveryById(int deliveryId) throws AccessDatabaseException;


    /**
     * This method is used to update a delivery
     * @param delivery the delivery to update
     * @return  true if the delivery is updated, false otherwise
     * @throws AccessDatabaseException
     */
    public abstract boolean updateDelivery(Delivery delivery) throws AccessDatabaseException;


    /**
     * This method is used to delete a delivery
     * @param id the id of the delivery to delete
     * @return true if the delivery is deleted, false otherwise
     * @throws AccessDatabaseException
     */
    public abstract void deleteDelivery(int id) throws AccessDatabaseException;


    /**
     * This method is used to change the state of a delivery
     * @param idDelivery the id of the delivery
     * @param state the new state of the delivery
     * @throws AccessDatabaseException
     */
    public abstract void changeStateOfDelivery(int idDelivery, String state) throws AccessDatabaseException;


    /**
     * This method is used to get all the deliveries of a restaurant
     * @param idRestaurant the id of the restaurant
     * @return the list of the deliveries of the restaurant
     * @throws AccessDatabaseException
     */
    public abstract List<Delivery> getAllDeliveriesOfRestaurant(int idRestaurant) throws AccessDatabaseException;
}
