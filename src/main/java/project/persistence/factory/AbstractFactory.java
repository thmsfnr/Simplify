package project.persistence.factory;

import project.persistence.product.UserDAO;

public abstract class AbstractFactory {

    public abstract UserDAO createUserDAO();
}
