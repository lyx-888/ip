package yxbot;

public class InvalidFindFormatException extends YXBotException {
    public InvalidFindFormatException(){
        super("Invalid find format! Use: find [keyword]");
    }
}
