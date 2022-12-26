package project.persistence.product;

import project.business.models.Opinion;
import project.exceptions.AccessDatabaseException;
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
}