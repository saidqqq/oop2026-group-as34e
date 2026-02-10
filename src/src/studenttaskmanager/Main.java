package studenttaskmanager;

import studenttaskmanager.UI.config.AppConfig;
import studenttaskmanager.DATA.db.PostgresDB;
import studenttaskmanager.DATA.repositories.TaskRepository;
import studenttaskmanager.BUSINESS.services.TaskService;
import studenttaskmanager.UI.controllers.ConsoleController;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Student Task Manager - Assignment 4");
        System.out.println("   Topic 10: Student Task Management");
        System.out.println("   Features: Generics, Patterns, Lambdas");
        System.out.println("=========================================");

        try {
            // Show Singleton configuration
            AppConfig config = AppConfig.getInstance();
            System.out.println("📱 App: " + config.getAppName() + " v" + config.getAppVersion());

            // Setup database connection
            PostgresDB db = new PostgresDB();

            // Create repository with Generics
            TaskRepository taskRepository = new TaskRepository(db);

            // Create service
            TaskService taskService = new TaskService(taskRepository);

            // Create controller and start
            ConsoleController controller = new ConsoleController(taskService);
            controller.start();

        } catch (Exception e) {
            System.err.println("❌ Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("\n🔧 Troubleshooting:");
            System.out.println("1. Check config.properties file");
            System.out.println("2. Check Supabase connection");
            System.out.println("3. Run updated db.sql script");
        }
    }
}