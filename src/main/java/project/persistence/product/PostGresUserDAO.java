
package project.persistence.product;

import java.sql.*;
import project.business.models.User;
import project.exceptions.UserNotFoundException;
import project.persistence.factory.PostGresDAOFactory;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the PostGresUserDAO
 * It is used to get the informations of a user from the database
 * it extends the class UserDAO to Override the method getUser to make the query to the database
 * @author Simplify members
 */
public class PostGresUserDAO extends UserDAO{

    /**
     * This method is used to get the user from the database by his email
     * @param email the email of the user
     * @return the user if he is found in the database, null otherwise
     */
    @Override
    public User getByEmail(String email) throws UserNotFoundException {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        // If the connection works
        if(connection != null) {

            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"User\" WHERE \"email\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the user is found in the database
                if(resultSet.next()) {
                    User user = new User(
                            resultSet.getInt("idUser"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("name"),
                            resultSet.getString("firstname"),
                            resultSet.getString("address"),
                            resultSet.getString("phone"),
                            resultSet.getBoolean("ban"),
                            resultSet.getBoolean("askDelete"),
                            resultSet.getInt("idRole")
                    );
                    resultSet.close();
                    preparedStatement.close();
                    return user;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * This method is used to save the user in the database
     * @param user the user to save
     */
    @Override
    public Boolean create(User user){
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "INSERT INTO \"public\".\"User\" (\"password\", \"email\", \"name\", \"firstname\", \"address\", \"phone\", \"ban\", \"askDelete\",\"idRole\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user.getPassword());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getFirstname());
                preparedStatement.setString(5, user.getAddress());
                preparedStatement.setString(6, user.getPhone());
                preparedStatement.setBoolean(7, user.getBan());
                preparedStatement.setBoolean(8, user.getAskDelete());
                preparedStatement.setInt(9, user.getRole());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
