package yxbot;

/**
 * Thrown when a mark command has invalid format.
 * Example: "mark" (missing task number) or "mark abc" (non-numeric task number)
 */
public class InvalidMarkFormatException extends YXBotException{
    /**
     * Constructs a new InvalidMarkFormatException with a default message.
     */
    public InvalidMarkFormatException(){
        super("Missing task number! Correct format: mark [task number]");
    }
}
