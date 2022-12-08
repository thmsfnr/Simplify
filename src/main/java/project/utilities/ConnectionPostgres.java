package project.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Simplify members on 07/12/22.
 * This class is the connection to the database
 * It is used to make the connection with the database
 * @author Simplify members
 */
public class ConnectionPostgres {

    // The connection to the database variables
    private String url;
    private String user;
    private String password;

    /**
     * Constructor of the class ConnectionPostgres
     * It is used to set the connection to the database
     */
    public ConnectionPostgres() {
        this.url = "jdbc:postgresql://postgresql-simplify.alwaysdata.net/simplify_bd";
        this.user = "simplify";
        this.password = "*mogsu52fA*";
    }


    /**
     * This method is used to get the connection to the database
     * @return the connection to the database
     */
    public Connection getConnection(){

        // Ask the driver to connect to the database
        try{
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);

            // Return the connection
            if(connection.isValid(2)){
                return connection;
            }
            else{
                // If the connection is not valid, return null
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
