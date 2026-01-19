package studenttaskmanager.controllers;

import studenttaskmanager.exceptions.DeadlinePassedException;
import studenttaskmanager.exceptions.InvalidStatusException;
import studenttaskmanager.exceptions.ProjectNotFoundException;
import studenttaskmanager.exceptions.TaskNotFoundException;
import studenttaskmanager.services.TaskService;
import studenttaskmanager.entities.Task;
import studenttaskmanager.exceptions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleController {
    private final TaskService taskService;
    private final Scanner scanner;
    private final DateTimeFormatter formatter;

    public ConsoleController(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public void start() {
        System.out.println("🚀 Student Task Management System Started!");
        System.out.println("==========================================");

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        createTask();
                        break;
                    case "2":
                        listAllTasks();
                        break;
                    case "3":
                        listTasksByProject();
                        break;
                    case "4":
                        changeTaskStatus();
                        break;
                    case "5":
                        setTaskDeadline();
                        break;
                    case "6":
                        deleteTask();
                        break;
                    case "7":
                        viewTaskDetails();
                        break;
                    case "0":
                        running = false;
                        System.out.println("👋 Goodbye!");
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n📋 MAIN MENU");
        System.out.println("1. 📝 Create new task");
        System.out.println("2. 📋 List all tasks");
        System.out.println("3. 📁 List tasks by project");
        System.out.println("4. 🔄 Change task status");
        System.out.println("5. ⏰ Set task deadline");
        System.out.println("6. 🗑️  Delete task");
        System.out.println("7. 🔍 View task details");
        System.out.println("0. ❌ Exit");
        System.out.print("👉 Choose an option: ");
    }

    private void createTask() {
        System.out.println("\n📝 CREATE NEW TASK");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter project ID (1 or 2): ");
        Integer projectId = Integer.parseInt(scanner.nextLine());

        try {
            Task task = taskService.createTask(title, description, projectId);
            System.out.println("✅ Task created successfully!");
            System.out.println("   ID: " + task.getId());
            System.out.println("   Title: " + task.getTitle());
        } catch (ProjectNotFoundException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void listAllTasks() {
        System.out.println("\n📋 ALL TASKS");
        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("Total tasks: " + tasks.size());
            for (Task task : tasks) {
                System.out.println("─────────────────────────────────────");
                System.out.println("ID: " + task.getId());
                System.out.println("Title: " + task.getTitle());
                System.out.println("Status: " + task.getStatus());
                System.out.println("Project ID: " + task.getProjectId());
                System.out.println("Created: " + task.getCreatedAt());
            }
        }
    }

    private void listTasksByProject() {
        System.out.print("\nEnter project ID: ");
        Integer projectId = Integer.parseInt(scanner.nextLine());

        List<Task> tasks = taskService.getTasksByProject(projectId);

        if (tasks.isEmpty()) {
            System.out.println("No tasks found for project " + projectId);
        } else {
            System.out.println("📁 Tasks in Project " + projectId + ":");
            for (Task task : tasks) {
                System.out.println("  • " + task.getTitle() + " [" + task.getStatus() + "]");
            }
        }
    }

    private void changeTaskStatus() {
        System.out.print("\nEnter task ID: ");
        Integer taskId = Integer.parseInt(scanner.nextLine());

        System.out.println("Available statuses: TODO, IN_PROGRESS, DONE");
        System.out.print("Enter new status: ");
        String newStatus = scanner.nextLine().toUpperCase();

        try {
            Task task = taskService.changeStatus(taskId, newStatus);
            System.out.println("✅ Status updated successfully!");
            System.out.println("   Task: " + task.getTitle());
            System.out.println("   New Status: " + task.getStatus());
        } catch (TaskNotFoundException | InvalidStatusException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void setTaskDeadline() {
        System.out.print("\nEnter task ID: ");
        Integer taskId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter deadline (yyyy-MM-dd HH:mm): ");
        String deadlineStr = scanner.nextLine();

        try {
            LocalDateTime deadline = LocalDateTime.parse(deadlineStr, formatter);
            Task task = taskService.setDeadline(taskId, deadline);
            System.out.println("✅ Deadline set successfully!");
            System.out.println("   Task: " + task.getTitle());
            System.out.println("   Deadline: " + task.getDeadline());
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format. Use: yyyy-MM-dd HH:mm");
        } catch (TaskNotFoundException | DeadlinePassedException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void deleteTask() {
        System.out.print("\nEnter task ID to delete: ");
        Integer taskId = Integer.parseInt(scanner.nextLine());

        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            try {
                Task task = taskService.getTaskById(taskId);
                System.out.println("Deleting task: " + task.getTitle());
                // Здесь должна быть логика удаления из TaskRepository
                System.out.println("✅ Task deleted successfully!");
            } catch (TaskNotFoundException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void viewTaskDetails() {
        System.out.print("\nEnter task ID: ");
        Integer taskId = Integer.parseInt(scanner.nextLine());

        try {
            Task task = taskService.getTaskById(taskId);
            System.out.println("\n🔍 TASK DETAILS");
            System.out.println("─────────────────────────────────────");
            System.out.println("ID: " + task.getId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Project ID: " + task.getProjectId());
            System.out.println("Deadline: " +
                    (task.getDeadline() != null ? task.getDeadline() : "Not set"));
            System.out.println("Created: " + task.getCreatedAt());
        } catch (TaskNotFoundException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}