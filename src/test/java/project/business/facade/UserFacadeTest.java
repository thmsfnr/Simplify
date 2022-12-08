package project.business.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.business.models.User;

/**
 * Created by Simplify members on 07/12/22.
 * This is the Test class of the facade of the user
 * It is used to test the methods of the DAO to get the informations of the user
 * @author Simplify members
 */
public class UserFacadeTest {

    @Test
    public void loginSuccess() {
        String email = "momo@gmail.com";
        String password = "momo";
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.login(email, password);


        Assertions.assertTrue(user != null);
        Assertions.assertTrue(user.getEmail().equals(email));
        Assertions.assertFalse(user.getPassword().equals(password));
        Assertions.assertFalse(user.getEmail().equals("momo"));

    }


    @Test
    public void loginFail() {
        String email = "momo@gmail.com";
        String password = "mo";
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.login(email, password);

        Assertions.assertTrue(user == null);
    }


    @Test
    public void loginFail2() {
        String email = "nexistepas";
        String password = "mo";
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.login(email, password);

        Assertions.assertTrue(user == null);
    }

    @Test
    public void getInstace() {
        UserFacade userFacade = UserFacade.getInstance();
        UserFacade userFacade2 = UserFacade.getInstance();
        Assertions.assertTrue(userFacade == userFacade2);
    }


    @Test
    public void getInformationsByEmail(){
        String email = "momo@gmail.com";
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getInformationsByEmail(email);

        Assertions.assertTrue(user != null);
        Assertions.assertTrue(user.getEmail().equals(email));
    }
}
