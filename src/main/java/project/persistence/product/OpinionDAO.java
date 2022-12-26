package project.persistence.product;

import project.business.models.Opinion;
import project.exceptions.AccessDatabaseException;
import java.util.ArrayList;
import java.util.List;

public abstract class OpinionDAO {

    public abstract ArrayList<Opinion> getOpinionsOfRestaurant(int id) throws AccessDatabaseException;


    /**
     * This method is used to get the list of opinions of a meal by the id of meal
     * @param idMeal the id of the meal
     * @return the list of opinions or null if the meal is not found and throw an exception
     */

    public abstract List<Opinion> getAllOpinionOfMeal(int idMeal);

}
