
package project.persistence.product;

import java.sql.*;
import project.business.models.User;
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
    public User getByEmail(String email) {
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
        return null;


        /*
        try {
            //------------------PreparedStatement-------------------
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                // lecture du type de notre colonne
                resultatRequete.add(resultSet.getInt(1)); // id
                resultatRequete.add(resultSet.getString(2)); // name
                resultatRequete.add(resultSet.getString(3)); // first_name
                resultatRequete.add(resultSet.getString(4)); // mail
                resultatRequete.add(resultSet.getString(5)); // phone_number
                resultatRequete.add(resultSet.getString(6)); // address
                resultatRequete.add(resultSet.getString(7)); // login
            }
            resultSet.close();
            preparedStatement.close();
            return resultatRequete;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        finally {
            try {
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }*/


    }
}
