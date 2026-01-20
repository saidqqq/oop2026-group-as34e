package studenttaskmanager;

import java.sql.*;

public class TestDelete {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://aws-1-us-west-1.pooler.supabase.com:5432/postgres";
        String user = "postgres.xvjkgimfqdonryqfkypa";
        String password = "2tJNOafOELuFLKw5";

        System.out.println("🧪 Testing DELETE operation on Supabase...");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // 1. Сначала создадим тестовую задачу
            System.out.println("1. Creating test task...");
            String insertSQL = "INSERT INTO tasks (title, description, project_id) VALUES (?, ?, ?) RETURNING id";
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setString(1, "Test Task for DELETE");
            insertStmt.setString(2, "This task will be deleted");
            insertStmt.setInt(3, 1);
            ResultSet rs = insertStmt.executeQuery();
            rs.next();
            int testId = rs.getInt("id");
            System.out.println("   ✅ Created task with ID: " + testId);

            // 2. Проверим, что задача существует
            System.out.println("2. Verifying task exists...");
            String checkSQL = "SELECT COUNT(*) FROM tasks WHERE id = " + testId;
            Statement checkStmt = conn.createStatement();
            ResultSet checkRs = checkStmt.executeQuery(checkSQL);
            checkRs.next();
            System.out.println("   ✅ Task exists: " + (checkRs.getInt(1) > 0));

            // 3. Попробуем удалить
            System.out.println("3. Attempting to delete task " + testId + "...");
            String deleteSQL = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, testId);
            int rowsDeleted = deleteStmt.executeUpdate();
            System.out.println("   ✅ Rows deleted: " + rowsDeleted);

            // 4. Проверим, что задача удалена
            System.out.println("4. Verifying task is deleted...");
            checkRs = checkStmt.executeQuery(checkSQL);
            checkRs.next();
            System.out.println("   ✅ Task still exists: " + (checkRs.getInt(1) > 0));

            if (rowsDeleted > 0) {
                System.out.println("\n🎉 DELETE operation works correctly!");
            } else {
                System.out.println("\n❌ DELETE operation failed - 0 rows affected");
                System.out.println("   This is likely a Row Level Security issue.");
                System.out.println("   Run in Supabase: ALTER TABLE tasks DISABLE ROW LEVEL SECURITY;");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
            if (e.getMessage().contains("permission denied")) {
                System.out.println("\n🔒 ROW LEVEL SECURITY ERROR!");
                System.out.println("Steps to fix:");
                System.out.println("1. Go to Supabase SQL Editor");
                System.out.println("2. Run: ALTER TABLE tasks DISABLE ROW LEVEL SECURITY;");
                System.out.println("3. Run: ALTER TABLE projects DISABLE ROW LEVEL SECURITY;");
                System.out.println("4. Run: ALTER TABLE comments DISABLE ROW LEVEL SECURITY;");
            }
        }
    }
}