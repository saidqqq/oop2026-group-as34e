package studenttaskmanager;

import studenttaskmanager.db.PostgresDB;
import studenttaskmanager.repositories.TaskRepository;
import studenttaskmanager.services.TaskService;
import studenttaskmanager.controllers.ConsoleController;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   Student Task Management System v1.0");
        System.out.println("   Assignment 3 - Project Milestone 1");
        System.out.println("   Topic 10: Student Task Manager");
        System.out.println("==========================================");

        try {
            // 1. Подключение к базе данных
            PostgresDB db = new PostgresDB();

            // 2. Создаем репозиторий
            TaskRepository taskRepository = new TaskRepository(db);

            // 3. Создаем сервис
            TaskService taskService = new TaskService(taskRepository);

            // 4. Создаем контроллер и запускаем
            ConsoleController controller = new ConsoleController(taskService);
            controller.start();

        } catch (Exception e) {
            System.err.println("  Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("\n⚠   TROUBLESHOOTING:");
            System.out.println("1. Check if PostgreSQL is running");
            System.out.println("2. Check database connection settings in PostgresDB.java");
            System.out.println("3. Run db.sql script to create tables");
        }
    }
}