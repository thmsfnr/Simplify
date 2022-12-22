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
}