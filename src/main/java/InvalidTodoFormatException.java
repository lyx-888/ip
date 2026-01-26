public class InvalidTodoFormatException extends YXBotException{
    public InvalidTodoFormatException(){
        super("Invalid todo format! Use: todo [description]");
    }
}
