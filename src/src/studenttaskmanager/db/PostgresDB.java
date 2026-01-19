package studenttaskmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private static final String URL = "jdbc:postgresql://aws-1-us-west-1.pooler.supabase.com";
    private static final String USER = "postgres.xvjkgimfqdonryqfkypa";
    private static final String PASSWORD = "2tJNOafOELuFLKw5"; // ЗАМЕНИТЕ НА СВОЙ ПАРОЛЬ!

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found", e);
        }
    }
}