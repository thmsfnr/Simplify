
package project.persistence.factory;

import project.persistence.product.PostGresUserDAO;
import project.persistence.product.UserDAO;
import project.utilities.ConnectionPostgres;

public class PostGresDAOFactory extends AbstractDAOFactory {

    public static ConnectionPostgres connectionPostgres = new ConnectionPostgres();

    public PostGresDAOFactory() { }


    @Override
    public UserDAO getUserDAO() {
        return new PostGresUserDAO();
    }

}
