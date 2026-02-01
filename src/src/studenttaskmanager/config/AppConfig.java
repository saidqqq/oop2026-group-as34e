package studenttaskmanager.config;

import java.io.IOException;
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
            } else {
                setDefaultProperties();
            }
        } catch (IOException e) {
            setDefaultProperties();
        }
    }

    private void setDefaultProperties() {
        properties.setProperty("db.url", "jdbc:postgresql://localhost:5432/student_tasks_db");
        properties.setProperty("db.user", "postgres");
        properties.setProperty("db.password", "12345");
        properties.setProperty("app.name", "Student Task Manager");
        properties.setProperty("app.version", "2.0");
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
}