package project.persistence.product;

import project.business.models.Reservation;
import project.exceptions.AccessDatabaseException;
import project.persistence.factory.PostGresDAOFactory;

import java.sql.*;
import java.util.List;

public class PostGresReservationDAO extends ReservationDAO{
    @Override
    public Boolean create(Reservation reservation) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection != null){
            try{
                connection.setAutoCommit(false);
                Savepoint savepoint = connection.setSavepoint("savepoint");
                try{
                    //insert into order
                    String query = "INSERT INTO \"public\".\"Order\" (\"idTypeOrder\", \"idRestaurant\", \"idUser\", \"idState\", \"date\") VALUES (?,?,?,?,?);";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, 2); // 2 is the id of the reservation (not delivery)
                    preparedStatement.setInt(2,reservation.getIdRestaurant());
                    preparedStatement.setInt(3,reservation.getIdUser());
                    preparedStatement.setInt(4,1); // 1 is the id of the state "waiting for confirmation"
                    preparedStatement.setDate(5,reservation.getDate());
                    preparedStatement.executeUpdate();

                    // Get the id of the order
                    String query2 = "SELECT \"idOrder\" FROM \"public\".\"Order\" WHERE \"idTypeOrder\" = ? AND \"idRestaurant\" = ? AND \"idUser\" = ? AND \"idState\" = ? AND \"date\" = ?;";
                    preparedStatement = connection.prepareStatement(query2);
                    preparedStatement.setInt(1, 2);
                    preparedStatement.setInt(2,reservation.getIdRestaurant());
                    preparedStatement.setInt(3,reservation.getIdUser());
                    preparedStatement.setInt(4,1);
                    preparedStatement.setDate(5,reservation.getDate());
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if(resultSet.next()){
                        reservation.setIdOrder(resultSet.getInt("idOrder"));
                    }
                    else{
                        connection.rollback(savepoint);
                        throw new AccessDatabaseException();
                    }
                    //creation order_meal
                    if(reservation.getMeals() != null){
                        for(Integer idMeal : reservation.getMeals().keySet()){
                            query = "INSERT INTO \"public\".\"Meal_ordered\" (\"idMeal\", \"idOrder\", \"quantity\") VALUES (?,?,?);";
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1,idMeal);
                            preparedStatement.setInt(2,reservation.getIdOrder());
                            preparedStatement.setInt(3,reservation.getMeals().get(idMeal));
                            preparedStatement.executeUpdate();
                        }
                    }

                    //creation order_table
                    if(reservation.getTables() != null){
                        for(Integer idTable : reservation.getTables()){
                            query = "INSERT INTO \"public\".\"Reserve_Table\" (\"idOrder\", \"idTable\") VALUES (?,?);";
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1,reservation.getIdOrder());
                            preparedStatement.setInt(2,idTable);
                            preparedStatement.executeUpdate();
                        }
                    }
                    connection.commit();

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    return true;

                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connection.rollback(savepoint);
                    } catch (SQLException ex) {
                        throw new AccessDatabaseException();
                    }
                    throw new AccessDatabaseException();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
                throw new AccessDatabaseException();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else{
            throw new AccessDatabaseException();
        }
    }

    @Override
    public Boolean update(Reservation reservation) {
        //update date

        //update meals

        //update tables
        return null;
    }

    @Override
    public Boolean delete(int idReservation) {
        return null;
    }

    @Override
    public Reservation getById(int idReservation) {
        return null;
    }

    @Override
    public List<Reservation> getAllReservations(int idRestaurant) {
        return null;
    }

    @Override
    public List<Reservation> getAllReservationsOfUser(int idUser) {
        return null;
    }

    @Override
    // REVOIR PAS FINI
    //TODO
    public Boolean cancelReservation(Reservation reservation) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "UPDATE \"public\".\"Order\" SET \"idOrder\" = ?, \"idTypeOrder\" = ?, \"idRestaurant\" = ?, \"idUser\" = ?, \"idState\" = ?, \"date\" = ? WHERE \"idOrder\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, reservation.getIdOrder());        // id 5 is the id of the state "canceled"
                preparedStatement.setInt(2, reservation.getIdTypeOrder());
                preparedStatement.setInt(3, reservation.getIdRestaurant());
                preparedStatement.setInt(4, reservation.getIdUser());
                preparedStatement.setInt(5, 5);
                preparedStatement.setDate(6, reservation.getDate());
                preparedStatement.setInt(7, reservation.getIdOrder());
                preparedStatement.execute();
                preparedStatement.close();
                return true;
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
        return false;
    }

    @Override
    public Boolean acceptReservation(Reservation reservation) {
        return null;
    }

    @Override
    public Boolean rejectReservation(Reservation reservation) {
        return null;
    }
}
