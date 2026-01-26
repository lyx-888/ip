public class InvalidDeadlineFormatException extends YXBotException{
    public InvalidDeadlineFormatException() {
        super("Invalid deadline format! Use: deadline [description] /by [time]");
    }

}
