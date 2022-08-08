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

    @Test
    void testEpicStatusForNewAndDoneSubtasks() {
        Epic epic = new Epic(
                TypeTask.EPIC,
                "Эпик",
                "Эпик.Описание",
                LocalDateTime.now(),
                0L
        );

        manager.createEpic(epic);
        int id = epic.getId();

        Subtask task1 = new Subtask(
                TypeTask.SUBTASK,
                "Подзадача1",
                "Подзадача1.Описание",
                LocalDateTime.now(),
                0L,
                id
        );

        Subtask task2 = new Subtask(
                TypeTask.SUBTASK,
                "Подзадача2",
                "Подзадача2.Описание",
                LocalDateTime.now(),
                0L,
                id
        );
        task2.setStatus(StatusTask.DONE);

        manager.createSubTask(task1);
        manager.createSubTask(task2);


        StatusTask epicStatus = manager.getStatusById(id);
        assertNotNull(epicStatus, "Эпик не найден!");
        assertEquals(StatusTask.IN_PROGRESS, epicStatus, String.format("Для текущего Эпика статус должен быть IN_PROGRESS! Текущий статус: %s", epicStatus));
    }
}