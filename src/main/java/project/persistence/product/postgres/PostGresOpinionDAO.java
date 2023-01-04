
package project.persistence.product.postgres;

import project.business.models.Opinion;
import project.exceptions.AccessDatabaseException;
import java.util.List;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.OpinionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Simplify members on 28/12/22.
 * This class is the DAO of the opinion
 * It is used to call the methods of the database to get the informations of the opinion
 * @author Simplify members
 */
public class PostGresOpinionDAO extends OpinionDAO {

        /**
         * This method is used to get the opinion of a restaurant
         * @param id the id of the restaurant
         * @return the list of the opinions
         */
        @Override
        public ArrayList<Opinion> getOpinionsOfRestaurant(int id) throws AccessDatabaseException {
            Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

            if (connection != null) {
                try {
                    String query = "SELECT * FROM \"public\".\"Opinion\" AS O INNER JOIN \"public\".\"Opinion_restaurant\" AS R ON O.\"idOpinion\" = R.\"idOpinion\" WHERE R.\"idRestaurant\" = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    ArrayList<Opinion> opinions = new ArrayList<>();

                    while (resultSet.next()) {
                        Opinion opinion = new Opinion();
                        opinion.setIdOpinion(resultSet.getInt("idOpinion"));
                        opinion.setComment(resultSet.getString("comment"));
                        opinion.setIdUser(resultSet.getInt("idUser"));
                        opinions.add(opinion);
                    }
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    return opinions;
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new AccessDatabaseException();
                }
            } else {
                throw new AccessDatabaseException();
            }
        }

        /**
         * This method is used to create an opinion
         * @param opinion the opinion model with the information of the new opinion
         * @return True if the opinion is created, false otherwise
         */
        @Override
        public Boolean create(Opinion opinion) {
            // Get the connection to the database
            Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
            // If the connection works
            if (connection != null) {
                // Create the query
                try {
                    String query = "INSERT INTO \"public\".\"Opinion\" (\"idUser\", \"comment\") VALUES (?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, opinion.getIdUser());
                    preparedStatement.setString(2, opinion.getComment());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return false;
        }

        /**
         * This method is used to delete an opinion
         * @param id the id of the opinion
         * @return True if the opinion is deleted, false otherwise
         */
        @Override
        public Boolean delete(int id) {
            // Get the connection to the database
            Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
            // If the connection works
            if (connection != null) {
                // Create the query
                try {
                    String query = "DELETE FROM \"public\".\"Opinion\" WHERE \"idOpinion\" = ?";
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
                        throw new RuntimeException(e);
                    }
                }
            }
            return false;
        }

        /**
         * This method is used to update an opinion
         * @param opinion the opinion model with the information of the new opinion
         * @return True if the opinion is updated, false otherwise
         */
        @Override
        public Boolean update(Opinion opinion) {
            // Get the connection to the database
            Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
            // If the connection works
            if (connection != null) {
                // Create the query
                try {
                    String query = "UPDATE \"public\".\"Opinion\" SET \"idUser\" = ?, \"comment\" = ? WHERE \"idOpinion\" = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, opinion.getIdUser());
                    preparedStatement.setString(2, opinion.getComment());
                    preparedStatement.setInt(3, opinion.getIdOpinion());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return false;
        }


    /**
     * This method is used to get the list of opinions of a meal by the id of meal
     * @param idMeal the id of the meal
     * @return the list of opinions or null if the meal is not found and throw an exception
     */
    @Override
    public List<Opinion> getAllOpinionOfMeal(int idMeal) {
        ArrayList<Opinion> opinions = new ArrayList<>();
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Opinion\" WHERE \"idOpinion\" IN (SELECT \"idOpinion\" FROM \"public\".\"Opinion_meal\" WHERE \"idMeal\" = ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idMeal);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the meal is found in the database
                while(resultSet.next()) {
                    Opinion opinion = new Opinion(
                            resultSet.getInt("idOpinion"),
                            resultSet.getInt("idUser"),
                            resultSet.getString("comment")
                    );

                    opinions.add(opinion);
                 }
                resultSet.close();
                preparedStatement.close();
                return opinions;
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
        return opinions;
    }
    
    
    

        /**
         * This method is used to get the list of opinions of a user
         * @param id the id of the user
         * @return the list of opinions
         */
        @Override
        public List<Opinion> getByUser(int id) {
            ArrayList<Opinion> opinions = new ArrayList<>();
            // Get the connection to the database
            Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
            // If the connection works
            if (connection != null) {
                // Create the query
                try {
                    String query = "SELECT * FROM \"public\".\"Opinion\" WHERE \"idUser\" = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // If the meal is found in the database
                    while (resultSet.next()) {
                        Opinion opinion = new Opinion(
                                resultSet.getInt("idOpinion"),
                                resultSet.getInt("idUser"),
                                resultSet.getString("comment")
                        );

                        opinions.add(opinion);
                    }
                    resultSet.close();
                    preparedStatement.close();
                    return opinions;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return null;
        }
        
        

        /**
         * This method is used to get the list of opinions
         * @return the list of opinions
         */
        @Override
        public List<Opinion> getAll() {
            ArrayList<Opinion> opinions = new ArrayList<>();
            // Get the connection to the database
            Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
            // If the connection works
            if (connection != null) {
                // Create the query
                try {
                    String query = "SELECT * FROM \"public\".\"Opinion\"";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // If the meal is found in the database
                    while (resultSet.next()) {
                        Opinion opinion = new Opinion(
                                resultSet.getInt("idOpinion"),
                                resultSet.getInt("idUser"),
                                resultSet.getString("comment")
                        );

                        opinions.add(opinion);
                    }
                    resultSet.close();
                    preparedStatement.close();
                    return opinions;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return null;
        }

}
