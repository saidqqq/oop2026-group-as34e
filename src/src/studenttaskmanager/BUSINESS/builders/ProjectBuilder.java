package studenttaskmanager.BUSINESS.builders;

import studenttaskmanager.DOMAIN.entities.Project;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectBuilder {
    private String title;
    private String description;
    private Integer ownerId;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime deadline;
    private List<Integer> memberIds = new ArrayList<>();

    public ProjectBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ProjectBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProjectBuilder setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public ProjectBuilder addTag(String tag) {
        this.tags.add(tag);
        return this;
    }

    public ProjectBuilder setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public ProjectBuilder addMember(Integer memberId) {
        this.memberIds.add(memberId);
        return this;
    }

    public Project build() {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Project title is required");
        }

        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        project.setOwnerId(ownerId);
        project.setTags(tags);
        project.setDeadline(deadline);
        project.setMemberIds(memberIds);
        project.setCreatedAt(LocalDateTime.now());

        return project;
    }

    // Static method to create builder
    public static ProjectBuilder builder() {
        return new ProjectBuilder();
    }
}