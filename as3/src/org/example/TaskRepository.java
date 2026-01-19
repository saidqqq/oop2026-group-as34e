import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private Connection connection;

    public TaskRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTask(Task task) throws SQLException {
        String query = "INSERT INTO tasks (name, status, project_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getStatus());
            statement.setInt(3, task.getProjectId());
            statement.executeUpdate();
        }
    }

    public Task getTaskById(int id) throws SQLException {
        String query = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setStatus(resultSet.getString("status"));
                task.setProjectId(resultSet.getInt("project_id"));
                return task;
            }
        }
        return null;
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setStatus(resultSet.getString("status"));
                task.setProjectId(resultSet.getInt("project_id"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void updateTask(Task task) throws SQLException {
        String query = "UPDATE tasks SET name = ?, status = ?, project_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getStatus());
            statement.setInt(3, task.getProjectId());
            statement.setInt(4, task.getId());
            statement.executeUpdate();
        }
    }

    public void deleteTask(int id) throws SQLException {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
