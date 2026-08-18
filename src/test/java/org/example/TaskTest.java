package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {

    @Test
    void trimsTaskTitle() {
        Task task = new Task("  Prepare portfolio  ");

        assertEquals("Prepare portfolio", task.getTitle());
    }

    @Test
    void rejectsBlankTaskTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task("   "));
    }

    @Test
    void rejectsNullTaskTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task(null));
    }
}
