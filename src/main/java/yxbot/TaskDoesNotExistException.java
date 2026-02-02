package yxbot;

public class TaskDoesNotExistException extends YXBotException {
    public TaskDoesNotExistException(){
        super("yxbot.Task does not exist!");
    }
}
