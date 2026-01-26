public class InvalidMarkFormatException extends YXBotException{
    public InvalidMarkFormatException(){
        super("Missing task number! Correct format: mark [task number]");
    }
}
