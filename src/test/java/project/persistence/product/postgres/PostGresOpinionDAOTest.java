
package project.persistence.product.postgres;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.business.models.Opinion;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.OpinionDAO;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the PostGresOpinionDAOTest
 * It is used to test the methods of the DAO to get the informations of the opinion
 * @author Simplify members
 */
public class PostGresOpinionDAOTest {

    @Test
    void connection_DB() {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        assertNotNull(connection);
    }

    @Test
    void create_success() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        OpinionDAO opinionDAO = factory.getOpinionDAO();
        Opinion opinion = new Opinion(7, "test");
        boolean result = opinionDAO.create(opinion);
        Assertions.assertTrue(result);
    }

    @Test
    void create_fail() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        OpinionDAO opinionDAO = factory.getOpinionDAO();
        Opinion opinion = new Opinion(0, "test");
        boolean result = opinionDAO.create(opinion);
        Assertions.assertFalse(result);
    }

    @Test
    void get_all() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        OpinionDAO opinionDAO = factory.getOpinionDAO();
        List<Opinion> result = opinionDAO.getAll();
        Assertions.assertTrue(result != null);
    }

    @Test
    void get_user_success() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        OpinionDAO opinionDAO = factory.getOpinionDAO();
        List<Opinion> result = opinionDAO.getByUser(7);
        Assertions.assertTrue(result.size() != 0);
    }

    @Test
    void get_user_fail() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        OpinionDAO opinionDAO = factory.getOpinionDAO();
        List<Opinion> result = opinionDAO.getByUser(0);
        Assertions.assertTrue(result.size() == 0);
    }

    @Test
    void update() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        OpinionDAO opinionDAO = factory.getOpinionDAO();
        Opinion opinion = new Opinion(7, 7, "test");
        boolean result = opinionDAO.update(opinion);
        Assertions.assertTrue(result);
    }

    @Test
    void delete() {
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        OpinionDAO opinionDAO = factory.getOpinionDAO();
        boolean result = opinionDAO.delete(7);
        Assertions.assertTrue(result);
    }

}
