package yxbot;

/**
 * Represents a command parsed from user input.
 * Contains the command type and any associated data needed for execution.
 * Serves as the base class for specific command implementations.
 */
public class Command {
    private CommandType type;
    private Integer index;
    private Task task;
    private String keyword;

    /**
     * Constructs a Command with only a type (for commands with no additional data).
     *
     * @param type The type of command (e.g., BYE, LIST)
     */
    public Command(CommandType type) {
        this.type = type;
    }

    /**
     * Constructs a Command that operates on a specific task by index.
     *
     * @param type The type of command (e.g., MARK, UNMARK, DELETE)
     * @param index The 0-based index of the task to operate on
     */
    public Command(CommandType type, int index) {
        this.type = type;
        this.index = index;
    }

    /**
     * Constructs a Command that adds a new task.
     *
     * @param type The type of command (e.g., TODO, DEADLINE, EVENT)
     * @param task The task to be added
     */
    public Command(CommandType type, Task task) {
        this.type = type;
        this.task = task;
    }

    /**
     * Constructs a Command that searches for tasks.
     *
     * @param type The type of command (FIND)
     * @param keyword The search keyword
     */
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

    /**
     * Executes the command. Base implementation throws an exception as this method
     * should be overridden by specific command subclasses.
     *
     * @param tasks The task list to operate on
     * @param ui The user interface for displaying results
     * @param storage The storage handler for persisting changes
     * @throws YXBotException If command execution fails or method is not overridden
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YXBotException {
        throw new YXBotException("Execute method must be overridden");
    }

}

