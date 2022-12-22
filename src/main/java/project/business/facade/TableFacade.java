package project.business.facade;

import javafx.collections.ObservableList;
import project.business.models.Table;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.TableDAO;



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

    public ObservableList<Table> getAllTables() {
        return this.tableDAO.getAllTables();
    }

    public Boolean deleteTable(int id) {
        return this.tableDAO.deleteTable(id);
    }

    public Boolean updateTable(Table table) {
        return this.tableDAO.updateTable(table);
    }

    public Table getTableById(int id) {
        return this.tableDAO.getTableById(id);
    }
    public static TableFacade getInstance() {
        return TableFacade.FacadeHolder.INSTANCE;
    }



    private static class FacadeHolder {
        // Instance of the class UserFacade
        static final TableFacade INSTANCE = new TableFacade();
    }



}
