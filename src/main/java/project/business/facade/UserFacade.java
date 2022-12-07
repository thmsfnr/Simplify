package project.business.facade;


import project.business.models.User;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.UserDAO;
import project.utilities.PasswordCrypt;

public class UserFacade {

    private UserDAO userDAO;
    private PasswordCrypt encoder;


    private UserFacade() {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        this.userDAO = factory.getUserDAO();
        this.encoder = new PasswordCrypt();
    }


    public static UserFacade getInstance() {
        return FacadeHolder.INSTANCE;
    }


    public User login(String email, String password) {
        User user = this.userDAO.getByEmail(email);
        if(user != null) {
            if (this.encoder.compare(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public User getInformationsByEmail(String email) {
        return this.userDAO.getByEmail(email);
    }



    private static class FacadeHolder {
        static final UserFacade INSTANCE = new UserFacade();
    }

}
