package studenttaskmanager.edu.aitu.oop3.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String URL =
            "jdbc:postgresql://aws-1-us-west-1.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.xvjkgimfqdonryqfkypa";
    private static final String PASSWORD = "2tJNOafOELuFLKw5"; // ← DATABASE PASSWORD
    private DatabaseConnection() {
        // no instances
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}