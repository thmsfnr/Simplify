package project.persistence.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import project.business.models.User;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;

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
    void getByEmail_User_Found(){
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.getByEmail("momo@gmail.com");
        assertNotNull(user);
        assertEquals(user.getId(),1);
        assertEquals(user.getEmail(),"momo@gmail.com");
        assertEquals(user.getName(),"momo");
    }
    @Test
    void getByEmail_User_No_Found(){
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        User user = userDAO.getByEmail("toto@gmail.com");
        assertNull(user);
    }
}