package yxbot;

public class UnknownCommandException extends YXBotException{
    public UnknownCommandException(){
        super("Unknown command!");
    }
}
