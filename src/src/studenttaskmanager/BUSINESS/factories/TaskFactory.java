package studenttaskmanager.BUSINESS.factories;

import studenttaskmanager.DOMAIN.entities.Task;
import studenttaskmanager.DOMAIN.enums.TaskType;
import studenttaskmanager.DOMAIN.enums.TaskPriority;
import java.time.LocalDateTime;

public class TaskFactory {

    public static Task createTask(TaskType type, String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setTaskType(type.name());
        task.setStatus("TODO");
        task.setCreatedAt(LocalDateTime.now());

        switch (type) {
            case BUG:
                task.setPriority(TaskPriority.HIGH.name());
                task.setEstimatedHours(4);
                break;
            case FEATURE:
                task.setPriority(TaskPriority.MEDIUM.name());
                task.setEstimatedHours(8);
                break;
            case RESEARCH:
                task.setPriority(TaskPriority.LOW.name());
                task.setEstimatedHours(16);
                break;
            default:
                task.setPriority(TaskPriority.MEDIUM.name());
                task.setEstimatedHours(5);
        }

        return task;
    }

    public static Task createBugTask(String title, String description) {
        return createTask(TaskType.BUG, title, description);
    }

    public static Task createFeatureTask(String title, String description) {
        return createTask(TaskType.FEATURE, title, description);
    }

    public static Task createResearchTask(String title, String description) {
        return createTask(TaskType.RESEARCH, title, description);
    }
}