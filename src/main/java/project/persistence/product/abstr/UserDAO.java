
package project.persistence.product.abstr;

import project.business.models.User;
import project.exceptions.AccessDatabaseException;
import project.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simplify members on 07/12/22.
 * This interface is the DAO of the user
 * It is used to call the methods of the database to get the informations of the user
 * @author Simplify members
 */
public abstract class UserDAO {

    /**
     * This method is used to get the user by the email
     * @param email the email of the user
     * @return the user
     */
    public abstract User getByEmail(String email) throws UserNotFoundException;

    /**
     * This method is used to save the user in the database
     * @param user the user to save
     * @return True if the user is saved, false otherwise
     */
    public abstract Boolean create(User user);

    /**
     * This method is used to get all the users in the database
     * @return the list of the users
     */
    public abstract List<User> getAll();

    /**
     * This method is used to update the user in the database
     * @param user the user to update
     * @return True if the update is successful, false otherwise
     */
    public abstract Boolean update(User user);

    /**
     * This method is used to delete the user in the database
     * @param id the id of the user to delete
     * @return True if the delete is successful, false otherwise
     */
    public abstract Boolean delete(int id);

    /**
     * This method is used to get the user by the id
     * @param id the id of the user
     * @return the user
     */
    public abstract User getById(int id) throws UserNotFoundException;

    /**
     * This method is used to get the list of user that have ask to delete their account
     * @return the list of the users
     */
    public abstract List<User> getAskDelete();


    /**
     * This method is used to get the list of managers that exists
     * @return the list of the users
     */
    public abstract ArrayList<User> getAllManagers() throws AccessDatabaseException;

}
