
package project.business.facade;

import project.business.models.Delivery;
import project.business.models.Meal;
import project.exceptions.AccessDatabaseException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.abstr.DeliveryDAO;
import project.persistence.product.abstr.MealDAO;
import java.util.List;

/**
 * Created by Simplify members on 10/01/23.
 * This class is the facade of the delivery
 * @author Simplify members
 */
public class DeliveryFacade {

    private MealDAO mealDAO;
    private DeliveryDAO deliveryDAO;

    /**
     * This method is used to create a delivery facade
     */
    private DeliveryFacade() {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        this.mealDAO = factory.getMealDAO();
        this.deliveryDAO = factory.getDeliveryDAO();
    }

    /**
     * This method is used to create a delivery
     * @param delivery the delivery to create
     * @param meals the meals of the delivery
     * @return True if the delivery is created, false otherwise
     * @throws AccessDatabaseException if the database is not accessible
     */
    public boolean createDelivery(Delivery delivery, List<Meal> meals) throws AccessDatabaseException {
        return this.deliveryDAO.createDelivery(delivery, meals);
    }

    /**
     * This method is used to update a delivery
     * @param delivery the delivery to update
     * @return True if the delivery is updated, false otherwise
     * @throws AccessDatabaseException if the database is not accessible
     */
    public boolean updateDelivery(Delivery delivery) throws AccessDatabaseException {
        return this.deliveryDAO.updateDelivery(delivery);
    }

    /**
     * This method is used to delete a delivery
     * @param idDelivery the id of the delivery to delete
     * @throws AccessDatabaseException if the database is not accessible
     */
    public void deleteDelivery(int idDelivery) throws AccessDatabaseException {
        this.deliveryDAO.deleteDelivery(idDelivery);
    }

    /**
     * This method is used to get a delivery by its id
     * @param idDelivery the id of the delivery to get
     * @return the delivery
     * @throws AccessDatabaseException if the database is not accessible
     */
    public Delivery getDeliveryById(int idDelivery) throws AccessDatabaseException {
        return this.deliveryDAO.getDeliveryById(idDelivery);
    }

    /**
     * This method is used to get all the deliveries
     * @return a list of the deliveries
     * @throws AccessDatabaseException if the database is not accessible
     */
    public List<Delivery> getAllDeliveries() throws AccessDatabaseException {
        return this.deliveryDAO.getAllDeliveries();
    }

    /**
     * This method is used to get all the deliveries of a user
     * @param idUser the id of the user
     * @return a delivery facade
     */
    public List<Delivery> getAllDeliveriesOfUser(int idUser) throws AccessDatabaseException {
        return this.deliveryDAO.getAllDeliveriesOfUser(idUser);
    }

    /**
     * This method is used to get all the deliveries for a meal
     * @param idDelivery the id of the delivery
     * @return a list of the deliveries
     * @throws AccessDatabaseException if the database is not accessible
     */
    public List<Meal> getAllMealOfDelivery(int idDelivery) throws AccessDatabaseException {
        return this.mealDAO.getAllMealOfDelivery(idDelivery);
    }

    /**
     * This method is used to change the status of a delivery
     * @param idDelivery the id of the delivery
     * @param state the new state of the delivery
     * @throws AccessDatabaseException if the database is not accessible
     */
    public void changeStateOfDelivery(int idDelivery, String state) throws AccessDatabaseException {
        this.deliveryDAO.changeStateOfDelivery(idDelivery, state);
    }

    /**
     * This method is used to get all the deliveries for a restaurant
     * @param idRestaurant the id of the restaurant
     * @return a list of the deliveries
     * @throws AccessDatabaseException if the database is not accessible
     */
    public List<Delivery> getAllDeliveriesOfRestaurant(int idRestaurant) throws AccessDatabaseException {
        return this.deliveryDAO.getAllDeliveriesOfRestaurant(idRestaurant);
    }


    /**
     * This method is used to get all the meals for a restaurant
     * @param idRestaurant the id of the restaurant
     * @return a list of the meals
     * @throws AccessDatabaseException if the database is not accessible
     */
    public List<Meal> getAllMealsOfRestaurant(int idRestaurant) throws AccessDatabaseException {
        return this.mealDAO.getAllMealOfRestaurant(idRestaurant);
    }





    /**  Singleton  **/
    
    /**
     * This method is used to get an instance of the delivery facade
     * @return
     */
    public static DeliveryFacade getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * This class is used to create a singleton of the delivery facade
     */
    private class SingletonHolder {
        private static final DeliveryFacade INSTANCE = new DeliveryFacade();
    }

}
