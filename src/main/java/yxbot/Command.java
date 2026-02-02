package yxbot;

public class Command {
    private CommandType type;
    private Integer index;
    private Task task;

    public Command(CommandType type) {
        this.type = type;
    }

    public Command(CommandType type, int index) {
        this.type = type;
        this.index = index;
    }

    public Command(CommandType type, Task task) {
        this.type = type;
        this.task = task;
    }

    public CommandType getType() {
        return type;
    }

    public Integer getIndex() {
        return index;
    }

    public Task getTask() {
        return task;
    }
}

