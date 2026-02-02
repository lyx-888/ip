package yxbot;

/**
 * Thrown when the user enters an unrecognized command.
 * Example: "random" or "help" (if not implemented)
 */
public class UnknownCommandException extends YXBotException{
    /**
     * Constructs a new UnknownCommandException with a default message.
     */
    public UnknownCommandException(){
        super("Unknown command!");
    }
}
