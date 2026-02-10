package studenttaskmanager.DATA.repositories;

import studenttaskmanager.DATA.db.IDB;
import studenttaskmanager.DOMAIN.entities.Task;
import studenttaskmanager.DOMAIN.exceptions.TaskNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository implements CrudRepository<Task, Integer> {
    private final IDB db;

    public TaskRepository(IDB db) {
        this.db = db;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToTask(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.err.println("Error finding task: " + e.getMessage());
            return Optional.empty();
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
    public List<Task> findByCriteria(String field, Object value) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE " + field + " = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (value instanceof String) {
                stmt.setString(1, (String) value);
            } else if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(mapRowToTask(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding tasks by criteria: " + e.getMessage());
        }
        return tasks;
    }

    @Override
    public Task save(Task task) {
        String sql = "INSERT INTO tasks (title, description, status, project_id, " +
                "task_type, priority, estimated_hours) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id, created_at";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getProjectId());
            stmt.setString(5, task.getTaskType());
            stmt.setString(6, task.getPriority());

            if (task.getEstimatedHours() != null) {
                stmt.setInt(7, task.getEstimatedHours());
            } else {
                stmt.setNull(7, Types.INTEGER);
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
                "deadline = ?, assigned_to = ?, task_type = ?, priority = ?, " +
                "estimated_hours = ? WHERE id = ?";
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

            stmt.setString(6, task.getTaskType());
            stmt.setString(7, task.getPriority());

            if (task.getEstimatedHours() != null) {
                stmt.setInt(8, task.getEstimatedHours());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            stmt.setInt(9, task.getId());

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
    public void delete(Integer id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new TaskNotFoundException("Task with ID " + id + " not found");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting task: " + e.getMessage());
        } catch (TaskNotFoundException e) {
            throw new RuntimeException(e);
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
        task.setTaskType(rs.getString("task_type"));
        task.setPriority(rs.getString("priority"));
        task.setEstimatedHours(rs.getObject("estimated_hours") != null ? rs.getInt("estimated_hours") : null);

        return task;
    }
}