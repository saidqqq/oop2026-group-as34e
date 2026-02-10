package studenttaskmanager.DOMAIN.entities;

import java.time.LocalDateTime;

public class Comment {
    private Integer id;
    private String text;
    private Integer taskId;
    private Integer userId;
    private LocalDateTime createdAt;

    public Comment() {}

    public Comment(String text, Integer taskId, Integer userId) {
        this.text = text;
        this.taskId = taskId;
        this.userId = userId;
    }


    public Integer getId() { return id; }
    public String getText() { return text; }
    public Integer getTaskId() { return taskId; }
    public Integer getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Integer id) { this.id = id; }
    public void setText(String text) { this.text = text; }
    public void setTaskId(Integer taskId) { this.taskId = taskId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}