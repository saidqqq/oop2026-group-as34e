package studenttaskmanager.DOMAIN.exceptions;

public class ProjectNotFoundException extends Exception {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}