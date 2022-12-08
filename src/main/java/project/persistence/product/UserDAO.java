
package project.persistence.product;

import project.business.models.User;
import project.exceptions.UserNotFoundException;

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

}
