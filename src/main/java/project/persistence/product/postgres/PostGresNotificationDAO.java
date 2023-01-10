package project.persistence.product.postgres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.business.models.Notification;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.NotificationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostGresNotificationDAO extends NotificationDAO {

    @Override
    public Boolean insertNotification(Notification notification) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "INSERT INTO \"public\".\"Notification\" (\"idUser\",\"title\",\"description\") VALUES (?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, notification.getIdUser());
                preparedStatement.setString(2, notification.getTitle());
                preparedStatement.setString(3, notification.getDescription());
                preparedStatement.execute();
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public Boolean deleteNotification(int idNotification) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "DELETE FROM \"public\".\"Notification\" WHERE \"idNotification\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idNotification);
                preparedStatement.execute();
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public List<Notification> getAllNotifications(int idUser) {
        ArrayList<Notification> notifications = new ArrayList<>();
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Notification\" WHERE \"idUser\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idUser);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the meal is found in the database
                while(resultSet.next()) {
                    Notification notification = new Notification(
                            resultSet.getInt("idNotification"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getInt("idUser")
                    );
                    notifications.add(notification);
                }
                resultSet.close();
                preparedStatement.close();
                return notifications;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return notifications;
    }

}
