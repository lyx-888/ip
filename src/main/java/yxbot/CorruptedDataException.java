package yxbot;

/**
 * Thrown when the data file contains corrupted or invalid data.
 * This exception stops the program to prevent data loss.
 */
public class CorruptedDataException extends YXBotException{
    /**
     * Constructs a new CorruptedDataException with the specified detail message.
     *
     * @param message The detail message explaining the data corruption
     */
    public CorruptedDataException(String message){
        super(message);
    }
}
