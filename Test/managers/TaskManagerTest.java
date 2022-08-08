package managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class TaskManagerTest<T extends TaskManager> {

    private TaskManager manager;
    abstract T getManager();

    @BeforeEach
    public void init() {
        manager = getManager();
    }

    @AfterEach
    public void clear() {
        manager = null;
    }

    @Test
    void addNewTask() {
        Task task = new Task(TypeTask.TASK, "Задача1", "Задача1.Описание", LocalDateTime.now(), 0L);

        manager.createTask(task);
        Task newCreatedTask = manager.getAllTasks().get(0);

        final int taskId = newCreatedTask.getId();
        final Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void testStatusForEmptyEpic() {
        Epic epic = new Epic(TypeTask.EPIC, "Эпик", "Эпик.Описание", LocalDateTime.now(), 0L);

        manager.createEpic(epic);
        int id = epic.getId();

        StatusTask epicStatus = manager.getStatusById(id);
        assertNotNull(epicStatus, "Эпик не найден!");
        assertEquals(1, manager.getAllEpics().size(), "Эпик должен быть один!");
        assertEquals(StatusTask.NEW, epicStatus, "У пустого эпика статус должен быть NEW!");
    }

    @Test
    void testEpicStatusForNewAndDoneSubtasks() {
        Epic epic = new Epic(TypeTask.EPIC, "Эпик", "Эпик.Описание", LocalDateTime.now(), 0L);

        manager.createEpic(epic);
        int id = epic.getId();

        Subtask task1 = new Subtask(TypeTask.SUBTASK, "Подзадача1", "Подзадача1.Описание", LocalDateTime.now(), 0L, id);
        Subtask task2 = new Subtask(TypeTask.SUBTASK, "Подзадача2", "Подзадача2.Описание", LocalDateTime.now(), 0L, id);
        task2.setStatus(StatusTask.DONE);

        manager.createSubTask(task1);
        manager.createSubTask(task2);


        StatusTask epicStatus = manager.getStatusById(id);
        assertNotNull(epicStatus, "Эпик не найден!");
        assertEquals(1, manager.getAllEpics().size(), "Эпик должен быть один!");
        assertEquals(StatusTask.IN_PROGRESS, epicStatus, String.format("Для текущего Эпика статус должен быть IN_PROGRESS! Текущий статус: %s", epicStatus));
    }

}