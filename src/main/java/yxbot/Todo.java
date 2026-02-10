package yxbot;

public class Todo extends Task{
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        assert description != null : "Todo description should not be null";
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat(){
        return "T | " + super.toFileFormat() + " | " + description;
    }
}
