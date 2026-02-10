package studenttaskmanager.DOMAIN.exceptions;

public class DeadlinePassedException extends Exception {
    public DeadlinePassedException(String message) {
        super(message);
    }
}