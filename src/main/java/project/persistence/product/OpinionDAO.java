package project.persistence.product;

import project.business.models.Opinion;
import project.exceptions.AccessDatabaseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simplify members on 28/12/22.
 * This interface is the DAO of the opinion
 * It is used to call the methods of the database to get the informations of the opinion
 * @author Simplify members
 */
public abstract class OpinionDAO {

    /**
     * This method is used to get the opinion of a restaurant
     * @param id the id of the restaurant
     * @return the list of the opinions
     */
    public abstract ArrayList<Opinion> getOpinionsOfRestaurant(int id) throws AccessDatabaseException;


    /**
     * This method is used to get the list of opinions of a meal by the id of meal
     * @param idMeal the id of the meal
     * @return the list of opinions or null if the meal is not found and throw an exception
     */
    public abstract List<Opinion> getAllOpinionOfMeal(int idMeal);

    /**
     * This method is used to create an opinion
     * @param opinion the opinion to create
     * @return True if the opinion is created, false otherwise
     */
    public abstract Boolean create(Opinion opinion);

    /**
     * This method is used to delete an opinion
     * @param id the id of the opinion to delete
     * @return True if the opinion is deleted, false otherwise
     */
    public abstract Boolean delete(int id);

    /**
     * This method is used to update an opinion
     * @param opinion the opinion to update
     * @return True if the opinion is updated, false otherwise
     */
    public abstract Boolean update(Opinion opinion);

    /**
     * This method is used to get the list of opinions of a user
     * @param id the id of the user
     * @return the list of opinions
     */
    public abstract List<Opinion> getByUser(int id);

    /**
     * This method is used to get the list of opinions
     * @return the list of opinions
     */
    public abstract List<Opinion> getAll();

}
