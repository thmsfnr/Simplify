package project.business.facade;

import project.business.models.Meal;
import project.business.models.Opinion;
import project.exceptions.MealNotFoundException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.MealDAO;
import project.persistence.product.abstr.OpinionDAO;

import java.util.List;

/**
 * Created by Simplify members on 21/12/22.
 * This class is the facade of the meal
 * It is used to call the DAO
 * It's a singleton
 *
 * @author Simplify members
 */
public class MealFacade {

    private MealDAO mealDAO;
    private OpinionDAO opinionDAO;

    /**
     * Constructor of the class MealFacade
     */
    private MealFacade() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        this.mealDAO = factory.getMealDAO();
        this.opinionDAO = factory.getOpinionDAO();
    }

    /**
     * This method is used to create a meal associated to a restaurant
     * @param meal the meal model with the information of the new meal
     * @return True if the meal is created, false otherwise
     */
    public boolean create(Meal meal) {
        return this.mealDAO.create(meal);
    }

    /**
     * this method is used to delete a meal which corresponds to the id in parameter
     * @param idMeal the id of the meal to delete
     * @return True if the meal is deleted, false otherwise
     */
    public boolean delete(int idMeal) {
        return this.mealDAO.delete(idMeal);
    }

    /**
     * this method is used to update a meal
     * @param meal the meal model with the information of the meal to update
     * @return True if the meal is updated, false otherwise
     */
    public boolean update(Meal meal) {
        return this.mealDAO.update(meal);
    }
    /**
     * This method is used to get all the meals of a restaurant
     * @param idRestaurant the id of the restaurant
     * @return a list of meals
     */
    public List<Meal> getAllMeal(int idRestaurant) {
        return this.mealDAO.getAllMeal(idRestaurant);
    }

    /**
     * This method is used to get a meal which correspond to the id in parameter
     * @param idMeal the id of the meal
     * @return the meal model with the information of the meal, null if the meal does not exist
     */
    public Meal getById(int idMeal) {
        try {
            return this.mealDAO.getById(idMeal);
        } catch (MealNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to get all the opinions of a meal
     * @param idMeal the id of the meal
     * @return a list of opinions
     */
    public List<Opinion> getAllOpinionOfMeal(int idMeal) {return this.opinionDAO.getAllOpinionOfMeal(idMeal);}

    /**
     * This method is used to get the instance of the class MealFacade
     * @return the instance of the class MealFacade
     */
    public static MealFacade getInstance() {
        return MealFacade.FacadeHolder.INSTANCE;
    }

    /**
     *  This class is used to get the instance of the class MealFacade
     *  for thread safety reasons (double checked locking)
     */
    private static class FacadeHolder {
        // Instance of the class UserFacade
        static final MealFacade INSTANCE = new MealFacade();
    }

}
