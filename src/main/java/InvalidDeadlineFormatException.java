public class InvalidDeadlineFormatException extends YXBotException{
    public InvalidDeadlineFormatException() {
        super("Invalid deadline format! Use: deadline [description] /by [yyyy-MM-dd HHmm] (e.g., 2023-12-25 1800)");
    }

}
