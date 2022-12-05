
package project.persistence.factory;

import project.persistence.product.PostGresUserDAO;
import project.persistence.product.UserDAO;

public class PostGresDAOFactory implements AbstractFactory {
    private PostGresDAOFactory() {}
    private static class LazyHolder {
        static final PostGresDAOFactory INSTANCE = new PostGresDAOFactory();
    }
    public static PostGresDAOFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public UserDAO createUserDAO() {
        return new PostGresUserDAO();
    }

}
