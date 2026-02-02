package yxbot;

public class Command {
    private CommandType type;
    private Integer index;
    private Task task;
    private String keyword;

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

    public Command(CommandType type, String keyword) {
        this.type = type;
        this.keyword = keyword;
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

    public String getKeyword() {
        return keyword;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws YXBotException {
        throw new YXBotException("Execute method must be overridden");
    }


}

