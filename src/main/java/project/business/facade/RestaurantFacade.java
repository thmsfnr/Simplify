package project.business.facade;

import project.business.models.*;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.exceptions.UserNotFoundException;
import project.persistence.factory.AbstractDAOFactory;
import project.persistence.product.UserDAO;
import project.persistence.product.*;


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



    /****** METHODS ******/


    public List<Restaurant> getAllRestaurants() throws RestaurantNotFoundException, AccessDatabaseException {
        return this.restaurantDAO.getAllRestaurants();
    }


    public Restaurant getRestaurantById(int id) throws RestaurantNotFoundException, AccessDatabaseException {
        return this.restaurantDAO.getRestaurant(id);
    }


    public Restaurant createRestaurant(Restaurant restaurant) throws AccessDatabaseException {
        return this.restaurantDAO.createRestaurant(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) throws AccessDatabaseException {
        return this.restaurantDAO.updateRestaurant(restaurant);
    }

    public void deleteRestauranteById(int idRestaurant) throws AccessDatabaseException {
        this.restaurantDAO.deleteRestaurant(idRestaurant);
    }

    public List<Restaurant> getRestaurantsOfUser(int id) throws AccessDatabaseException {
        return this.restaurantDAO.getRestaurantsOfUser(id);
    }

    public ArrayList<Opinion> getOpinionsOfRestaurant(int id) throws AccessDatabaseException {
        return this.opinionDAO.getOpinionsOfRestaurant(id);
    }

    public int countOfTablesOfRestaurant(int id) throws AccessDatabaseException {
        return this.tableDAO.countOfTablesOfRestaurant(id);
    }

    public List<User> getAllManagers() throws AccessDatabaseException {
        return this.userDAO.getAllManagers();
    }


    public User getManagerByEmail(String email) throws UserNotFoundException, AccessDatabaseException {
        return this.userDAO.getByEmail(email);
    }



    public User getUserById(int id) throws UserNotFoundException, AccessDatabaseException {
        return this.userDAO.getById(id);
    }



    private static class SingletonRestaurantHolder {
        private final static RestaurantFacade instance = new RestaurantFacade();
    }



}
