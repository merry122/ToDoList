package mini.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String Host="127.0.0.1";
    private static final int PORT=3306;
    private static final String DB_NAME="to_do_list";
    private static final String USERNAME="root";
    private static final String PASSWORD="";

    private static Connection connection;

    public static Connection getConnection(){

        try{
            connection= DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",Host,PORT,DB_NAME),USERNAME,PASSWORD);
        } catch(SQLException mess){
            mess.printStackTrace();
        }
        return connection;
    }


}
