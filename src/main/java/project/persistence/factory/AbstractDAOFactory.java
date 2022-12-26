package project.persistence.factory;
import project.persistence.product.MealDAO;
import project.persistence.product.OpinionDAO;
import project.persistence.product.TableDAO;
import project.persistence.product.UserDAO;


/**
 * Created by Simplify members on 07/12/22.
 * This class is the abstract factory of the DAO
 * It is used to create the DAO (data access object)
 * It uses the singleton pattern to create only one instance of the class
 * @author Simplify members
 */
public abstract class AbstractDAOFactory {


    /**
     * abstract method to get the UserDAO
     * @return the UserDAO
     */
    public abstract UserDAO getUserDAO();

    public abstract TableDAO getTableDAO();

    /**
     * abstract method to get the MealDAO
     * @return the MealDAO
     */
    public abstract MealDAO getMealDAO();

    public abstract OpinionDAO getOpinionDAO();

    /**
     * This method is used to get the instance of the class
     * @return the instance of the class
     */
    public static AbstractDAOFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Using thread safe lazy initialization to create the instance of the class
     * To create an instance of the class only when it is needed
     */
    private static class LazyHolder {

        static final PostGresDAOFactory INSTANCE = new PostGresDAOFactory();
    }
}
