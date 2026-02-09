package studenttaskmanager.UI.controllers;

import studenttaskmanager.BUSINESS.services.TaskService;
import studenttaskmanager.DOMAIN.entities.Task;
import studenttaskmanager.BUSINESS.builders.ProjectBuilder;
import studenttaskmanager.BUSINESS.factories.TaskFactory;
import studenttaskmanager.DOMAIN.enums.TaskType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        System.out.println("Student Task Management System v2.0");
        System.out.println("======================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1": createSimpleTask(); break;
                    case "2": createTaskWithFactory(); break;
                    case "3": createProjectWithBuilder(); break;
                    case "4": listAllTasks(); break;
                    case "5": filterTasksByLambda(); break;
                    case "6": showHighPriorityTasks(); break;
                    case "7": showTasksDueSoon(); break;
                    case "8": showTasksByType(); break;
                    case "9": changeTaskStatus(); break;
                    case "10": setTaskDeadline(); break;
                    case "11": deleteTask(); break;
                    case "0": running = false; break;
                    default: System.out.println("❌ Invalid option");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
            System.out.println();
        }
        System.out.println("👋 Goodbye!");
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("\nMAIN MENU - ASSIGNMENT 4");
        System.out.println("1. Create simple task (A3 style)");
        System.out.println("2.Create task using Factory pattern");
        System.out.println("3.Create project using Builder pattern");
        System.out.println("4.List all tasks");
        System.out.println("5.Filter tasks using Lambda");
        System.out.println("6. Show high priority tasks (Lambda)");
        System.out.println("7. Show tasks due soon (Lambda)");
        System.out.println("8. Show tasks by type (Lambda)");
        System.out.println("9.Change task status");
        System.out.println("10.Set task deadline");
        System.out.println("11. Delete task");
        System.out.println("0.  Exit");
        System.out.print("👉 Choose an option: ");
    }

    // Method from Assignment 3
    private void createSimpleTask() throws Exception {
        System.out.println("\n📝 CREATE SIMPLE TASK");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter project ID: ");
        Integer projectId = Integer.parseInt(scanner.nextLine());

        Task task = taskService.createTask(title, description, projectId);
        System.out.println("✅ Task created with ID: " + task.getId());
    }

    private void createTaskWithFactory() {
        System.out.println("\nCREATE TASK USING FACTORY PATTERN");
        System.out.println("Available task types: BUG, FEATURE, RESEARCH");
        System.out.print("Enter task type: ");
        String type = scanner.nextLine().toUpperCase();

        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter project ID: ");
        Integer projectId = Integer.parseInt(scanner.nextLine());

        try {
            TaskType taskType = TaskType.valueOf(type);
            Task task = TaskFactory.createTask(taskType, title, description);
            task.setProjectId(projectId);

            // In real code, you would save this to database
            // For demo, just show the created task
            System.out.println("✅ Task created using Factory pattern!");
            System.out.println("   Type: " + task.getTaskType());
            System.out.println("   Priority: " + task.getPriority());
            System.out.println("   Estimated hours: " + task.getEstimatedHours());

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid task type. Use: BUG, FEATURE, RESEARCH");
        }
    }

    private void createProjectWithBuilder() {
        System.out.println("\n🛠️ CREATE PROJECT USING BUILDER PATTERN");

        System.out.print("Enter project title: ");
        String title = scanner.nextLine();

        System.out.print("Enter project description: ");
        String description = scanner.nextLine();

        System.out.print("Enter owner ID: ");
        Integer ownerId = Integer.parseInt(scanner.nextLine());

        // Create builder
        ProjectBuilder builder = ProjectBuilder.builder()
                .setTitle(title)
                .setDescription(description)
                .setOwnerId(ownerId);

        System.out.println("Add tags (enter 'done' when finished):");
        while (true) {
            System.out.print("Tag: ");
            String tag = scanner.nextLine();
            if (tag.equalsIgnoreCase("done")) break;
            builder.addTag(tag);
        }

        System.out.println("Add team members (enter '0' when finished):");
        while (true) {
            System.out.print("Member ID: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            if (memberId == 0) break;
            builder.addMember(memberId);
        }

        try {
            // Build the project
            var project = builder.build();
            System.out.println("✅ Project created using Builder pattern!");
            System.out.println("   Title: " + project.getTitle());
            System.out.println("   Tags: " + project.getTags());
            System.out.println("   Members: " + project.getMemberIds().size());

            // Here you would save to database
            // projectRepository.save(project);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // Method from Assignment 3
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
                System.out.println("Type: " + task.getTaskType());
                System.out.println("Priority: " + task.getPriority());
            }
        }
    }

    private void filterTasksByLambda() {
        System.out.println("\n🔍 FILTER TASKS USING LAMBDA");
        System.out.println("Filter by:");
        System.out.println("1. Status (TODO, IN_PROGRESS, DONE)");
        System.out.println("2. Priority (HIGH, MEDIUM, LOW)");
        System.out.println("3. Task type");
        System.out.print("Choose filter type: ");

        String filterChoice = scanner.nextLine();
        System.out.print("Enter filter value: ");
        String filterValue = scanner.nextLine();
        List<Task> filteredTasks;

        switch (filterChoice) {
            case "1":
                filteredTasks = taskService.filterTasks(
                        task -> task.getStatus().equalsIgnoreCase(filterValue)
                );
                break;
            case "2":
                filteredTasks = taskService.filterTasks(
                        task -> task.getPriority() != null &&
                                task.getPriority().equalsIgnoreCase(filterValue)
                );
                break;
            case "3":
                filteredTasks = taskService.filterTasks(
                        task -> task.getTaskType() != null &&
                                task.getTaskType().equalsIgnoreCase(filterValue)
                );
                break;
            default:
                System.out.println("❌ Invalid filter type");
                return;
        }

        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found matching the filter");
        } else {
            System.out.println("Found " + filteredTasks.size() + " task(s):");
            for (Task task : filteredTasks) {
                System.out.println("  • " + task.getTitle() +
                        " [" + task.getStatus() + ", " + task.getPriority() + "]");
            }
        }
    }

    private void showHighPriorityTasks() {
        System.out.println("\n⚠️ HIGH PRIORITY TASKS (Using Lambda)");
        List<Task> highPriorityTasks = taskService.getHighPriorityTasks();

        if (highPriorityTasks.isEmpty()) {
            System.out.println("No high priority tasks found");
        } else {
            System.out.println("High priority tasks:");
            for (Task task : highPriorityTasks) {
                System.out.println("  • " + task.getTitle() +
                        " - Due: " + (task.getDeadline() != null ?
                        task.getDeadline().format(formatter) : "No deadline"));
            }
        }
    }

    private void showTasksDueSoon() {
        System.out.println("\n⏰ TASKS DUE SOON (Within 7 days, Using Lambda)");
        List<Task> dueSoonTasks = taskService.getTasksDueSoon();

        if (dueSoonTasks.isEmpty()) {
            System.out.println("No tasks due soon");
        } else {
            System.out.println("Tasks due within 7 days:");
            for (Task task : dueSoonTasks) {
                System.out.println("  • " + task.getTitle() +
                        " - Due: " + task.getDeadline().format(formatter));
            }
        }
    }

    private void showTasksByType() {
        System.out.println("\n🏷️ SHOW TASKS BY TYPE (Using Lambda)");
        System.out.print("Enter task type (BUG/FEATURE/RESEARCH): ");
        String type = scanner.nextLine();

        List<Task> tasks = taskService.getTasksByType(type);

        if (tasks.isEmpty()) {
            System.out.println("No tasks found of type: " + type);
        } else {
            System.out.println("Tasks of type " + type + ":");
            for (Task task : tasks) {
                System.out.println("  • " + task.getTitle() +
                        " [" + task.getStatus() + "]");
            }
        }
    }

    // Method from Assignment 3
    private void changeTaskStatus() throws Exception {
        System.out.print("\nEnter task ID: ");
        Integer taskId = Integer.parseInt(scanner.nextLine());

        System.out.println("Available statuses: TODO, IN_PROGRESS, DONE");
        System.out.print("Enter new status: ");
        String newStatus = scanner.nextLine().toUpperCase();

        Task task = taskService.changeStatus(taskId, newStatus);
        System.out.println("✅ Status updated to: " + task.getStatus());
    }

    // Method from Assignment 3
    private void setTaskDeadline() throws Exception {
        System.out.print("\nEnter task ID: ");
        Integer taskId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter deadline (yyyy-MM-dd HH:mm): ");
        String deadlineStr = scanner.nextLine();
        LocalDateTime deadline = LocalDateTime.parse(deadlineStr, formatter);
        Task task = taskService.setDeadline(taskId, deadline);
        System.out.println("✅ Deadline set: " + task.getDeadline().format(formatter));
    }

    // Method from Assignment 3
    private void deleteTask() throws Exception {
        System.out.print("\nEnter task ID to delete: ");
        Integer taskId = Integer.parseInt(scanner.nextLine());

        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            taskService.deleteTask(taskId);
            System.out.println("✅ Task deleted!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}