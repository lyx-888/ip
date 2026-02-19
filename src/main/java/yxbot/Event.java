package yxbot;

import java.util.Objects;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end time.
 * An Event task has a description and specific date/time range during which it occurs.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     *
     * @param description The description of the event
     * @param from The start date and time in "yyyy-MM-dd HHmm" format
     * @param to The end date and time in "yyyy-MM-dd HHmm" format
     * @throws DateTimeParseException If either date string cannot be parsed
     */
    public Event(String description, String from, String to) throws DateTimeParseException {
        super(description);
        this.from = LocalDateTime.parse(from, INPUT_FORMAT);
        this.to = LocalDateTime.parse(to, INPUT_FORMAT);
    }

    /**
     * Returns the string representation of the event task for display.
     * Format: [E][status] description (from: MMM dd yyyy, h:mm a to: MMM dd yyyy, h:mm a)
     *
     * @return Formatted string showing task type, status, description, and time range
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " +
                from.format(OUTPUT_FORMAT) + " to: " +
                to.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the file format representation of the event task for storage.
     * Format: E | status | description | yyyy-MM-dd HHmm | yyyy-MM-dd HHmm
     *
     * @return String formatted for saving to data file
     */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + description +
                " | " + from.format(DateTimeFormatter
                .ofPattern("yyyy-MM-dd HHmm"))
                + " | " + to.format(DateTimeFormatter
                .ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Compares this event task to another object for equality.
     * Two event tasks are equal if they have the same description, status, start time, and end time.
     *
     * @param other The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        Event o = (Event) other;
        return Objects.equals(from, o.from) && Objects.equals(to, o.to);
    }

    /**
     * Returns a hash code for this event task.
     *
     * @return A hash code based on the task's class, description, start time, and end time
     */
    @Override
    public int hashCode() {
        return Objects.hash(getClass(), description, from, to);
    }
}
