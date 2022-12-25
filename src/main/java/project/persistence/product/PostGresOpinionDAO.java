package project.persistence.product;

import project.business.models.Opinion;
import project.persistence.factory.PostGresDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostGresOpinionDAO extends OpinionDAO {

    @Override
    public List<Opinion> getAllOpinionOfMeal(int idMeal) {
        System.out.println(idMeal);
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
