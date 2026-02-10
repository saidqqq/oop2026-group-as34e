package studenttaskmanager.DOMAIN.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private Integer id;
    private String title;
    private String description;
    private Integer ownerId;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime deadline;
    private List<Integer> memberIds = new ArrayList<>();
    private LocalDateTime createdAt;

    // Getters and setters for all fields
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public List<Integer> getMemberIds() { return memberIds; }
    public void setMemberIds(List<Integer> memberIds) { this.memberIds = memberIds; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}