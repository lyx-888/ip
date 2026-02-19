package yxbot;

/**
 * Represents a todo task.
 * A Todo task is the simplest task type with only a description and completion status.
 */
public class Todo extends Task{

    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description The description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo task for display.
     * Format: [T][status] description
     *
     * @return Formatted string showing task type, status, and description
     */
    @Override
    public String toString() {
        assert description != null : "Todo description should not be null";
        return "[T]" + super.toString();
    }

    /**
     * Returns the file format representation of the todo task for storage.
     * Format: T | status | description
     *
     * @return String formatted for saving to data file
     */
    @Override
    public String toFileFormat(){
        return "T | " + super.toFileFormat() + " | " + description;
    }
}
