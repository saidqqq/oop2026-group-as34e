package studenttaskmanager.BUSINESS.services;

import studenttaskmanager.DOMAIN.entities.Task;
import studenttaskmanager.DATA.repositories.CrudRepository;
import studenttaskmanager.DOMAIN.exceptions.TaskNotFoundException;
import studenttaskmanager.DOMAIN.exceptions.InvalidStatusException;
import studenttaskmanager.DOMAIN.exceptions.ProjectNotFoundException;
import studenttaskmanager.DOMAIN.exceptions.DeadlinePassedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskService {
    private final CrudRepository<Task, Integer> taskRepository;

    public TaskService(CrudRepository<Task, Integer> taskRepository) {
        this.taskRepository = taskRepository;
    }

    // From Assignment 3
    public Task createTask(String title, String description, Integer projectId)
            throws ProjectNotFoundException {
        if (projectId == null || projectId <= 0) {
            throw new ProjectNotFoundException("Invalid project ID");
        }

        Task task = new Task(title, description, projectId);
        return taskRepository.save(task);
    }

    public Task changeStatus(Integer taskId, String newStatus)
            throws TaskNotFoundException, InvalidStatusException {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found");
        }

        Task task = taskOpt.get();

        if (!isValidStatus(newStatus)) {
            throw new InvalidStatusException("Invalid status: " + newStatus);
        }

        task.setStatus(newStatus);
        return taskRepository.update(task);
    }

    public Task setDeadline(Integer taskId, LocalDateTime deadline)
            throws TaskNotFoundException, DeadlinePassedException {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found");
        }

        if (deadline.isBefore(LocalDateTime.now())) {
            throw new DeadlinePassedException("Deadline cannot be in the past");
        }

        Task task = taskOpt.get();
        task.setDeadline(deadline);
        return taskRepository.update(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) throws TaskNotFoundException {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Task with ID " + id + " not found");
        }
        return taskOpt.get();
    }

    public void deleteTask(Integer id) throws TaskNotFoundException {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Task with ID " + id + " not found");
        }
        taskRepository.delete(id);
    }

    // Assignment 4 - Lambdas
    public List<Task> filterTasks(Predicate<Task> filterCondition) {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .filter(filterCondition)
                .collect(Collectors.toList());
    }

    public List<Task> getHighPriorityTasks() {
        return filterTasks(task ->
                task.getPriority() != null &&
                        task.getPriority().equalsIgnoreCase("HIGH")
        );
    }

    public List<Task> getTasksDueSoon() {
        LocalDateTime oneWeekFromNow = LocalDateTime.now().plusDays(7);
        return filterTasks(task ->
                task.getDeadline() != null &&
                        task.getDeadline().isBefore(oneWeekFromNow) &&
                        !task.getStatus().equals("DONE")
        );
    }

    public List<Task> getTasksByType(String taskType) {
        return filterTasks(task ->
                task.getTaskType() != null &&
                        task.getTaskType().equalsIgnoreCase(taskType)
        );
    }

    private boolean isValidStatus(String status) {
        return status.equals("TODO") || status.equals("IN_PROGRESS") || status.equals("DONE");
    }
}