package project.persistence.product;

import project.business.models.Restaurant;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class RestaurantDAO {

    public abstract Restaurant getRestaurant(int id) throws RestaurantNotFoundException, AccessDatabaseException;

    public abstract ArrayList<Restaurant> getAllRestaurants() throws AccessDatabaseException;

    public abstract Restaurant createRestaurant(Restaurant restaurant) throws AccessDatabaseException;

    public abstract Restaurant updateRestaurant(Restaurant restaurant) throws AccessDatabaseException;

    public abstract void deleteRestaurant(int id) throws AccessDatabaseException;

    public abstract ArrayList<Restaurant> getRestaurantsOfUser(int idUser) throws AccessDatabaseException;
}
