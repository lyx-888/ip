package yxbot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
/**
 * Handles loading and saving tasks to/from a data file.
 * Manages file operations and data persistence for the chatbot.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath Path to the data file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }


    /**
     * Loads tasks from the data file.
     *
     * @return ArrayList of loaded tasks
     * @throws CorruptedDataException if file cannot be read or contains invalid data
     */
    public ArrayList<Task> load() throws CorruptedDataException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(filePath);
            File folder = file.getParentFile();

            if (folder != null && !folder.exists()) {
                folder.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }

            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (!line.isEmpty()) {
                        Task task = parseTask(line);
                        tasks.add(task);
                    }
                }
            }

        } catch (IOException e) {
            throw new CorruptedDataException("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Parses a single line from the data file into a Task object.
     *
     * @param line The line to parse
     * @return Parsed Task object
     * @throws CorruptedDataException if line format is invalid
     */
    private Task parseTask(String line) throws CorruptedDataException {
        try{
            String[] parts = line.split(" \\| ");

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task = null;

            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    String by = parts[3];
                    task = new Deadline(description, by);
                    break;
                case "E":
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                    break;
            }

            if (task != null && isDone) {
                task.markAsDone();
            }

            return task;

        }catch (DateTimeParseException e) {
            throw new CorruptedDataException("Invalid date format in task: " + line);
        } catch (Exception e) {
            throw new CorruptedDataException("Error parsing task: " + line);
        }
    }

    /**
     * Saves tasks to the data file.
     *
     * @param tasks The list of tasks to save
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            File folder = file.getParentFile();

            if (folder != null && !folder.exists()) {
                folder.mkdirs();
            }

            try (FileWriter writer = new FileWriter(file)) {
                for (Task task : tasks) {
                    writer.write(task.toFileFormat() + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}