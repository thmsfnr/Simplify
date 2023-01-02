package project.persistence.product;

import project.business.models.Delivery;
import project.business.models.State;
import project.exceptions.AccessDatabaseException;
import project.persistence.factory.PostGresDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simplify members on 02/01/23.
 */
public class PostGresDeliveryDAO extends DeliveryDAO{

    /**
     * This method is used to create a delivery
     * @param delivery the delivery to create
     * @return true if the delivery is created, false otherwise
     */
    @Override
    public boolean createDelivery(Delivery delivery) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection == null){
            throw new AccessDatabaseException();
        }
        else{
            try{
                String query = "INSERT INTO \"public\".\"Order\" (\"idTypeOrder\", \"idRestaurant\", \"idUser\", \"accepted\", \"idState\") VALUES (1,?,?,false,1);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, delivery.getIdRestaurant());
                preparedStatement.setInt(2, delivery.getIdUser());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
    }


    /**
     * This method is used to get all the deliveries that exist in the database
     * @return a list of all the deliveries
     */
    @Override
    public List<Delivery> getAllDeliveries() throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection == null){
            throw new AccessDatabaseException();
        }
        else{
            try{
                String query = "SELECT * FROM \"public\".\"Order\" LEFT JOIN \"public\".\"State_order\" WHERE \"idTypeOrder\" = 1;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Delivery> deliveries = new ArrayList<>();
                while(resultSet.next()){
                    String state_string = resultSet.getString("description").toUpperCase().replace(" ", "_");
                    State state = State.valueOf(state_string);
                    Delivery delivery = new Delivery(
                            resultSet.getInt("idOrder"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getInt("idUser"),
                            resultSet.getDate("date"),
                            state
                    );
                    deliveries.add(delivery);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return deliveries;
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
    }


    /**
     * This method is used to get all the deliveries of a user
     * @param id the id of the user
     * @return the list of the deliveries of the user
     */
    @Override
    public List<Delivery> getAllDeliveriesOfUser(int id) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection == null){
            throw new AccessDatabaseException();
        }
        else{
            try{
                String query = "SELECT * FROM \"public\".\"Order\" AS O LEFT JOIN \"public\".\"State_order\" AS S ON O.\"idState\" = S.\"idState\" WHERE \"idTypeOrder\" = 1 AND \"idUser\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Delivery> deliveries = new ArrayList<>();
                while(resultSet.next()){
                    String state_string = resultSet.getString("description").toUpperCase().replace(" ", "_");
                    State state = State.valueOf(state_string);
                    Delivery delivery = new Delivery(
                            resultSet.getInt("idOrder"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getInt("idUser"),
                            resultSet.getDate("date"),
                            state
                    );
                    deliveries.add(delivery);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return deliveries;
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
    }


    /**
     * This method is used to get a delivery by its id
     * @param deliveryId the id of the delivery
     * @return the delivery
     */
    @Override
    public Delivery getDeliveryById(int deliveryId) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection == null){
            throw new AccessDatabaseException();
        }
        else{
            try{
                String query = "SELECT * FROM \"public\".\"Order\" LEFT JOIN \"public\".\"State_order\" WHERE \"idTypeOrder\" = 1 AND \"idOrder\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, deliveryId);
                ResultSet resultSet = preparedStatement.executeQuery();
                Delivery delivery = null;
                if(resultSet.next()){
                    String state_string = resultSet.getString("description").toUpperCase().replace(" ", "_");
                    State state = State.valueOf(state_string);
                    delivery = new Delivery(
                            resultSet.getInt("idOrder"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getInt("idUser"),
                            resultSet.getDate("date"),
                            state
                    );
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return delivery;
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
    }


    /**
     * This method is used to update a delivery
     * @param delivery the delivery to update
     * @return  true if the delivery is updated, false otherwise
     */
    @Override
    public boolean updateDelivery(Delivery delivery) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection == null){
            throw new AccessDatabaseException();
        }
        else{
            try{
                String state = delivery.getState().toString().toLowerCase().replace("_", " ");
                String query1 = "SELECT idState FROM \"public\".\"State_order\" WHERE description = ?;";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                String state_string = delivery.getState().toString().toLowerCase().replace("_", " ");
                state_string = state_string.substring(0, 1).toUpperCase() + state_string.substring(1);
                preparedStatement1.setString(1, state_string);
                ResultSet resultSet = preparedStatement1.executeQuery();
                int idState = 0;
                if(resultSet.next()){
                    idState = resultSet.getInt("idState");
                }
                resultSet.close();
                preparedStatement1.close();

                if(idState != 0){
                    String query2 = "UPDATE \"public\".\"Order\" SET \"idState\" = ? WHERE \"idOrder\" = ?;";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setInt(1, idState);
                    preparedStatement2.setInt(2, delivery.getIdDelivery());
                    preparedStatement2.executeUpdate();
                    preparedStatement2.close();
                    connection.close();
                    return true;
                }
                else{
                    connection.close();
                    return false;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
    }


    /**
     * This method is used to delete a delivery
     * @param id the id of the delivery to delete
     * @return true if the delivery is deleted, false otherwise
     */
    @Override
    public void deleteDelivery(int id) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection == null){
            throw new AccessDatabaseException();
        }
        else{
            try{
                String query = "DELETE FROM \"public\".\"Order\" WHERE \"idOrder\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.close();
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
    }

    @Override
    public void changeStateOfDelivery(int idDelivery, String state) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection == null){
            throw new AccessDatabaseException();
        }
        else{
            try{
                String query1 = "SELECT \"idState\" FROM \"public\".\"State_order\" WHERE \"description\" = ?;";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                preparedStatement1.setString(1, state);
                ResultSet resultSet = preparedStatement1.executeQuery();
                int idState = 0;
                if(resultSet.next()){
                    idState = resultSet.getInt("idState");
                }
                resultSet.close();
                preparedStatement1.close();

                if(idState != 0){
                    String query2 = "UPDATE \"public\".\"Order\" SET \"idState\" = ? WHERE \"idOrder\" = ?;";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setInt(1, idState);
                    preparedStatement2.setInt(2, idDelivery);
                    preparedStatement2.executeUpdate();
                    preparedStatement2.close();
                    connection.close();
                }
                else{
                    connection.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }
        }
    }
}
