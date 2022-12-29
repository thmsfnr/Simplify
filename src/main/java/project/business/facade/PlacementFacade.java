package project.business.facade;

import project.business.models.Restaurant;
import project.business.models.Table;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.RestaurantDAO;
import project.persistence.product.TableDAO;

import java.util.List;

public class PlacementFacade {

    private RestaurantDAO restaurantDAO;
    private TableDAO tableDAO;

    /**  Singleton  **/
    private PlacementFacade() {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        this.restaurantDAO = factory.getRestaurantDAO();
        this.tableDAO = factory.getTableDAO();
    }


    public static PlacementFacade getInstance() {
        return SingletonPlacementHolder.INSTANCE;
    }

    private static class SingletonPlacementHolder {
        private static PlacementFacade INSTANCE = new PlacementFacade();
    }


    /**    Methods    **/

    public void deletePlacement(int idTable) throws AccessDatabaseException{
        this.tableDAO.deletePlacement(idTable);
    }


    public void updatePlacement(int idTable, int x, int y) throws AccessDatabaseException{
        this.tableDAO.updatePlacement(idTable, x, y);
    }


    public Restaurant getRestaurantById(int id) throws AccessDatabaseException, RestaurantNotFoundException {
        return restaurantDAO.getRestaurant(id);
    }

    public List<Table> getAllTablesOfRestaurant(int id) throws AccessDatabaseException {
        return tableDAO.getAllTablesOfRestaurant(id);
    }

    public Table getTableById(int id) {
        return tableDAO.getTableById(id);
    }






}