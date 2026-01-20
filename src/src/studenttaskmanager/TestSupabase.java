package studenttaskmanager;

import studenttaskmanager.db.PostgresDB;
import java.sql.Connection;
import java.sql.Statement;

public class TestSupabase {
    public static void main(String[] args) {
        try {
            PostgresDB db = new PostgresDB();
            Connection conn = db.getConnection();
            System.out.println("Connected to Supabase successfully!");

            // Проверяем таблицы
            Statement stmt = conn.createStatement();
            var rs = stmt.executeQuery("SELECT COUNT(*) FROM tasks");
            if (rs.next()) {
                System.out.println("Tables exist. Task count: " + rs.getInt(1));
            }

            conn.close();
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}