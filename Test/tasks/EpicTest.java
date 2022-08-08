package tasks;

import managers.InMemoryTaskManager;
import managers.StatusTask;
import managers.TaskManager;
import managers.TypeTask;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTest {

    static private TaskManager manager;

    @BeforeAll
    public static void init() {
        manager = new InMemoryTaskManager();
    }

    @AfterAll
    public static void clear() {
        manager = null;
    }

    @Test
    void testStatusForEmptyEpic() {
        Epic epic = new Epic(
                TypeTask.EPIC,
                "Эпик",
                "Эпик.Описание",
                LocalDateTime.now(),
                0L
        );

        manager.createEpic(epic);
        int id = epic.getId();

        StatusTask epicStatus = manager.getStatusById(id);
        assertNotNull(epicStatus, "Эпик не найден!");
        assertEquals(StatusTask.NEW, epicStatus, "У пустого эпика статус должен быть NEW!");
    }
}