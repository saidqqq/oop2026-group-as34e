package studenttaskmanager.DOMAIN.entities;

import java.time.LocalDateTime;

public class Task {
    private Integer id;
    private String title;
    private String description;
    private String status;
    private Integer projectId;
    private LocalDateTime deadline;
    private Integer assignedTo;
    private LocalDateTime createdAt;
    private String taskType;
    private String priority;
    private Integer estimatedHours;

    public Task() {}

    public Task(String title, String description, Integer projectId) {
        this.title = title;
        this.description = description;
        this.status = "TODO";
        this.projectId = projectId;
    }

    // Getters and setters for ALL fields
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public Integer getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Integer assignedTo) { this.assignedTo = assignedTo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getTaskType() { return taskType; }
    public void setTaskType(String taskType) { this.taskType = taskType; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public Integer getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
}