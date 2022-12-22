package project.persistence.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.persistence.factory.PostGresDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import project.business.models.Table;
public class PostGresTableDAO extends TableDAO {

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
                    result.add(new Table(id, name, description, booked));
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
                    result = new Table(idTable, name, description, booked);
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
}