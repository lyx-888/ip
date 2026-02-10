package yxbot;

public class DuplicateTaskException extends YXBotException {
    public DuplicateTaskException() {
        super("This task already exists in your list.");
    }
}
