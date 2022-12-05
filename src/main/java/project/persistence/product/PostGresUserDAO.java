
package project.persistence.product;

import java.sql.*;
import java.util.ArrayList;

public class PostGresUserDAO extends UserDAO{
    @Override
    public ArrayList<Object> getByMail(String mail) {
        Connection connection = null;
        ArrayList<Object> resultatRequete = new ArrayList<>();
        String query = "SELECT * FROM \"public\".\"User\" WHERE \"mail\" = ?;";
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://postgresql-simplify.alwaysdata.net/simplify_bd","simplify","*mogsu52fA*");
            connection.isValid(2);

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
        }
    }
}
