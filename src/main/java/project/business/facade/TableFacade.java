package project.business.facade;

import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.TableDAO;
import project.persistence.product.UserDAO;
import project.utilities.PasswordCrypt;


public class TableFacade {


    // Instance variables
    private TableDAO tableDAO;

    /**
     * Constructor of the class UserFacade
     */
    private TableFacade() {
        // Get the DAO
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        // Get the UserDAO
        this.tableDAO = factory.getTableDAO();
    }


    public Boolean createTable(String name, String description) {
        return this.tableDAO.insertTable(name, description);
    }


    public static TableFacade getInstance() {
        return TableFacade.FacadeHolder.INSTANCE;
    }


    private static class FacadeHolder {
        // Instance of the class UserFacade
        static final TableFacade INSTANCE = new TableFacade();
    }



}
