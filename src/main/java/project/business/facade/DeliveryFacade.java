package project.business.facade;

import project.business.models.Delivery;
import project.business.models.Meal;
import project.exceptions.AccessDatabaseException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.DeliveryDAO;
import project.persistence.product.MealDAO;

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

    public boolean createDelivery(Delivery delivery) throws AccessDatabaseException {
        return this.deliveryDAO.createDelivery(delivery);
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





    /**  Singleton  **/
    public static DeliveryFacade getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private class SingletonHolder {
        private static final DeliveryFacade INSTANCE = new DeliveryFacade();
    }
}