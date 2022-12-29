package project.persistence.product;

import project.business.models.Restaurant;
import project.exceptions.AccessDatabaseException;
import project.exceptions.RestaurantNotFoundException;
import project.persistence.factory.PostGresDAOFactory;

import java.sql.*;
import java.util.ArrayList;

public class PostGresRestaurantDAO extends RestaurantDAO{

    /**
     * This method is used to get a restaurant from the database
     * @param id the id of the restaurant
     * @return the restaurant
     * @throws RestaurantNotFoundException if the restaurant is not found
     * @throws AccessDatabaseException if there is a problem with the database
     */
    @Override
    public Restaurant getRestaurant(int id) throws RestaurantNotFoundException, AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                String query = "SELECT * FROM \"public\".\"Restaurant\" AS R1 LEFT JOIN \"public\".\"Respo_restaurant\" AS R2 ON R1.\"idRestaurant\" = R2.\"idRestaurant\" WHERE R1.\"idRestaurant\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();


                if(resultSet.next()){
                    Restaurant restaurant = new Restaurant();
                    restaurant.setIdRestaurant(resultSet.getInt("idRestaurant"));
                    restaurant.setName(resultSet.getString("name"));
                    restaurant.setAddress(resultSet.getString("address"));
                    restaurant.setNbOfStars(resultSet.getInt("nbOfStars"));
                    restaurant.setLength(resultSet.getInt("length"));
                    restaurant.setWidth(resultSet.getInt("width"));
                    restaurant.setPhoneNumber(resultSet.getString("phoneNumber"));
                    restaurant.setEmail(resultSet.getString("email"));
                    restaurant.setIdManager(resultSet.getInt("idUser"));

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    return restaurant;
                }
                else{
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    throw new RestaurantNotFoundException();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }


    /**
     * This method is used to get all the restaurants from the database
     * @return an arraylist of all the restaurants
     * @throws AccessDatabaseException if there is a problem with the database
     */
    @Override
    public ArrayList<Restaurant> getAllRestaurants() throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                String query = "SELECT * FROM \"public\".\"Restaurant\" LEFT JOIN \"public\".\"Respo_restaurant\" ON \"public\".\"Restaurant\".\"idRestaurant\" = \"public\".\"Respo_restaurant\".\"idRestaurant\"";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                ArrayList<Restaurant> restaurants = new ArrayList<>();
                while(resultSet.next()){
                    Restaurant restaurant = new Restaurant();
                    restaurant.setIdRestaurant(resultSet.getInt("idRestaurant"));
                    restaurant.setName(resultSet.getString("name"));
                    restaurant.setAddress(resultSet.getString("address"));
                    restaurant.setNbOfStars(resultSet.getInt("nbOfStars"));
                    restaurant.setLength(resultSet.getInt("length"));
                    restaurant.setWidth(resultSet.getInt("width"));
                    restaurant.setPhoneNumber(resultSet.getString("phoneNumber"));
                    restaurant.setEmail(resultSet.getString("email"));
                    restaurant.setIdManager(resultSet.getInt("idUser"));
                    restaurants.add(restaurant);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return restaurants;

            } catch (SQLException e) {
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }


