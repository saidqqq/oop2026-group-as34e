package studenttaskmanager.entities;

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

    public Task() {}

    public Task(String title, String description, Integer projectId) {
        this.title = title;
        this.description = description;
        this.status = "TODO";
        this.projectId = projectId;
    }


    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Integer getProjectId() { return projectId; }
    public LocalDateTime getDeadline() { return deadline; }
    public Integer getAssignedTo() { return assignedTo; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
    public void setAssignedTo(Integer assignedTo) { this.assignedTo = assignedTo; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}