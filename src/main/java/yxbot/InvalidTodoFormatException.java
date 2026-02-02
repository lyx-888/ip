package yxbot;

/**
 * Thrown when a todo command has invalid format.
 * Example: "todo" (missing description) or empty description
 */
public class InvalidTodoFormatException extends YXBotException{
    /**
     * Constructs a new InvalidTodoFormatException with a default message.
     */
    public InvalidTodoFormatException(){
        super("Invalid todo format! Use: todo [description]");
    }
}
