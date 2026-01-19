import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    private Connection connection;

    public ProjectRepository(Connection connection) {
        this.connection = connection;
    }

    public void createProject(Project project) throws SQLException {
        String query = "INSERT INTO projects (name, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.executeUpdate();
        }
    }

    public Project getProjectById(int id) throws SQLException {
        String query = "SELECT * FROM projects WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                return project;
            }
        }
        return null;
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                projects.add(project);
            }
        }
        return projects;
    }

    public void updateProject(Project project) throws SQLException {
        String query = "UPDATE projects SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setInt(3, project.getId());
            statement.executeUpdate();
        }
    }

    public void deleteProject(int id) throws SQLException {
        String query = "DELETE FROM projects WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
