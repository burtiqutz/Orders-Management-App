package Connection;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Singleton factory class to manage database connections.
 * Provides methods to create and close JDBC resources.
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/order_management";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static ConnectionFactory singleInstance = new ConnectionFactory();
    /**
     * Private constructor loads the JDBC driver.
     */
    private ConnectionFactory() {
        try{
            Class.forName(DRIVER);
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance of ConnectionFactory.
     * @return singleton instance
     */
    public static ConnectionFactory getInstance() {
        return singleInstance;
    }

    /**
     * Creates and returns a new database connection.
     * @return new {@link Connection} or null if failed
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error creating connection", e);
        }
        return connection;
    }

    /**
     * Returns a new database connection.
     * @return new {@link Connection} or null if failed
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes the given {@link Connection}.
     * @param connection connection to close
     */
    public static void close (Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Failed to close connection", e);
            }
        }
    }

    /**
     * Closes the given {@link Statement}.
     * @param statement statement to close
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Failed to close statement", e);
            }
        }
    }

    /**
     * Closes the given {@link ResultSet}.
     * @param resultSet result set to close
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Failed to close resultSet", e);
            }
        }
    }

}
