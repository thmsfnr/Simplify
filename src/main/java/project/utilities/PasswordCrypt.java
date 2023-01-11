
package project.utilities;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the crypt of the password
 * It is used to crypt the password of the user when he is registered
 * And to compare the password of the user when he whants to log in
 * @author Simplify members
 */
public class PasswordCrypt {

    /**
     * This method is used to crypt the password of the user
     * Using the BCrypt library
     * @param password the password of the user
     * @return the hashed password
     */
    public String cryptPassword(String password) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        return hashedPassword;
    }

    /**
     * This method is used to compare the password of the user
     * Using the BCrypt library
     * @param password the password of the user
     * @param hash the hash key
     * @return true if the password is correct, false otherwise
     */
    public boolean compare(String password, String hash) {
        // Check that the password can be hashed with the hash to confirm it is the same password
        return BCrypt.checkpw(password, hash);
    }

}
