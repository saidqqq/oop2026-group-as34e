package studenttaskmanager.entities;

import java.time.LocalDateTime;

public class Project {
    private Integer id;
    private String title;
    private String description;
    private Integer ownerId;
    private LocalDateTime createdAt;

    public Project() {}

    public Project(String title, String description, Integer ownerId) {
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
    }


    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getOwnerId() { return ownerId; }
    public LocalDateTime getCreatedAt() { return createdAt; }


    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}