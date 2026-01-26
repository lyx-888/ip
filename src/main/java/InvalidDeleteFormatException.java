public class InvalidDeleteFormatException extends YXBotException{
    public InvalidDeleteFormatException(){
        super("Missing task number! Correct format: delete [task number]");
    }
}
