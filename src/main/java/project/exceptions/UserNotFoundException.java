
package project.exceptions;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the exception of the user
 * It is used to throw an exception when the user is not found
 * @author Simplify members
 */
public class UserNotFoundException extends Exception {

    /**
     * Constructor of the class UserNotFoundException
     * It is used when the user is not found
     */
    public UserNotFoundException() {super("User not found");}

}
