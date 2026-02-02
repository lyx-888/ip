package yxbot;

/**
 * Thrown when an unmark command has invalid format.
 * Example: "unmark" (missing task number) or "unmark abc" (non-numeric task number)
 */
public class InvalidUnmarkFormatException extends YXBotException{
    /**
     * Constructs a new InvalidUnmarkFormatException with a default message.
     */
    public InvalidUnmarkFormatException(){
        super("Missing task number! Correct format: unmark [task number]");
    }
}
