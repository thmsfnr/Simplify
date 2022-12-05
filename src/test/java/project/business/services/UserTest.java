package project.business.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void login1() {
        User user = new User();
        String result = user.login("username", "password");
        Assertions.assertTrue(result.equals("User not found"));
    }

    @Test
    void login2() {
        User user = new User();
        String result = user.login("username", "wrongPassword");
        Assertions.assertTrue(result.equals("Login failed"));
    }

    @Test
    void login3() {
        User user = new User();
        String result = user.login("username", "goodPassword");
        Assertions.assertTrue(result.equals("Login successful"));
    }
}
