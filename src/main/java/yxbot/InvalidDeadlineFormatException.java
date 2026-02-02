package yxbot;

/**
 * Thrown when a deadline command has invalid format.
 * Example: "deadline task" (missing /by) or "deadline task /by" (missing date)
 */
public class InvalidDeadlineFormatException extends YXBotException{
    /**
     * Constructs a new InvalidDeadlineFormatException with a default message.
     */
    public InvalidDeadlineFormatException() {
        super("Invalid deadline format! Use: deadline [description] /by [yyyy-MM-dd HHmm] (e.g., 2023-12-25 1800)");
    }

}
