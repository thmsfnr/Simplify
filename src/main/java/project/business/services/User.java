
package project.business.services;

import project.persistence.factory.AbstractFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.UserDAO;
import project.utilities.PasswordCrypt;
import java.util.ArrayList;

public class User {

    private UserDAO userDAO;
    private PasswordCrypt passwordCrypt;

    public User() {
        this.passwordCrypt = new PasswordCrypt();
        AbstractFactory factory = PostGresDAOFactory.getInstance();
        this.userDAO = factory.createUserDAO();
    }

    public String login(String username, String password) {
        ArrayList<Object> user = this.userDAO.getByMail(username);
        if(user.size() != 0 && password.equals(user.get(6))) {
            return "Success";
        } else {
            return "Fail";
        }
    }
}
