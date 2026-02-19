package yxbot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void load_fileDoesNotExist_createsFileAndReturnsEmptyList() throws Exception {
        Path filePath = tempDir.resolve("data").resolve("tasks.txt");
        Storage storage = new Storage(filePath.toString());

        assertFalse(Files.exists(filePath));

        ArrayList<Task> tasks = storage.load();

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());

        assertTrue(Files.exists(filePath));
    }

    @Test
    public void save_writesOneLinePerTask_usingToFileFormatOnly() throws Exception {
        Path filePath = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(filePath.toString());

        ArrayList<Task> tasks = new ArrayList<>();
        Task a = new Task("a");
        Task b = new Task("b");
        b.markAsDone();

        tasks.add(a);
        tasks.add(b);

        storage.save(tasks);

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(2, lines.size());
        assertEquals("0", lines.get(0));
        assertEquals("1", lines.get(1));
    }
}
