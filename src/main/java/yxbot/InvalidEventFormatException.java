package yxbot;

public class InvalidEventFormatException extends YXBotException{
    public InvalidEventFormatException(){
        super("Invalid event format! Use: event [description] /from [ yyyy-MM-dd HHmm] /to [ yyyy-MM-dd HHmm] (e.g., 2023-12-25 1800)");
    }
}
