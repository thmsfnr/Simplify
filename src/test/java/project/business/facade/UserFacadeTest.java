
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
        String email = "admin";
        String password = "admin";
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.login(email, password);

        Assertions.assertTrue(user != null);
        Assertions.assertTrue(user.getEmail().equals(email));
        Assertions.assertFalse(user.getPassword().equals(password));
        Assertions.assertFalse(user.getEmail().equals("admin"));
    }

    @Test
    public void loginFail() {
        String email = "admin";
        String password = "ad";
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
    public void getInstance() {
        UserFacade userFacade = UserFacade.getInstance();
        UserFacade userFacade2 = UserFacade.getInstance();
        Assertions.assertTrue(userFacade == userFacade2);
    }


    @Test
    public void getInformationsByEmail(){
        String email = "admin";
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getInformationsByEmail(email);

        Assertions.assertTrue(user != null);
        Assertions.assertTrue(user.getEmail().equals(email));
    }

    @Test
    public void getInformationsByEmailFail(){
        String email = "nexistepas";
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getInformationsByEmail(email);

        Assertions.assertTrue(user == null);
    }

    @Test
    public void getById(){
        int id = 100000000;
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getById(id);

        Assertions.assertTrue(user == null);
    }

    @Test
    public void getById2(){
        int id = 40;
        UserFacade userFacade = UserFacade.getInstance();
        User user = userFacade.getById(id);

        Assertions.assertTrue(user != null);
        Assertions.assertTrue(user.getId() == id);
    }

    @Test
    public void create() {
        User user = new User("test", "test", "test", "test", "test", "test", false, false, 1);
        UserFacade userFacade = UserFacade.getInstance();
        Boolean creation = userFacade.create(user);

        Assertions.assertTrue(creation);
    }

    @Test
    public void update() {
        UserFacade userFacade = UserFacade.getInstance();
        int id = userFacade.getInformationsByEmail("admin").getId();
        User user = userFacade.getById(id);
        user.setName("test2");
        Boolean update = userFacade.update(user);

        Assertions.assertTrue(update);
    }

    @Test
    public void delete() {
        UserFacade userFacade = UserFacade.getInstance();
        int id = userFacade.getInformationsByEmail("admin").getId();
        User user = userFacade.getById(id);
        Boolean delete = userFacade.delete(user.getId());

        Assertions.assertTrue(delete);
    }

}
