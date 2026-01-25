package studenttaskmanager.repositories;

import studenttaskmanager.db.IDB;
import studenttaskmanager.entities.Task;
import studenttaskmanager.exceptions.TaskNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements ITaskRepository {
    private final IDB db;

    public TaskRepository(IDB db) {
        this.db = db;
    }

    @Override
    public Task findById(Integer id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToTask(rs);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error finding task: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks ORDER BY created_at DESC";

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(mapRowToTask(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tasks: " + e.getMessage());
        }
        return tasks;
    }

    @Override
    public List<Task> findByProjectId(Integer projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE project_id = ? ORDER BY created_at DESC";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(mapRowToTask(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tasks by project: " + e.getMessage());
        }
        return tasks;
    }

    @Override
    public Task save(Task task) {
        String sql = "INSERT INTO tasks (title, description, status, project_id, deadline, assigned_to) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id, created_at";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getProjectId());

            if (task.getDeadline() != null) {
                stmt.setTimestamp(5, Timestamp.valueOf(task.getDeadline()));
            } else {
                stmt.setNull(5, Types.TIMESTAMP);
            }

            if (task.getAssignedTo() != null) {
                stmt.setInt(6, task.getAssignedTo());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                task.setId(rs.getInt("id"));
                task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            return task;
        } catch (SQLException e) {
            System.err.println("Error saving task: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Task update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, status = ?, " +
                "deadline = ?, assigned_to = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());

            if (task.getDeadline() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(task.getDeadline()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }

            if (task.getAssignedTo() != null) {
                stmt.setInt(5, task.getAssignedTo());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, task.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Task not found");
            }
            return task;
        } catch (SQLException e) {
            System.err.println("Error updating task: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(Integer id) throws TaskNotFoundException {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new TaskNotFoundException("Task with ID " + id + " not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete task", e);
        }
    }


    private Task mapRowToTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setStatus(rs.getString("status"));
        task.setProjectId(rs.getInt("project_id"));

        Timestamp deadline = rs.getTimestamp("deadline");
        if (deadline != null) {
            task.setDeadline(deadline.toLocalDateTime());
        }

        task.setAssignedTo(rs.getObject("assigned_to") != null ? rs.getInt("assigned_to") : null);
        task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return task;
    }
}