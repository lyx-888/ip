package yxbot;

/**
 * Thrown when a delete command has invalid format.
 * Example: "delete" (missing task number) or "delete abc" (non-numeric task number)
 */
public class InvalidDeleteFormatException extends YXBotException{
    /**
     * Constructs a new InvalidDeleteFormatException with a default message.
     */
    public InvalidDeleteFormatException(){
        super("Missing task number! Correct format: delete [task number]");
    }
}
