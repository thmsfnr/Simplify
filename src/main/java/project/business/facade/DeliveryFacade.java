package project.business.facade;

import project.business.models.Delivery;
import project.business.models.Meal;
import project.exceptions.AccessDatabaseException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.abstr.DeliveryDAO;
import project.persistence.product.abstr.MealDAO;

import java.util.List;

public class DeliveryFacade {

    private MealDAO mealDAO;
    private DeliveryDAO deliveryDAO;
    private DeliveryFacade() {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        this.mealDAO = factory.getMealDAO();
        this.deliveryDAO = factory.getDeliveryDAO();
    }



    /**  Methods  **/

    public boolean createDelivery(Delivery delivery, List<Meal> meals) throws AccessDatabaseException {
        return this.deliveryDAO.createDelivery(delivery, meals);
    }

    public boolean updateDelivery(Delivery delivery) throws AccessDatabaseException {
        return this.deliveryDAO.updateDelivery(delivery);
    }

    public void deleteDelivery(int idDelivery) throws AccessDatabaseException {
        this.deliveryDAO.deleteDelivery(idDelivery);
    }

    public Delivery getDeliveryById(int idDelivery) throws AccessDatabaseException {
        return this.deliveryDAO.getDeliveryById(idDelivery);
    }

    public List<Delivery> getAllDeliveries() throws AccessDatabaseException {
        return this.deliveryDAO.getAllDeliveries();
    }

    public List<Delivery> getAllDeliveriesOfUser(int idUser) throws AccessDatabaseException {
        return this.deliveryDAO.getAllDeliveriesOfUser(idUser);
    }

    public List<Meal> getAllMealOfDelivery(int idDelivery) throws AccessDatabaseException {
        return this.mealDAO.getAllMealOfDelivery(idDelivery);
    }

    public void changeStateOfDelivery(int idDelivery, String state) throws AccessDatabaseException {
        this.deliveryDAO.changeStateOfDelivery(idDelivery, state);
    }

    public List<Delivery> getAllDeliveriesOfRestaurant(int idRestaurant) throws AccessDatabaseException {
        return this.deliveryDAO.getAllDeliveriesOfRestaurant(idRestaurant);
    }


    public List<Meal> getAllMealsOfRestaurant(int idRestaurant) throws AccessDatabaseException {
        return this.mealDAO.getAllMealOfRestaurant(idRestaurant);
    }





    /**  Singleton  **/
    public static DeliveryFacade getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private class SingletonHolder {
        private static final DeliveryFacade INSTANCE = new DeliveryFacade();
    }
}
