package yxbot;

public class InvalidEventTimeRangeException extends YXBotException {
    public InvalidEventTimeRangeException() {
        super("Event end time must be after start time.");
    }
}
