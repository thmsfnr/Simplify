package project.persistence.product.postgres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.business.models.Reservation;
import project.business.models.State;
import project.business.models.Table;
import project.exceptions.AccessDatabaseException;
import project.exceptions.ReservationNotFoundException;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.ReservationDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostGresReservationDAO extends ReservationDAO {
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
                            System.out.println(idTable);
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
    public Boolean update(Reservation reservation) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                Savepoint savepoint = connection.setSavepoint("savepoint");
                try {
                    //update order
                    String query = "UPDATE \"public\".\"Order\" SET \"idOrder\" = ?, \"idTypeOrder\" = ?, \"idRestaurant\" = ?, \"idUser\" = ?, \"idState\" = ?, \"date\" = ? WHERE \"idOrder\" = ?;";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, reservation.getIdOrder());
                    preparedStatement.setInt(2, reservation.getIdTypeOrder());
                    preparedStatement.setInt(3, reservation.getIdRestaurant());
                    preparedStatement.setInt(4, reservation.getIdUser());
                    preparedStatement.setInt(5, reservation.getIdState());
                    preparedStatement.setDate(6, reservation.getDate());
                    preparedStatement.setInt(7, reservation.getIdOrder());
                    preparedStatement.executeUpdate();

                    //select order_meal of the order
                    query = "SELECT \"idMeal\" FROM \"public\".\"Meal_ordered\" WHERE \"idOrder\" = ?;";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, reservation.getIdOrder());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Map<Integer, Integer> meals = new HashMap<>();
                    while (resultSet.next()) {
                        meals.put(resultSet.getInt("idMeal"), resultSet.getInt("quantity"));
                    }

                    //select order_table of the order
                    query = "SELECT \"idTable\" FROM \"public\".\"Reserve_Table\" WHERE \"idOrder\" = ?;";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, reservation.getIdOrder());
                    resultSet = preparedStatement.executeQuery();
                    ArrayList<Integer> tables = new ArrayList<>();
                    while (resultSet.next()) {
                        tables.add(resultSet.getInt("idTable"));
                    }

                    //delete order_meal if there is any meal removed
                    if (reservation.getMeals() != null) {
                        for (Integer idMeal : meals.keySet()) {
                            if (!reservation.getMeals().containsKey(idMeal)) {
                                query = "DELETE FROM \"public\".\"Meal_ordered\" WHERE \"idMeal\" = ? AND \"idOrder\" = ?;";
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setInt(1, idMeal);
                                preparedStatement.setInt(2, reservation.getIdOrder());
                                preparedStatement.executeUpdate();
                            }
                        }
                    }
                    //delete order_table if there is any table removed
                    if(reservation.getTables() != null){
                        for(Integer idTable : tables){
                            if(!reservation.getTables().contains(idTable)){
                                query = "DELETE FROM \"public\".\"Reserve_Table\" WHERE \"idTable\" = ? AND \"idOrder\" = ?;";
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setInt(1, idTable);
                                preparedStatement.setInt(2, reservation.getIdOrder());
                                preparedStatement.executeUpdate();
                            }
                        }
                    }
                    //creation order_meal if there is any meal added
                    if(reservation.getMeals() != null){
                        for(Map.Entry<Integer, Integer> entry : reservation.getMeals().entrySet()){
                            if(!meals.containsKey(entry.getKey())){
                                query = "INSERT INTO \"public\".\"Meal_ordered\" (\"idMeal\", \"idOrder\", \"quantity\") VALUES (?,?,?);";
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setInt(1,entry.getKey());
                                preparedStatement.setInt(2,reservation.getIdOrder());
                                preparedStatement.setInt(3,entry.getValue());
                                preparedStatement.executeUpdate();
                            }
                        }
                    }
                    //creation order_table if there is any table added
                    if(reservation.getTables() != null){
                        for(Integer idTable : reservation.getTables()){
                            if(!tables.contains(idTable)){
                                query = "INSERT INTO \"public\".\"Reserve_Table\" (\"idOrder\", \"idTable\") VALUES (?,?);";
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setInt(1,reservation.getIdOrder());
                                preparedStatement.setInt(2,idTable);
                                preparedStatement.executeUpdate();
                            }
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

            } catch (SQLException e) {
                e.printStackTrace();
                throw new AccessDatabaseException();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new AccessDatabaseException();
        }
    }

    @Override
    public Boolean delete(int idReservation) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "DELETE FROM \"public\".\"Order\" WHERE \"idOrder\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idReservation);
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
    public Reservation getById(int idReservation) throws ReservationNotFoundException {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Order\" AS O LEFT JOIN \"public\".\"State_order\" AS S ON O.\"idState\" = S.\"idState\" WHERE \"idOrder\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idReservation);
                ResultSet resultSet = preparedStatement.executeQuery();

                String state_string = resultSet.getString("description").toUpperCase().replace(" ", "_");
                State state = State.valueOf(state_string);
                // If the reservation is found in the database
                if(resultSet.next()) {
                    Reservation reservation = new Reservation(
                            resultSet.getInt("idOrder"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getInt("idUser"),
                            resultSet.getDate("date"),
                            resultSet.getInt("idState"),
                            state
                    );
                    resultSet.close();
                    preparedStatement.close();
                    return reservation;
                }

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
        throw new ReservationNotFoundException();
    }

    @Override
    public List<Reservation> getAllReservations(int idRestaurant) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Order\" AS O LEFT JOIN \"public\".\"State_order\" AS S ON O.\"idState\" = S.\"idState\" WHERE \"idTypeOrder\" = 2 AND \"idRestaurant\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idRestaurant);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the reservation is found in the database
                while(resultSet.next()) {
                    String state_string = resultSet.getString("description").toUpperCase().replace(" ", "_");
                    State state = State.valueOf(state_string);
                    Reservation reservation = new Reservation(
                            resultSet.getInt("idOrder"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getInt("idUser"),
                            resultSet.getDate("date"),
                            resultSet.getInt("idState"),
                            state
                    );
                    reservations.add(reservation);
                }
                resultSet.close();
                preparedStatement.close();
                return reservations;
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
        return reservations;
    }

    @Override
    public List<Reservation> getAllReservationsOfUser(int idUser) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Order\" AS O LEFT JOIN \"public\".\"State_order\" AS S ON O.\"idState\" = S.\"idState\" WHERE \"idTypeOrder\" = 2 AND \"idUser\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idUser);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the meal is found in the database
                while(resultSet.next()) {
                    String state_string = resultSet.getString("description").toUpperCase().replace(" ", "_");
                    State state = State.valueOf(state_string);
                    Reservation reservation = new Reservation(
                            resultSet.getInt("idOrder"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getInt("idUser"),
                            resultSet.getDate("date"),
                            resultSet.getInt("idState"),
                            state
                    );
                    reservations.add(reservation);
                }
                resultSet.close();
                preparedStatement.close();
                return reservations;
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
        return reservations;
    }

    @Override
    public Boolean cancelReservation(Reservation reservation) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "UPDATE \"public\".\"Order\" SET \"idOrder\" = ?, \"idTypeOrder\" = ?, \"idRestaurant\" = ?, \"idUser\" = ?, \"idState\" = ?, \"date\" = ? WHERE \"idOrder\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, reservation.getIdOrder());
                preparedStatement.setInt(2, reservation.getIdTypeOrder());
                preparedStatement.setInt(3, reservation.getIdRestaurant());
                preparedStatement.setInt(4, reservation.getIdUser());
                preparedStatement.setInt(5, 5); // id 5 is the id of the state "canceled"
                preparedStatement.setDate(6, reservation.getDate());
                preparedStatement.setInt(7, reservation.getIdOrder());
                preparedStatement.executeUpdate();

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
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "UPDATE \"public\".\"Order\" SET \"idOrder\" = ?, \"idTypeOrder\" = ?, \"idRestaurant\" = ?, \"idUser\" = ?, \"idState\" = ?, \"date\" = ? WHERE \"idOrder\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, reservation.getIdOrder());
                preparedStatement.setInt(2, reservation.getIdTypeOrder());
                preparedStatement.setInt(3, reservation.getIdRestaurant());
                preparedStatement.setInt(4, reservation.getIdUser());
                preparedStatement.setInt(5, 2); // id 2 is the id of the state "in preparation
                preparedStatement.setDate(6, reservation.getDate());
                preparedStatement.setInt(7, reservation.getIdOrder());
                preparedStatement.executeUpdate();

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
    public String getStateLabel(int idState) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT \"description\" FROM \"public\".\"State_order\" WHERE \"idState\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idState);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the state is found in the database
                if(resultSet.next()) {
                    String label = resultSet.getString("description");
                    resultSet.close();
                    preparedStatement.close();
                    return label;
                }
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
        return null;
    }
}
