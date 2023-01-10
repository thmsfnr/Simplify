
package project.business.facade;

import project.business.models.*;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.exceptions.UserNotFoundException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.abstr.OpinionDAO;
import project.persistence.product.abstr.RestaurantDAO;
import project.persistence.product.abstr.TableDAO;
import project.persistence.product.abstr.UserDAO;
import java.util.ArrayList;
import java.util.List;

public class RestaurantFacade {

    private OpinionDAO opinionDAO;
    private UserDAO userDAO;
    private TableDAO tableDAO;
    private RestaurantDAO restaurantDAO;

    /**
     * Constructor of the class RestaurantFacade
     */
    private RestaurantFacade() {
        AbstractDAOFactory factory = AbstractDAOFactory.getInstance();
        this.opinionDAO = factory.getOpinionDAO();
        this.userDAO = factory.getUserDAO();
        this.tableDAO = factory.getTableDAO();
        this.restaurantDAO = factory.getRestaurantDAO();
    }

    /**
     * This method is used to get the instance of the class RestaurantFacade
     * @return the instance of the class RestaurantFacade
     */
    public static RestaurantFacade getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * This private class is used to get the instance of the class RestaurantFacade
     * for thread safe lazy initialization
     */
    private static class SingletonHolder {
        private final static RestaurantFacade instance = new RestaurantFacade();
    }

    /**
     * This method is used to get the list of all the restaurants
     * @return the list of all the restaurants
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public List<Restaurant> getAllRestaurants() throws RestaurantNotFoundException, AccessDatabaseException {
        return this.restaurantDAO.getAllRestaurants();
    }

    /**
     * This method is used to get a restaurant by its id
     * @param id the id of the restaurant
     * @return the restaurant with the id
     * @throws RestaurantNotFoundException if the restaurant is not found
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public Restaurant getRestaurantById(int id) throws RestaurantNotFoundException, AccessDatabaseException {
        return this.restaurantDAO.getRestaurant(id);
    }

    /**
     * This method is used to create a restaurant
     * @param restaurant the restaurant to create
     * @return True if the restaurant is created, false otherwise
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public Restaurant createRestaurant(Restaurant restaurant) throws AccessDatabaseException {
        return this.restaurantDAO.createRestaurant(restaurant);
    }

    /**
     * This method is used to update restaurant
     * @param restaurant the restaurant to update
     * @return True if the restaurant is updated, false otherwise
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public Restaurant updateRestaurant(Restaurant restaurant) throws AccessDatabaseException {
        return this.restaurantDAO.updateRestaurant(restaurant);
    }

    /**
     * This method is used to delete a restaurant
     * @param idRestaurant the id of the restaurant to delete
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public void deleteRestauranteById(int idRestaurant) throws AccessDatabaseException {
        this.restaurantDAO.deleteRestaurant(idRestaurant);
    }

    /**
     * This method is used to get all restaurants of a user
     * @param id the id of the user
     * @return the list of all the restaurants of the user
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public List<Restaurant> getRestaurantsOfUser(int id) throws AccessDatabaseException {
        return this.restaurantDAO.getRestaurantsOfUser(id);
    }

    /**
     * This method is used to get all opinions of a restaurant
     * @param id the id of the restaurant
     * @return the list of all the opinions of the restaurant
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public ArrayList<Opinion> getOpinionsOfRestaurant(int id) throws AccessDatabaseException {
        return this.opinionDAO.getOpinionsOfRestaurant(id);
    }

    /**
     * This method is used to count all tables of a restaurant
     * @param id the id of the restaurant
     * @return the number of tables of the restaurant
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public int countOfTablesOfRestaurant(int id) throws AccessDatabaseException {
        return this.tableDAO.countOfTablesOfRestaurant(id);
    }

    /**
     * This method is used to get all managers
     * @return the list of all the managers
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public List<User> getAllManagers() throws AccessDatabaseException {
        return this.userDAO.getAllManagers();
    }

    /**
     * This method is used to get a manager by its email
     * @param email the email of the manager
     * @return the manager with the email
     * @throws UserNotFoundException if the manager is not found
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public User getManagerByEmail(String email) throws UserNotFoundException, AccessDatabaseException {
        return this.userDAO.getByEmail(email);
    }

    /**
     * This method is used to get a user with its id
     * @param id the id of the user
     * @return the user with the id
     * @throws UserNotFoundException if the user is not found
     * @throws AccessDatabaseException if there is a problem with the database
     */
    public User getUserById(int id) throws UserNotFoundException, AccessDatabaseException {
        return this.userDAO.getById(id);
    }

    /**
     * This method is used to create a restaurant facade
     */
    private static class SingletonRestaurantHolder {
        private final static RestaurantFacade instance = new RestaurantFacade();
    }

}
