public class InvalidUnmarkFormatException extends YXBotException{
    public InvalidUnmarkFormatException(){
        super("Missing task number! Correct format: unmark [task number]");
    }
}
