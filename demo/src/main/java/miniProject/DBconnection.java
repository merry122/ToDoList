package miniProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *@author raouane krama
 */
public class DBconnection {
    private static final String Host = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String DB_NAME = "to_do_list";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            // Add useSSL=false to the URL to disable SSL
            String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false", Host, PORT, DB_NAME);
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException mess) {
            mess.printStackTrace();
        }
        return connection;
    }
}


