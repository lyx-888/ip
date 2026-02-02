package yxbot;

/**
 * Base exception class for all YXBot-specific exceptions.
 * All custom exceptions in YXBot should extend this class.
 */
public class YXBotException extends Exception{
    /**
     * Constructs a new YXBotException with the specified detail message.
     *
     * @param message The detail message explaining the error
     */
    public YXBotException(String message){
        super(message);
    }
}
