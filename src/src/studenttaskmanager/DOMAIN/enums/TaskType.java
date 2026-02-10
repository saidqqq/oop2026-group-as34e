package studenttaskmanager.DOMAIN.enums;

public enum TaskType {
    BUG("Bug"),
    FEATURE("Feature"),
    RESEARCH("Research"),
    DOCUMENTATION("Documentation"),
    TEST("Test");

    private final String displayName;

    TaskType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}