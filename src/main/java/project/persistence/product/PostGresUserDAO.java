
package project.persistence.product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
     * This method is used to get all the users in the database
     * @return the list of the users
     */
    @Override
    public List<User> getAll() {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"User\";";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<User> users = new ArrayList<User>();
                // If the user is found in the database
                while(resultSet.next()) {
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
                    users.add(user);
                }
                resultSet.close();
                preparedStatement.close();
                return users;
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
     * This method is used to update the user in the database
     * @param user the user to update
     */
    @Override
    public Boolean update(User user) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "UPDATE \"public\".\"User\" SET \"password\" = ?, \"email\" = ?, \"name\" = ?, \"firstname\" = ?, \"address\" = ?, \"phone\" = ?, \"ban\" = ?, \"askDelete\" = ?, \"idRole\" = ? WHERE \"idUser\" = ?;";
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
                preparedStatement.setInt(10, user.getId());
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
     * This method is used to delete the user in the database
     * @param id the id of the user to delete
     */
    @Override
    public Boolean delete(int id) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "DELETE FROM \"public\".\"User\" WHERE \"idUser\" = ?;";
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
     * This method is used to get the user by his id
     * @param id the id of the user
     * @return the user
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public User getById(int id) throws UserNotFoundException {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"User\" WHERE \"idUser\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
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
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * This method is used to get the list of user that have ask to delete their account
     * @return the list of the users
     */
    public List<User> getAskDelete() {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"User\" WHERE \"askDelete\" = true;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<User> users = new ArrayList<User>();
                // If the user is found in the database
                while(resultSet.next()) {
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
                    users.add(user);
                }
                resultSet.close();
                preparedStatement.close();
                return users;
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
