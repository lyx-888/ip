package yxbot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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