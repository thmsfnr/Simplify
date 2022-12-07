
package project.persistence.factory;

import project.persistence.product.UserDAO;

public abstract class AbstractDAOFactory {

    public abstract UserDAO getUserDAO();

    public static AbstractDAOFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        static final PostGresDAOFactory INSTANCE = new PostGresDAOFactory();
    }
}
