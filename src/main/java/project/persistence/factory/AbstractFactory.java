
package project.persistence.factory;

import project.persistence.product.UserDAO;

public interface AbstractFactory {

    UserDAO createUserDAO();

}
