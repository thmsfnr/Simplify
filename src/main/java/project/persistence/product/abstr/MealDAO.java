package project.persistence.product.abstr;

import project.business.models.Meal;
import project.exceptions.AccessDatabaseException;
import project.exceptions.MealNotFoundException;

import java.util.List;

public abstract class MealDAO {
    /**
     * This method is used to get the meal by the id
     * @param idMeal the id of the meal
     * @return the meal or null if the meal is not found and throw an exception
     */
    public abstract Meal getById(int idMeal) throws MealNotFoundException;

    /**
     * This method is used to create a meal associated to a restaurant
     * @param meal the meal model with the information of the new meal
     * @return True if the meal is created, false otherwise
     */
    public abstract boolean create(Meal meal);

    /**
     * this method is used to delete a meal which corresponds to the id in parameter
     * @param idMeal the id of the meal to delete
     * @return True if the meal is deleted, false otherwise
     */
    public abstract boolean delete(int idMeal);

    /**
     * this method is used to update a meal
     * @param meal the meal model with the information of the meal to update
     * @return True if the meal is updated, false otherwise
     */
    public abstract boolean update(Meal meal);
    /**
     * This method is used to get all the meals of a restaurant
     * @param idRestaurant the id of the restaurant
     * @return a list of meals
     */
    public abstract List<Meal> getAllMeal(int idRestaurant);



    /**
     * This method is used to get all the meals of a delivery
     * @param idDelivery the id of the delivery
     * @return a list of meals
     */
    public abstract List<Meal> getAllMealOfDelivery(int idDelivery) throws AccessDatabaseException;

    /**
     * This method is used to get all the meals of a reservation
     * @param idReservation the id of the reservation
     * @return a list of meals
     */
    public abstract List<Meal> getMealsOfReservation(int idReservation);


    /**
     * This method is used to get all the meals of a restaurant
     * @param idRestaurant
     * @return a list of meals
     * @throws AccessDatabaseException
     */
    public abstract List<Meal> getAllMealOfRestaurant(int idRestaurant) throws AccessDatabaseException;

}
