package project.business.services;

import project.exceptions.UserNotFoundException;
import project.models.UserModel;
import project.persistence.product.UserDAO;
import project.utilities.PasswordCrypt;

public class User {

    private UserDAO userDAO;
    private PasswordCrypt passwordCrypt;


    public User() {
        this.passwordCrypt = new PasswordCrypt();
        //AbstractFactory factory = ;
        //this.userDAO = factory.createUserDAO();
    }



    public String login(String username, String password) {
        try{
            UserModel user = this.userDAO.getByUsername(username);
            if(this.passwordCrypt.compare(password, "password")) {
                return "Login successful";
            } else {
                return "Login failed";
            }
        }
        catch(UserNotFoundException e){
            return "User not found";
        }



    }
}
