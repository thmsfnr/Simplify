package project.business.facade;

// Import the classes from the project.business.models package
import project.business.models.User;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.UserDAO;
import project.utilities.PasswordCrypt;

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
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
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
        // Get the user from the database
        User user = this.userDAO.getByEmail(email);
        // If the object user returned is not null ( user do not existe in the database)
        // and the password is correct return the user
        if(user != null) {
            if (this.encoder.compare(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    /**
     * This method is used to get the informations of a user from the database
     * @param email the email of the user
     * @return the informations of the user if the user exists, null otherwise
     */
    public User getInformationsByEmail(String email) {
        return this.userDAO.getByEmail(email);
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
        // Instance of the class UserFacade
        static final UserFacade INSTANCE = new UserFacade();
    }
}