    /**
     * This method is used to create a restaurant in the database
     * @param restaurant the restaurant to be created
     * @return the restaurant that was created
     * @throws AccessDatabaseException if there is a problem with the database
     */
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) throws AccessDatabaseException{
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();


        if(connection != null){

            try{
                connection.setAutoCommit(false);
                Savepoint savepoint = connection.setSavepoint("savepoint");
                try{
                    String query = "INSERT INTO \"public\".\"Restaurant\" (\"name\", \"address\", \"nbOfStars\", \"length\", \"width\", \"phoneNumber\", \"email\") VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, restaurant.getName());
                    preparedStatement.setString(2, restaurant.getAddress());
                    preparedStatement.setInt(3, restaurant.getNbOfStars());
                    preparedStatement.setInt(4, restaurant.getLength());
                    preparedStatement.setInt(5, restaurant.getWidth());
                    preparedStatement.setString(6, restaurant.getPhoneNumber());
                    preparedStatement.setString(7, restaurant.getEmail());
                    preparedStatement.executeUpdate();

                    String query3 = "SELECT * FROM \"public\".\"Restaurant\" WHERE \"name\" = ? AND \"address\" = ? AND \"nbOfStars\" = ? AND \"length\" = ? AND \"width\" = ? AND \"phoneNumber\" = ? AND \"email\" = ?";
                    PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                    preparedStatement3.setString(1, restaurant.getName());
                    preparedStatement3.setString(2, restaurant.getAddress());
                    preparedStatement3.setInt(3, restaurant.getNbOfStars());
                    preparedStatement3.setInt(4, restaurant.getLength());
                    preparedStatement3.setInt(5, restaurant.getWidth());
                    preparedStatement3.setString(6, restaurant.getPhoneNumber());
                    preparedStatement3.setString(7, restaurant.getEmail());
                    ResultSet resultSet = preparedStatement3.executeQuery();

                    if(resultSet.next()){
                        restaurant.setIdRestaurant(resultSet.getInt("idRestaurant"));
                    }
                    else{
                        connection.rollback(savepoint);
                        throw new AccessDatabaseException();
                    }

                    String query2 = "INSERT INTO \"public\".\"Respo_restaurant\" (\"idUser\", \"idRestaurant\") VALUES (?, ?)";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setInt(1, restaurant.getIdManager());
                    preparedStatement2.setInt(2, restaurant.getIdRestaurant());

                    preparedStatement2.executeUpdate();

                    connection.commit();

                    preparedStatement.close();
                    connection.close();
                    return restaurant;

                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connection.rollback(savepoint);
                    } catch (SQLException ex) {
                        throw new AccessDatabaseException();
                    }
                    throw new AccessDatabaseException();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }


    /**
     * This method is used to update a restaurant in the database
     * @param restaurant the restaurant to be updated
     * @return the restaurant that was updated
     * @throws AccessDatabaseException if there is a problem with the database
     */
    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                connection.setAutoCommit(false);
                Savepoint savepoint = connection.setSavepoint("savepoint");
                try{
                    String query = "UPDATE \"public\".\"Restaurant\" SET \"name\" = ?, \"address\" = ?, \"nbOfStars\" = ?, \"length\" = ?, \"width\" = ?, \"phoneNumber\" = ?, \"email\" = ? WHERE \"idRestaurant\" = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, restaurant.getName());
                    preparedStatement.setString(2, restaurant.getAddress());
                    preparedStatement.setInt(3, restaurant.getNbOfStars());
                    preparedStatement.setInt(4, restaurant.getLength());
                    preparedStatement.setInt(5, restaurant.getWidth());
                    preparedStatement.setString(6, restaurant.getPhoneNumber());
                    preparedStatement.setString(7, restaurant.getEmail());
                    preparedStatement.setInt(8, restaurant.getIdRestaurant());
                    preparedStatement.executeUpdate();

                    String query2 = "UPDATE \"public\".\"Respo_restaurant\" SET \"idUser\" = ? WHERE \"idRestaurant\" = ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setInt(1, restaurant.getIdManager());
                    preparedStatement2.setInt(2, restaurant.getIdRestaurant());
                    preparedStatement2.executeUpdate();

                    connection.commit();

                    preparedStatement.close();
                    connection.close();
                    return restaurant;

                } catch (SQLException e) {
                    connection.rollback(savepoint);
                    e.printStackTrace();
                    throw new AccessDatabaseException();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }


    /**
     * This method is used to delete a restaurant from the database
     * @param id the id of the restaurant to be deleted
     * @throws AccessDatabaseException if there is a problem with the database
     */
    //TODO : delete the tables associations with the managers
    @Override
    public void deleteRestaurant(int id) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                connection.setAutoCommit(false);
                Savepoint savepoint = connection.setSavepoint("savepoint");
                try{
                    String query = "DELETE FROM \"public\".\"Restaurant\" WHERE \"idRestaurant\" = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    preparedStatement.executeUpdate();

                    String query2 = "DELETE FROM \"public\".\"Respo_restaurant\" WHERE \"idRestaurant\" = ?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setInt(1, id);
                    preparedStatement2.executeUpdate();

                    connection.commit();

                    preparedStatement.close();
                    connection.close();

                } catch (SQLException e) {
                    connection.rollback(savepoint);
                    e.printStackTrace();
                    throw new AccessDatabaseException();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }



    /**
     * This method is used to get all the restaurants from the database
     * @return an arraylist of all the restaurants
     * @throws AccessDatabaseException if there is a problem with the database
     */
    @Override
    public ArrayList<Restaurant> getRestaurantsOfUser(int idUser) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                String query = "SELECT * FROM \"public\".\"Restaurant\" WHERE \"idRestaurant\" IN (SELECT \"idRestaurant\" FROM \"public\".\"Respo_restaurant\" WHERE \"idUser\" = ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idUser);
                ResultSet resultSet = preparedStatement.executeQuery();

                ArrayList<Restaurant> restaurants = new ArrayList<>();
                while(resultSet.next()){
                    Restaurant restaurant = new Restaurant();
                    restaurant.setIdRestaurant(resultSet.getInt("idRestaurant"));
                    restaurant.setName(resultSet.getString("name"));
                    restaurant.setAddress(resultSet.getString("address"));
                    restaurant.setNbOfStars(resultSet.getInt("nbOfStars"));
                    restaurant.setLength(resultSet.getInt("length"));
                    restaurant.setWidth(resultSet.getInt("width"));
                    restaurant.setPhoneNumber(resultSet.getString("phoneNumber"));
                    restaurant.setEmail(resultSet.getString("email"));
                    restaurant.setIdManager(idUser);
                    restaurants.add(restaurant);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return restaurants;
            } catch(SQLException e) {
                throw new AccessDatabaseException();
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }
}
