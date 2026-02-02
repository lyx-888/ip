package yxbot;

/**
 * Base class for all types of tasks.
 * Provides common functionality for task description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone(){
        this.isDone = true;
    }

    public void markAsNotDone(){
        this.isDone = false;
    }

    public boolean isDone(){
        return isDone;
    }

    /**
     * Returns the file format representation of the task.
     *
     * @return String representation for saving to file
     */
    public String toFileFormat(){
        return isDone ? "1" : "0";
    }

    /**
     * Returns the string representation of the task for display.
     *
     * @return Formatted string showing task status and description
     */
    @Override
    public String toString(){
        return "[" + getStatusIcon() + "] " + description;
    }
}
