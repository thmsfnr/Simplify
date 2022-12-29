package project.persistence.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.exceptions.AccessDatabaseException;
import project.persistence.factory.PostGresDAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.business.models.Table;

/**
 * Created by Simplify members on 22/12/22.
 * This class is the DAO of the table
 * It is used to communicate with the database
 * It's a singleton
 *
 * @author Simplify members
 */
public class PostGresTableDAO extends TableDAO {

    /**
     * This method is used to create a table
     * @return a boolean, true if the table is created, false otherwise
     */
    @Override
    public Boolean insertTable(String name, String description) {
        Boolean result = false;

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "INSERT INTO \"public\".\"Table\" (\"name\", \"description\") VALUES (?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, description);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    /**
     * This method is used to get all the tables
     * @return a list of tables
     */
    @Override
    public ObservableList<Table> getAllTables() {
        ObservableList<Table> result = FXCollections.observableArrayList();

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Table\";";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("idTable");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Boolean booked = resultSet.getBoolean("booked");
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    result.add(new Table(id, name, description, booked, x, y));
                }
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    /**
     * This method is used to delete a table by its id
     * @param id the id of the table
     * @return the table
     */
    @Override
    public Boolean deleteTable(int id) {
        Boolean result = false;

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "DELETE FROM \"public\".\"Table\" WHERE \"idTable\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    /**
     * This method is used to update a table
     * @param table the table to update
     * @return a boolean, true if the table is updated, false otherwise
     */
    @Override
    public Boolean updateTable(Table table) {
        Boolean result = false;

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "UPDATE \"public\".\"Table\" SET \"name\" = ?, \"description\" = ?,\"booked\" = ?  WHERE \"idTable\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, table.getName());
                preparedStatement.setString(2, table.getDescription());
                preparedStatement.setBoolean(3, table.getBooked());
                preparedStatement.setInt(4, table.getIdTable());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    /**
     * This method is used to get a table by its id
     * @param id the id of the table
     * @return the table
     */

    @Override
    public Table getTableById(int id) {
        Table result = null;

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Table\" WHERE \"idTable\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Integer idTable = resultSet.getInt("idTable");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Boolean booked = resultSet.getBoolean("booked");
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    result = new Table(idTable, name, description, booked, x, y);
                }
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }


    /**
     * This method is used to get the number of tables in a restaurant
     * @param idRestaurant the id of the restaurant
     * @return the table
     */
    @Override
    public int countOfTablesOfRestaurant(int idRestaurant) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                String query = "SELECT COUNT(*) FROM \"public\".\"Table\" WHERE \"idRestaurant\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idRestaurant);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    return resultSet.getInt(1);
                }
                return 0;

            }
            catch(SQLException e){
                throw new AccessDatabaseException(e);
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }


    /**
     * This method is used to get the number of tables in a restaurant
     * @param idRestaurant the id of the restaurant
     * @return list of tables
     */
    @Override
    public List<Table> getAllTablesOfRestaurant(int idRestaurant) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                String query = "SELECT * FROM \"public\".\"Table\" WHERE \"idRestaurant\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idRestaurant);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Table> tables = new ArrayList<>();
                while(resultSet.next()){
                    Integer idTable = resultSet.getInt("idTable");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Boolean booked = resultSet.getBoolean("booked");
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    tables.add(new Table(idTable, name, description, booked, x, y));
                }
                return tables;
            }
            catch(SQLException e){
                throw new AccessDatabaseException(e);
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }

    /**
     * This method is used to update the placement of a table
     * @param idTable
     * @param x
     * @param y
     * @throws AccessDatabaseException
     */
    @Override
    public void updatePlacement(int idTable, int x, int y) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                String query = "UPDATE \"public\".\"Table\" SET \"x\" = ?, \"y\" = ? WHERE \"idTable\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, x);
                preparedStatement.setInt(2, y);
                preparedStatement.setInt(3, idTable);
                preparedStatement.executeUpdate();
            }
            catch(SQLException e){
                throw new AccessDatabaseException(e);
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }


    /**
     * This method is used to delete the position stored in the database
     * @param idTable
     * @throws AccessDatabaseException
     */
    @Override
    public void deletePlacement(int idTable) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                String query = "UPDATE \"public\".\"Table\" SET \"x\" = ?, \"y\" = ? WHERE \"idTable\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, -1);
                preparedStatement.setInt(2, -1);
                preparedStatement.setInt(3, idTable);
                preparedStatement.executeUpdate();
            }
            catch(SQLException e){
                throw new AccessDatabaseException(e);
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }
}