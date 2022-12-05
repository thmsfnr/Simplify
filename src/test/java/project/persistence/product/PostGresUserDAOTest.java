package project.persistence.product;

import org.junit.jupiter.api.Test;
import project.persistence.factory.PostGresDAOFactory;

import java.util.ArrayList;

class PostGresUserDAOTest {

    @Test
    void getByMail() {
        PostGresDAOFactory postGresDAOFactory = PostGresDAOFactory.getInstance();
        UserDAO postGresUserDAO = postGresDAOFactory.createUserDAO();

        ArrayList<Object> userToCompare = new ArrayList<>();
        userToCompare.add(1);
        userToCompare.add("Smith");
        userToCompare.add("John");
        userToCompare.add("momo@gmail.com");
        userToCompare.add("0123456789");
        userToCompare.add("Dans la jungle");
        userToCompare.add("momo");
        ArrayList<Object> user = postGresUserDAO.getByMail("momo@gmail.com");
        System.out.println(user);

    }
}