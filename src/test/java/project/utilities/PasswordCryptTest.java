package project.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordCryptTest {

    @Test
    void CryptPassword() {
        PasswordCrypt passwordCrypt = new PasswordCrypt();
        String password = "password";
        String hash = passwordCrypt.cryptPassword(password);
        Assertions.assertFalse(password.equals(hash));
    }

    @Test
    void Compare() {
        PasswordCrypt passwordCrypt = new PasswordCrypt();
        String password = "password";
        String hash = passwordCrypt.cryptPassword(password);
        Assertions.assertTrue(passwordCrypt.compare(password, hash));
        Assertions.assertFalse(passwordCrypt.compare("wrongPassword", hash));
    }
}
