package miniProject;
/**
 * DBconnection - Utility class for managing database connections.
 *
 * This class handles the establishment of database connections and prepares SQL statements for execution.
 * It provides methods to connect to the database, ensuring secure access to the required resources.
 *
 * Methods:
 * - `getConnection()`: Returns a Connection object to interact with the database.
 * - `prepareStatement(String query)`: Prepares a SQL statement from the given query string.
 *
 * Author: Krama Raouane
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBconnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/to_do_list"; //database name
    private static final String DB_USER = "root"; //database username
    private static final String DB_PASSWORD = ""; //database password

    // Establish connection to the database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Prepare a statement with the provided query
    public static PreparedStatement prepareStatement(String query) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                return connection.prepareStatement(query);
            } else {
                System.out.println("Connection to the database failed.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
