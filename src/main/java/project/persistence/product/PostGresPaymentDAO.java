
package project.persistence.product;

import project.business.models.Payment;
import project.persistence.factory.PostGresDAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Simplify members on 27/12/22.
 * This class is the PostGresPaymentDAO
 * It is used to manage payments in the database
 * it extends the class PaymentDAO to Override the methods
 * @author Simplify members
 */
public class PostGresPaymentDAO extends PaymentDAO {

    /**
     * This method is used to create a payment
     * @param payment the payment to create
     * @return true if the payment is created, false otherwise
     */
    @Override
    public Boolean create(Payment payment) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "INSERT INTO \"public\".\"Payment\" (\"idUser\", \"amount\", \"date\", \"numbercard\") VALUES (?, ?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, payment.getIdUser());
                preparedStatement.setString(2, payment.getAmount());
                preparedStatement.setDate(3, payment.getDate());
                preparedStatement.setString(4, payment.getCardNumber());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * This method is used to delete a payment
     * @param id the id of the payment to delete
     * @return true if the payment is deleted, false otherwise
     */
    @Override
    public Boolean delete(int id) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "DELETE FROM \"public\".\"Payment\" WHERE \"idPayment\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * This method is used to get all the payments
     * @return the list of the payments
     */
    @Override
    public List<Payment> getAllPayments() {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Payment\";";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeQuery();
                preparedStatement.close();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * This method is used to get all the payments of a user
     * @param id the id of the user
     * @return the list of the payments of the user
     */
    @Override
    public List<Payment> getAllPaymentsOfUser(int id) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Payment\" WHERE \"idUser\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeQuery();
                preparedStatement.close();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
