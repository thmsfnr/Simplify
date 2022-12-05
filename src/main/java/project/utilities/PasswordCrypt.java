
package project.utilities;

import org.springframework.security.crypto.bcrypt.BCrypt;
public class PasswordCrypt {
    public String cryptPassword(String password) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        return hashedPassword;
    }
    public boolean compare(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
