package userHandling;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by Roubal Saker
 * 2020-04-09
 */

public class ConnectionMaker {

    private static java.sql.Connection myConn = null;
    private static PreparedStatement myStmt = null;
    private static ResultSet myRs = null;

    static {
        try {
            // In the url change the "user" and "password" with your own server name and password.
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/timeline?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "0936954");

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
    public static void setAutoCommit(boolean state) throws SQLException {
        myConn.setAutoCommit(state);

    }

    public static void commit() throws SQLException {
        myConn.commit();
    }

    public static void rollback() throws SQLException {
        myConn.rollback();
    }

    public static PreparedStatement prepareStatement(String statement) throws SQLException {
        myStmt = myConn.prepareStatement(statement);
        return myStmt;
    }

    public static ResultSet executeQuery() throws SQLException {
        return myStmt.executeQuery();
    }

    public static void executeUpdate() throws SQLException {
        myStmt.executeUpdate();
    }
}
