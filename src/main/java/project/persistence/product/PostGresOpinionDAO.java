package project.persistence.product;

import project.business.models.Opinion;
import project.exceptions.AccessDatabaseException;
import java.util.List;
import project.persistence.factory.PostGresDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PostGresOpinionDAO extends OpinionDAO {


    /**
     * This method returns all the opinions of a restaurant
     * @param id
     * @return
     */
    @Override
    public ArrayList<Opinion> getOpinionsOfRestaurant(int id) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                String query = "SELECT * FROM \"public\".\"Opinion\" AS O INNER JOIN \"public\".\"Opinion_restaurant\" AS R ON O.\"idOpinion\" = R.\"idOpinion\" WHERE R.\"idRestaurant\" = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<Opinion> opinions = new ArrayList<>();

                while(resultSet.next()){
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
            }
            catch(SQLException e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
        else{
            throw new AccessDatabaseException();
        }

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
}