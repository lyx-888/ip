public class InvalidEventFormatException extends YXBotException{
    public InvalidEventFormatException(){
        super("Invalid event format! Use: event [description] /from [start] /to [end]");
    }
}
