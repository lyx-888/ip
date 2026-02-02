package yxbot;

/**
 * Thrown when attempting to access a task that doesn't exist (null).
 * This can happen if the data file contains corrupted entries.
 */
public class TaskDoesNotExistException extends YXBotException {
    /**
     * Constructs a new TaskDoesNotExistException with a default message.
     */
    public TaskDoesNotExistException(){
        super("yxbot.Task does not exist!");
    }
}
