package project.persistence.product.postgres;

import project.business.models.Meal;
import project.exceptions.AccessDatabaseException;
import project.exceptions.MealNotFoundException;
import project.persistence.factory.PostGresDAOFactory;
import project.persistence.product.abstr.MealDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostGresMealDAO extends MealDAO {

    /**
     * This method is used to return the Meal with it's id
     * @param idMeal the id of the meal
     * @return a Meal object
     * @throws MealNotFoundException if the meal is not found
     */
    @Override
    public Meal getById(int idMeal) throws MealNotFoundException {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Meal\" WHERE \"idMeal\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idMeal);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the meal is found in the database
                if(resultSet.next()) {
                    Meal meal = new Meal(
                            resultSet.getInt("idMeal"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getString("description"),
                            resultSet.getString("title"),
                            resultSet.getDouble("price")
                    );
                    resultSet.close();
                    preparedStatement.close();
                    return meal;
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
        throw new MealNotFoundException();
    }

    /**
     * This method is used to create a Meal
     * @param meal the meal model with the information of the new meal
     * @return True if the meal is created, false if not
     */
    @Override
    public boolean create(Meal meal) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "INSERT INTO \"public\".\"Meal\" (\"idRestaurant\",\"description\",\"title\",\"price\") VALUES (?,?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, meal.getIdRestaurant());
                preparedStatement.setString(2, meal.getDescription());
                preparedStatement.setString(3, meal.getTitle());
                preparedStatement.setDouble(4, meal.getPrice());
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

    /**
     * This method is delete to update a Meal
     * @param idMeal the id of the meal to delete
     * @return true if the meal is deleted, false if not
     */
    @Override
    public boolean delete(int idMeal) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "DELETE FROM \"public\".\"Meal\" WHERE \"idMeal\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idMeal);
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

    /**
     * This method is used to update a meal
     * @param meal the meal model with the information of the meal to update
     * @return True if the meal is updated, false otherwise
     */
    @Override
    public boolean update(Meal meal) {
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "UPDATE \"public\".\"Meal\" SET \"idRestaurant\" = ?, \"description\" = ?, \"title\" = ?, \"price\" = ? WHERE \"idMeal\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, meal.getIdRestaurant());
                preparedStatement.setString(2, meal.getDescription());
                preparedStatement.setString(3, meal.getTitle());
                preparedStatement.setDouble(4, meal.getPrice());
                preparedStatement.setInt(5, meal.getIdMeal());
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

    /**
     * THis method is used to get all the meals of a restaurant
     * @param idRestaurant the id of the restaurant
     * @return a list of meals
     */
    @Override
    public List<Meal> getAllMeal(int idRestaurant) {
        ArrayList<Meal> meals = new ArrayList<>();
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Meal\" WHERE \"idRestaurant\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idRestaurant);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the meal is found in the database
                while(resultSet.next()) {
                    Meal meal = new Meal(
                            resultSet.getInt("idMeal"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getString("description"),
                            resultSet.getString("title"),
                            resultSet.getDouble("price")
                    );
                    meals.add(meal);
                }
                resultSet.close();
                preparedStatement.close();
                return meals;
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
        return meals;
    }


    /**
     * This method is used to get all the meals of a delivery
     * @param idDelivery the id of the delivery
     * @return a List of Meal
     * @throws AccessDatabaseException
     */
    @Override
    public List<Meal> getAllMealOfDelivery(int idDelivery) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if (connection == null) {
            throw new AccessDatabaseException();
        } else {
            try {
                String query = "SELECT * FROM \"public\".\"Meal\" AS M1 LEFT JOIN \"public\".\"Meal_ordered\" AS M2 ON M1.\"idMeal\" = M2.\"idMeal\" WHERE M2.\"idDelivery\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idDelivery);
                ResultSet resultSet = preparedStatement.executeQuery();

                ArrayList<Meal> meals = new ArrayList<>();
                while (resultSet.next()) {
                    Meal meal = new Meal(
                            resultSet.getInt("idMeal"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getString("description"),
                            resultSet.getString("title"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("quantity")
                    );
                    meals.add(meal);
                }
                resultSet.close();
                preparedStatement.close();
                return meals;
            } catch (SQLException e) {
                throw new AccessDatabaseException();
            }
        }
    }


    /**
     * This method is used to gets all the meal of a reservation
     * @param idReservation the id of the reservation
     * @return a list of Meal
     */
    @Override
    public List<Meal> getMealsOfReservation(int idReservation) {
        ArrayList<Meal> meals = new ArrayList<>();
        // Get the connection to the database
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();
        // If the connection works
        if(connection != null) {
            // Create the query
            try {
                String query = "SELECT * FROM \"public\".\"Meal\" WHERE \"idMeal\" IN (SELECT \"idMeal\" FROM \"public\".\"Meal_ordered\" WHERE \"idOrder\" = ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idReservation);
                ResultSet resultSet = preparedStatement.executeQuery();

                // If the meal is found in the database

                while(resultSet.next()) {
                    Meal meal = new Meal(
                            resultSet.getInt("idMeal"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getString("description"),
                            resultSet.getString("title"),
                            resultSet.getDouble("price")
                    );
                    meals.add(meal);
                }
                resultSet.close();
                preparedStatement.close();
                return meals;
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
        return meals;

    }

    @Override
    public List<Meal> getAllMealOfRestaurant(int idRestaurant) throws AccessDatabaseException {
        Connection connection = PostGresDAOFactory.connectionPostgres.getConnection();

        if(connection == null){
            throw new AccessDatabaseException();
        }
        else{
            try{
                String query = "SELECT * FROM \"public\".\"Meal\" WHERE \"idRestaurant\" = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idRestaurant);
                ResultSet resultSet = preparedStatement.executeQuery();

                ArrayList<Meal> meals = new ArrayList<>();

                while(resultSet.next()){
                    Meal meal = new Meal(
                            resultSet.getInt("idMeal"),
                            resultSet.getInt("idRestaurant"),
                            resultSet.getString("description"),
                            resultSet.getString("title"),
                            resultSet.getDouble("price")
                    );
                    meals.add(meal);
                }

                resultSet.close();
                preparedStatement.close();
                return meals;

            } catch(SQLException e){
                throw new AccessDatabaseException();
            }
        }
    }
}
