package yxbot;

import java.util.Objects;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * A Deadline task has a description and a specific date/time by which it must be completed.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructs a new Deadline task with the specified description and due date.
     *
     * @param description The description of the deadline task
     * @param by The due date and time in "yyyy-MM-dd HHmm" format
     * @throws DateTimeParseException If the date string cannot be parsed
     */
    public Deadline(String description, String by) throws DateTimeParseException {
        super(description);
        this.by = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    /**
     * Returns the string representation of the deadline task for display.
     * Format: [D][status] description (by: MMM dd yyyy, h:mm a)
     *
     * @return Formatted string showing task type, status, description, and deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the file format representation of the deadline task for storage.
     * Format: D | status | description | yyyy-MM-dd HHmm
     *
     * @return String formatted for saving to data file
     */
    @Override
    public String toFileFormat(){
        return "D | " + super.toFileFormat() + " | " + description + " | " +
                by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Compares this deadline task to another object for equality.
     * Two deadline tasks are equal if they have the same description, status, and due date.
     *
     * @param other The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        Deadline o = (Deadline) other;
        return Objects.equals(by, o.by);
    }

    /**
     * Returns a hash code for this deadline task.
     *
     * @return A hash code based on the task's class, description, and due date
     */
    @Override
    public int hashCode() {
        return Objects.hash(getClass(), description, by);
    }
}
