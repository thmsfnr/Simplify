package project.persistence.product.postgres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.business.models.Event;
;import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.EventDAO;
import project.utilities.LocalStorage;

import java.io.IOException;
import java.sql.*;

public class PostGresEventDAO extends EventDAO {


    @Override
    public Boolean createEvent(Event event)
    {
        Boolean result = false;

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "INSERT INTO \"public\".\"Event\" (\"idRestaurant\", \"date\", \"description\", \"title\") VALUES (?, ?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                // get the restaurant id from the file restaurant_idLs.txt
                int id = (int) LocalStorage.load("restaurant_id");
                preparedStatement.setInt(1, id);
                preparedStatement.setTimestamp(2,event.getDate());
                preparedStatement.setString(3, event.getDescription());
                preparedStatement.setString(4, event.getTitle());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                result = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
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
     * This method is used to update an event
     * @param event the event to update
     * @return a boolean, true if the event is updated, false otherwise
     */
    @Override
    public Boolean updateEvent(Event event) {
        Boolean result = false;

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "UPDATE \"public\".\"Event\" SET \"title\" = ?, \"description\" = ?,\"date\" = ?  WHERE \"idEvent\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, event.getTitle());
                preparedStatement.setString(2, event.getDescription());
                // transformer Locale date en string
                preparedStatement.setTimestamp(3, event.getDate());
                preparedStatement.setInt(4, event.getIdEvent());
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
     * This method is used to get an event by its id
     * @return the Event with the id given
     */
    @Override
    public Event getEventById(Integer idEvent) {
        Event result = null;

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Event\" WHERE \"idEvent\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idEvent);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("idEvent");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    Timestamp date = resultSet.getTimestamp("date");
                    result = new Event(id, title, description, date);
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
     * This method is used to delete an event by its id
     * @return a Boolean, true if the Event is deleted, false otherwise
     */
    @Override
    public boolean deleteEvent(Integer idEvent) {
        Boolean result = false;

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "DELETE FROM \"public\".\"Event\" WHERE \"idEvent\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idEvent);
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
     * This method is used to get all the events
     * @return a list of events
     */
    @Override
    public ObservableList<Event> getAllEvents() {
        ObservableList<Event> result = FXCollections.observableArrayList();

        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if (connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Event\" WHERE \"idRestaurant\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                // get the restaurant id from the file restaurant_idLs.txt
                int id = (int) LocalStorage.load("restaurant_id");
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Integer idEvent = resultSet.getInt("idEvent");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    Timestamp date = resultSet.getTimestamp("date");
                    result.add(new Event(idEvent, title, description, date));
                }
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
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
