
package project.business.facade;

import javafx.collections.ObservableList;
import project.business.models.Table;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.TableDAO;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the facade of the table
 * It is used to call the DAO
 * It's a singleton
 * @author Simplify members
 */
public class TableFacade {


    // Instance variables
    private TableDAO tableDAO;

    /**
     * Constructor of the class UserFacade
     */
    private TableFacade() {
        // Get the DAO
        AbstractDAOFactory factory = PostGresDAOFactory.getInstance();
        // Get the TableDAO
        this.tableDAO = factory.getTableDAO();
    }

    /**
     * This method is used to create a table
     * @return a boolean, true if the table is created, false otherwise
     */
    public Boolean createTable(String name, String description) {
        return this.tableDAO.insertTable(name, description);
    }

    /**
     * This method is used to get all the tables
     * @return a list of tables
     */
    public ObservableList<Table> getAllTables() {
        return this.tableDAO.getAllTables();
    }

    /**
     * This method is used to delete a table by its id
     * @param id the id of the table
     * @return the table
     */
    public Boolean deleteTable(int id) {
        return this.tableDAO.deleteTable(id);
    }

    /**
     * This method is used to update a table
     * @param table the table to update
     * @return the table
     */
    public Boolean updateTable(Table table) {
        return this.tableDAO.updateTable(table);
    }

    /**
     * This method is used to get a table by its id
     * @param id the id of the table
     * @return the table
     */
    public Table getTableById(int id) {
        return this.tableDAO.getTableById(id);
    }

    /**
     * Get the instance of the class
     * @return the instance of the class
     * it's a singleton
     */
    public static TableFacade getInstance() {
        return TableFacade.FacadeHolder.INSTANCE;
    }


    /**
     * This class is used to get the instance of the class
     * it's a holder class for the singleton
     */
    private static class FacadeHolder {
        // Instance of the class UserFacade
        static final TableFacade INSTANCE = new TableFacade();
    }

}
