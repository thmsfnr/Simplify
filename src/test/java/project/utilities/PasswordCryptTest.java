package project.utilities;


/**
 * Created by Simplify members on 07/12/22.
 * This is the test class of the crypt of the password
 * It is used to test the crypt of the password of the user when he is registered
 * And to test the compare of the password of the user when he whant to log in
 * @author Simplify members
 */
public class PasswordCryptTest {

    /**
     * This method is used to test the crypt of the password of the user
     * Using the BCrypt library
     */
/*
    @Test
    void CryptPassword() {
        PasswordCrypt passwordCrypt = new PasswordCrypt();
        String password = "password";
        String hash = passwordCrypt.cryptPassword(password);
        Assertions.assertFalse(password.equals(hash));
    }
*/
    /**
     * This method is used to test the compare of the password of the user
     * Using the BCrypt library
     */
    /*
    @Test
    void Compare() {
        PasswordCrypt passwordCrypt = new PasswordCrypt();
        String password = "password";
        String hash = passwordCrypt.cryptPassword(password);
        Assertions.assertTrue(passwordCrypt.compare(password, hash));
        Assertions.assertFalse(passwordCrypt.compare("wrongPassword", hash));
    }
    */
}
