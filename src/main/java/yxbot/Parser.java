package yxbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input into Command objects.
 * Handles validation of command syntax.
 */
public class Parser {
    private static final DateTimeFormatter DATE_INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Parses a user input string into a Command object.
     *
     * @param input The user input string
     * @return Corresponding Command object
     * @throws YXBotException if input format is invalid
     */
    public static Command parse(String input) throws YXBotException {
        String commandWord = input.contains(" ")
                ? input.substring(0, input.indexOf(" "))
                : input;

        switch (commandWord) {
            case "bye":
                if (!input.equals("bye")) {
                    throw new UnknownCommandException();
                }
                return new Command(CommandType.BYE);

            case "list":
                if (!input.equals("list")) {
                    throw new UnknownCommandException();
                }
                return new Command(CommandType.LIST);

            case "mark":
                return parseMark(input);

            case "unmark":
                return parseUnmark(input);

            case "delete":
                return parseDelete(input);

            case "todo":
                return parseTodo(input);

            case "deadline":
                return parseDeadline(input);

            case "event":
                return parseEvent(input);

            case "find":
                return parseFind(input);

            default:
                throw new UnknownCommandException();
        }
    }

    private static Command parseMark(String input) throws InvalidMarkFormatException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidMarkFormatException();
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            return new Command(CommandType.MARK, index);
        } catch (NumberFormatException e) {
            throw new InvalidMarkFormatException();
        }
    }

    private static Command parseUnmark(String input) throws InvalidUnmarkFormatException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidUnmarkFormatException();
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            return new Command(CommandType.UNMARK, index);
        } catch (NumberFormatException e) {
            throw new InvalidUnmarkFormatException();
        }
    }

    private static Command parseDelete(String input) throws InvalidDeleteFormatException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidDeleteFormatException();
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            return new Command(CommandType.DELETE, index);
        } catch (NumberFormatException e) {
            throw new InvalidDeleteFormatException();
        }
    }

    private static Command parseTodo(String input) throws YXBotException {
        if (input.length() <= 4 || !input.startsWith("todo ")) {
            throw new InvalidTodoFormatException();
        }

        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new InvalidTodoFormatException();
        }

        rejectPipe(description);

        return new Command(CommandType.TODO, new Todo(description));
    }

    private static Command parseDeadline(String input) throws YXBotException {
        if (!input.contains(" /by ")) {
            throw new InvalidDeadlineFormatException();
        }

        String content = input.substring(9).trim();
        String[] parts = content.split(" /by ");
        if (parts.length != 2) {
            throw new InvalidDeadlineFormatException();
        }

        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new InvalidDeadlineFormatException();
        }

        rejectPipe(description);

        try {
            LocalDateTime.parse(by, DATE_INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidDeadlineFormatException();
        }

        return new Command(CommandType.DEADLINE, new Deadline(description, by));
    }

    private static Command parseEvent(String input) throws YXBotException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new InvalidEventFormatException();
        }

        int fromIndex = input.indexOf(" /from ");
        int toIndex = input.indexOf(" /to ");
        if (fromIndex >= toIndex) {
            throw new InvalidEventFormatException();
        }

        String content = input.substring(6).trim();
        String[] parts = content.split(" /from | /to ");
        if (parts.length != 3) {
            throw new InvalidEventFormatException();
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new InvalidEventFormatException();
        }

        rejectPipe(description);

        try {
            LocalDateTime dtFrom = LocalDateTime.parse(from, DATE_INPUT_FORMAT);
            LocalDateTime dtTo = LocalDateTime.parse(to, DATE_INPUT_FORMAT);
            if (!dtTo.isAfter(dtFrom)) {
                throw new InvalidEventTimeRangeException();
            }
        } catch (DateTimeParseException e) {
            throw new InvalidEventFormatException();
        }

        return new Command(CommandType.EVENT, new Event(description, from, to));
    }

    private static Command parseFind(String input) throws InvalidFindFormatException {
        if (input.length() <= 5) {
            throw new InvalidFindFormatException();
        }

        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new InvalidFindFormatException();
        }

        return new FindCommand(keyword);
    }

    private static void rejectPipe(String text) throws YXBotException {
        if (text.contains("|")) {
            throw new YXBotException("Please don't use '|' in task descriptions.");
        }
    }
}
