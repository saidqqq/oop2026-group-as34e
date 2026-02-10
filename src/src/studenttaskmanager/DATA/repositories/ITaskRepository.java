package studenttaskmanager.DATA.repositories;

import studenttaskmanager.DOMAIN.entities.Task;
import studenttaskmanager.DOMAIN.exceptions.TaskNotFoundException; // ← ДОБАВЬ ЭТО
import java.util.List;

public interface ITaskRepository {
    Task findById(Integer id);
    List<Task> findAll();
    List<Task> findByProjectId(Integer projectId);
    Task save(Task task);
    Task update(Task task);
    void delete(Integer taskId) throws TaskNotFoundException; // ← добавь throws
}
