package project.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgres {

    private String url;
    private String user;
    private String password;

    public ConnectionPostgres() {
        this.url = "jdbc:postgresql://postgresql-simplify.alwaysdata.net/simplify_bd";
        this.user = "simplify";
        this.password = "*mogsu52fA*";
    }


    public Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
            if(connection.isValid(2)){
                return connection;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
