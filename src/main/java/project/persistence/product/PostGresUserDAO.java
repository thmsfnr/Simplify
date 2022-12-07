
package project.persistence.product;

import java.sql.*;
import project.business.models.User;
import project.persistence.factory.PostGresDAOFactory;

public class PostGresUserDAO extends UserDAO{
    @Override
    public User getByEmail(String email) {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        if(connection != null) {
            try {
                String query = "SELECT * FROM \"public\".\"User\" WHERE \"email\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
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
