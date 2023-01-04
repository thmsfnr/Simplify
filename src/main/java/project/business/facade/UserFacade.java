
package project.business.facade;

import project.business.models.User;
import project.exceptions.UserNotFoundException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.UserDAO;
import project.utilities.PasswordCrypt;
import java.util.List;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the facade of the user
 * It is used to call the DAO and the crypt class
 * It's a singleton
 *
 * @author Simplify members
 */
public class UserFacade {

    // Instance variables
    private UserDAO userDAO;
    private PasswordCrypt encoder;

    /**
     * Constructor of the class UserFacade
     */
    private UserFacade() {
        // Get the DAO
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        // Get the UserDAO
        this.userDAO = factory.getUserDAO();
        // Get the PasswordCrypt
        this.encoder = new PasswordCrypt();
    }

    /**
     * This method is used to login a user in the application
     * @param email the email of the user
     * @param password the password of the user
     * @return the user if the login is successful, null otherwise
     */
    public User login(String email, String password) {
        try {
            // Get the user from the database
            User user = this.userDAO.getByEmail(email);
            //  Return the user if the object user returned is not null (user does not exist in the database), the password is correct and the user is not banned
            if (this.encoder.compare(password, user.getPassword()) && !user.getBan()) {
                return user;
            }
            return null;
        }
        catch(UserNotFoundException e) {
            return null;
        }
    }

    /**
     * This method is used to create a user in the database
     * @param user the user to create
     * @return True if the user is created, false otherwise
     */
    public Boolean create(User user) {
            // Get the user from the database
            try {
                User userFromDB = this.userDAO.getByEmail(user.getEmail());
                return false;
            }
            catch (UserNotFoundException e) {
                // If the user does not exist in the database, create it
                user.setPassword(this.encoder.cryptPassword(user.getPassword()));
                return this.userDAO.create(user);
            }
    }

    /**
     * This method is used to get the informations of a user from the database
     * @param email the email of the user
     * @return the informations of the user if the user exists, null otherwise
     */
    public User getInformationsByEmail(String email) {
        try {
            // Get the user from the database
            return this.userDAO.getByEmail(email);
        }
        catch(UserNotFoundException e) {
            return null;
        }
    }

    /**
     * This method is used to get the informations of a user from the database
     * @param id the id of the user
     * @return the informations of the user if the user exists, null otherwise
     */
    public User getById(int id) {
        try {
            // Get the user from the database
            return this.userDAO.getById(id);
        }
        catch(UserNotFoundException e) {
            return null;
        }
    }

    /**
     * This method is used to update the informations of a user in the database
     * @param user the user to update
     * @return True if the update is successful, false otherwise
     */
    public Boolean update(User user) {
        // Get the user from the database
        try {
            User userFromDB = this.userDAO.getByEmail(user.getEmail());
            // If the user exists in the database, update it
            return this.userDAO.update(user);
        }
        catch (UserNotFoundException e) {
            return false;
        }
    }

    /**
     * This method is used to delete a user from the database
     * @param id the id of the user
     * @return True if the user is deleted, false otherwise
     */
    public Boolean delete(int id) {
        // Get the user from the database
        try {
            User userFromDB = this.userDAO.getById(id);
            // If the user exists in the database, delete it
            return this.userDAO.delete(id);
        }
        catch (UserNotFoundException e) {
            return false;
        }
    }

    /**
     * This method is used to get all the users from the database
     * @return the list of all the users
     */
    public List<User> getAllUser() {
        return this.userDAO.getAll();
    }

    /**
     * This method is use to retrieve all users that have asked to delete their account
     * @return the list of all the users
     */
    public List<User> getAskDelete() {
        return this.userDAO.getAskDelete();
    }

    /**
     * This method is used to get the instance of the class UserFacade
     * @return the instance of the class UserFacade because it's a singleton
     */
    public static UserFacade getInstance() {
        return FacadeHolder.INSTANCE;
    }

    /**
     *  This class is used to get the instance of the class UserFacade
     *  for thread safety reasons (double checked locking)
     */
    private static class FacadeHolder {
        static final UserFacade INSTANCE = new UserFacade(); // Instance of the class UserFacade
    }

}
