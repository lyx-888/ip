package yxbot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void markAsDone_updatesStatusIconAndToString() {
        Task t = new Task("read book");
        assertEquals("[ ] read book", t.toString());
        assertEquals(" ", t.getStatusIcon());
        assertEquals("0", t.toFileFormat());

        t.markAsDone();
        assertTrue(t.isDone());
        assertEquals("X", t.getStatusIcon());
        assertEquals("[X] read book", t.toString());
        assertEquals("1", t.toFileFormat());
    }

    @Test
    public void markAsNotDone_revertsDoneState() {
        Task t = new Task("gym");
        t.markAsDone();
        assertTrue(t.isDone());

        t.markAsNotDone();
        assertFalse(t.isDone());
        assertEquals("[ ] gym", t.toString());
        assertEquals("0", t.toFileFormat());
    }
}
