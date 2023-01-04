
package project.persistence.product.postgres;

import org.junit.jupiter.api.Test;
import project.business.models.User;
import project.exceptions.UserNotFoundException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.UserDAO;

import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Simplify members on 07/12/22.
 * This is the Test class of the PostGres DAO of the user
 * It is used to test the methods of the DAO to get the informations of the user by the email
 * @author Simplify members
 */
class PostGresUserDAOTest {

    @Test
    void connection_DB() {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        assertNotNull(connection);
    }

    @Test
    void getByEmail_User_Found() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        User user = null;
        try {
            user = userDAO.getByEmail("momo@gmail.com");
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(user);
        assertEquals(user.getId(), 1);
        assertEquals(user.getEmail(), "momo@gmail.com");
        assertEquals(user.getName(), "momo");
    }

    @Test
    void getByEmail_User_No_Found() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> {
            userDAO.getByEmail("toto");
        });
        assertEquals("User not found", userNotFoundException.getMessage());
    }

    @Test
    void getById() throws UserNotFoundException {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.getById(3);

        assertNotNull(user);
    }

    @Test
    void getById_User_No_Found() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> {
            userDAO.getById(100);
        });
        assertEquals("User not found", userNotFoundException.getMessage());
    }

    @Test
    void create() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        String email = "test" + Math.random() + "@gmail.com";
        User user = new User("test", email, "test", "test", "test", "test", false, false, 1);
        Boolean ok = userDAO.create(user);

        assertTrue(ok);
    }

    @Test
    void update() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        User user = null;
        try {
            user = userDAO.getByEmail("test");
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        user.setName("test2");
        Boolean ok = userDAO.update(user);

        assertTrue(ok);
    }

    @Test
    void delete() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        User user = null;
        try {
            user = userDAO.getByEmail("test");
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        Boolean ok = userDAO.delete(user.getId());

        assertTrue(ok);
    }

}
