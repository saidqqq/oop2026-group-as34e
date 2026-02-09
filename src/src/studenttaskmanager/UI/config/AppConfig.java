package studenttaskmanager.UI.config;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static AppConfig instance;
    private Properties properties;

    private AppConfig() {
        loadProperties();
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input != null) {
                properties.load(input);
                System.out.println("Loaded configuration from config.properties");
            } else {
                // Use default values if file not found
                setDefaultProperties();
                System.out.println("Using default configuration");
            }
        } catch (Exception e) {
            System.err.println("Error loading config: " + e.getMessage());
            setDefaultProperties();
        }
    }

    private void setDefaultProperties() {
        properties = new Properties();
        properties.setProperty("db.url", "jdbc:postgresql://localhost:5432/student_tasks_db");
        properties.setProperty("db.user", "postgres");
        properties.setProperty("db.password", "12345");
        properties.setProperty("app.name", "Student Task Manager v2.0");
        properties.setProperty("app.version", "2.0.0");
        properties.setProperty("default.project.id", "1");
        properties.setProperty("max.tasks.per.project", "100");
    }

    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUser() {
        return properties.getProperty("db.user");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public String getAppName() {
        return properties.getProperty("app.name");
    }

    public String getAppVersion() {
        return properties.getProperty("app.version");
    }

    public int getDefaultProjectId() {
        return Integer.parseInt(properties.getProperty("default.project.id"));
    }

    public int getMaxTasksPerProject() {
        return Integer.parseInt(properties.getProperty("max.tasks.per.project"));
    }
}