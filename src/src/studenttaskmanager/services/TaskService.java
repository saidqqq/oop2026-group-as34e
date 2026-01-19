package studenttaskmanager.services;

import studenttaskmanager.entities.Task;
import studenttaskmanager.exceptions.DeadlinePassedException;
import studenttaskmanager.exceptions.InvalidStatusException;
import studenttaskmanager.exceptions.ProjectNotFoundException;
import studenttaskmanager.exceptions.TaskNotFoundException;
import studenttaskmanager.repositories.ITaskRepository;
import studenttaskmanager.exceptions.*;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description, Integer projectId)
            throws ProjectNotFoundException {
        if (projectId == null || projectId <= 0) {
            throw new ProjectNotFoundException("Project ID is required");
        }

        Task task = new Task(title, description, projectId);
        return taskRepository.save(task);
    }

    public Task changeStatus(Integer taskId, String newStatus)
            throws TaskNotFoundException, InvalidStatusException {
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found");
        }

        if (!isValidStatus(newStatus)) {
            throw new InvalidStatusException("Invalid status: " + newStatus +
                    ". Allowed: TODO, IN_PROGRESS, DONE");
        }

        task.setStatus(newStatus);
        return taskRepository.update(task);
    }

    public Task setDeadline(Integer taskId, LocalDateTime deadline)
            throws TaskNotFoundException, DeadlinePassedException {
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            throw new TaskNotFoundException("Task not found");
        }

        if (deadline.isBefore(LocalDateTime.now())) {
            throw new DeadlinePassedException("Deadline cannot be in the past");
        }

        task.setDeadline(deadline);
        return taskRepository.update(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByProject(Integer projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public Task getTaskById(Integer id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("Task not found");
        }
        return task;
    }

    private boolean isValidStatus(String status) {
        return status.equals("TODO") || status.equals("IN_PROGRESS") || status.equals("DONE");
    }
}