
package project.persistence.factory;
import project.persistence.product.*;
import project.utilities.ConnectionPostgres;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the factory of the postgreSQL DAO
 * It is used to make the connection with the database
 * It uses the singleton pattern to create only one instance of the class
 * because its inherited from the abstract class AbstractDAOFactory
 * @author Simplify members
 */
public class PostGresDAOFactory extends AbstractDAOFactory {

    /**
     * This method is used to get the instance of The connection to the database
     */
    public static ConnectionPostgres connectionPostgres = new ConnectionPostgres();

    /**
     * the constructor of the class PostGresDAOFactory
     */
    protected PostGresDAOFactory() { }


    /**
     * This method ovveride the method getUserDAO from the abstract class AbstractDAOFactory
     * Because it is a PostGresDAOFactory it returns a PostGresUserDAO
     * @return the PostGresUserDAO
     */
    @Override
    public UserDAO getUserDAO() {return new PostGresUserDAO();}

    /**
     * This method ovveride the method getPaymentDAO from the abstract class AbstractDAOFactory
     * Because it is a PostGresDAOFactory it returns a PostGresPaymentDAO
     * @return the PostGresPaymentDAO
     */
    @Override
    public PaymentDAO getPaymentDAO() {
        return new PostGresPaymentDAO();
    }

    @Override
    public MealDAO getMealDAO() {return new PostGresMealDAO();}

    @Override
    public TableDAO getTableDAO() {
        return new PostGresTableDAO();
    }

    @Override
    public EventDAO getEventDAO() {
        return new PostGresEventDAO();
    }

    @Override
    public NotificationDAO getNotificationDAO() {return new PostGresNotificationDAO();}

    @Override
    public OpinionDAO getOpinionDAO() {
        return new PostGresOpinionDAO();
    }

    @Override
    public RestaurantDAO getRestaurantDAO() {
        return new PostGresRestaurantDAO();
    }

}
