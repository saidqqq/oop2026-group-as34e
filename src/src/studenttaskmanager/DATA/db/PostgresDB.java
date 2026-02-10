package studenttaskmanager.DATA.db;

import studenttaskmanager.UI.config.AppConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {

    @Override
    public Connection getConnection() throws SQLException {
        // Get configuration from Singleton
        AppConfig config = AppConfig.getInstance();

        // These methods DON'T take parameters - they RETURN values
        String url = config.getDbUrl();
        String user = config.getDbUser();
        String password = config.getDbPassword();

        System.out.println("🔗 Connecting to: " + url);

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database connection established");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found", e);
        }
    }
}