package yxbot;

/**
 * Thrown when a task number is out of valid range.
 * Example: "mark 0" or "mark 10" when only 5 tasks exist
 */
public class InvalidTaskNumberException extends YXBotException{
    /**
     * Constructs a new InvalidTaskNumberException with a default message.
     */
    public InvalidTaskNumberException(){
        super("Invalid task number!");
    }
}
