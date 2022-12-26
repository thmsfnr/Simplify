package project.persistence.product;

import javafx.collections.ObservableList;
import project.business.models.Table;
import project.exceptions.AccessDatabaseException;

import java.util.ArrayList;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the DAO of the table
 * It is used to communicate with the database
 * It's a singleton
 *
 * @author Simplify members
 */
public abstract class TableDAO {


    /**
     * This method is used to create a table
     * @return a boolean, true if the table is created, false otherwise
     */
    public abstract Boolean insertTable(String name, String description);

    /**
     * This method is used to get all the tables
     * @return a list of tables
     */
    public abstract ObservableList<Table> getAllTables();

    /**
     * This method is used to delete a table by its id
     * @param id the id of the table
     * @return the table
     */
    public abstract Boolean deleteTable(int id);

    /**
     * This method is used to update a table
     * @param table the table to update
     * @return the table
     */
    public abstract Boolean updateTable(Table table);

    /**
     * This method is used to get a table by its id
     * @param id the id of the table
     * @return the table
     */
    public abstract Table getTableById(int id);


    /**
     * This method is used to get the number of the tables in a restaurant
     * @param idRestaurant the id of the restaurant
     * @return the table
     */
    public abstract int countOfTablesOfRestaurant(int idRestaurant) throws AccessDatabaseException;
}
