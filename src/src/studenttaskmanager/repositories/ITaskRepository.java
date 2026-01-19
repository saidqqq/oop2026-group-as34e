package studenttaskmanager.repositories;

import studenttaskmanager.entities.Task;
import java.util.List;

public interface ITaskRepository {
    Task findById(Integer id);
    List<Task> findAll();
    List<Task> findByProjectId(Integer projectId);
    Task save(Task task);
    Task update(Task task);
    void delete(Integer id);
}