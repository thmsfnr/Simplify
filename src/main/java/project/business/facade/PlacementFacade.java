
package project.business.facade;

import project.business.models.Restaurant;
import project.business.models.Table;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.abstr.RestaurantDAO;
import project.persistence.product.abstr.TableDAO;
import java.util.List;

/**
 * Created by Simplify members on 21/12/22.
 * This class is the facade of the placement
 * It is used to call the DAO
 * @author Simplify members
 */
public class PlacementFacade {

    private RestaurantDAO restaurantDAO;
    private TableDAO tableDAO;

    /**
     * This method is the constructor of the placement facade
     */
    private PlacementFacade() {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        this.restaurantDAO = factory.getRestaurantDAO();
        this.tableDAO = factory.getTableDAO();
    }

    /**
     * This method is used to get the instance of the class PlacementFacade
     * @return the instance of the class PlacementFacade
     */
    public static PlacementFacade getInstance() {
        return SingletonPlacementHolder.INSTANCE;
    }

    /**
     * This method is used to create a placement facade
     */
    private static class SingletonPlacementHolder {
        private static PlacementFacade INSTANCE = new PlacementFacade();
    }

    /**
     * This method is used to delete the placement of a table
     * @param idTable the id of the table to delete
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public void deletePlacement(int idTable) throws AccessDatabaseException{
        this.tableDAO.deletePlacement(idTable);
    }

    /**
     * This method is used to update the placement of a table
     * @param idTable the id of the table to update
     * @param x the x coordinate of the table
     * @param y the y coordinate of the table
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public void updatePlacement(int idTable, int x, int y) throws AccessDatabaseException{
        this.tableDAO.updatePlacement(idTable, x, y);
    }

    /**
     * This method is used to get a restaurant by its id
     * @param id the id of the restaurant
     * @return the restaurant
     * @throws AccessDatabaseException if there is a problem with the database
     * @throws RestaurantNotFoundException if the restaurant is not found
     */
    public Restaurant getRestaurantById(int id) throws AccessDatabaseException, RestaurantNotFoundException {
        return restaurantDAO.getRestaurant(id);
    }

    /**
     * This method is used to get all tables of a restaurant
     * @param id the id of the restaurant
     * @return a list of tables
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public List<Table> getAllTablesOfRestaurant(int id) throws AccessDatabaseException {
        return tableDAO.getAllTablesOfRestaurant(id);
    }

    /**
     * This method is used to get a table by its id
     * @param id the id of the table
     * @return the table
     */
    public Table getTableById(int id) {
        return tableDAO.getTableById(id);
    }

}
