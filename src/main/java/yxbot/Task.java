package yxbot;

import java.util.Objects;

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
        assert description != null : "Task description cannot be null";
        assert !description.trim().isEmpty() : "Task description cannot be empty";
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Task otherTask = (Task) other;
        return Objects.equals(description, otherTask.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), description);
    }

}
