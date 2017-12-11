package www.guestform.com.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static ConnectionProvider cp = new ConnectionProvider();
    private static Connection conn = null;
    private static final String DRIVER_CLASS = "org.sqlite.JDBC";
    private static final String URL = "jdbc:sqlite:src/main/resources/guestbook.db";

    private ConnectionProvider() {
        try{
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createConnection() {
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if(conn == null){
            cp.createConnection();
        }
        return conn;
    }
}
