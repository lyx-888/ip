package yxbot;

/**
 * Thrown when an event command has invalid format.
 * Example: "event task" (missing /from or /to) or incorrect order of parameters
 */
public class InvalidEventFormatException extends YXBotException{
    /**
     * Constructs a new InvalidEventFormatException with a default message.
     */
    public InvalidEventFormatException(){
        super("Invalid event format! Use: event [description] /from [ yyyy-MM-dd HHmm] /to [ yyyy-MM-dd HHmm] (e.g., 2023-12-25 1800)");
    }
}
