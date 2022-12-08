
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
                            resultSet.getInt("id"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("name"),
                            resultSet.getString("firstname"),
                            resultSet.getString("address"),
                            resultSet.getString("phone")
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
}
