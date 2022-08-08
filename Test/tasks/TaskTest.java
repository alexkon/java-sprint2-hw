package tasks;

import managers.InMemoryTaskManager;
import managers.TaskManager;
import managers.TypeTask;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void addNewTask() {

        TaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task(
                TypeTask.TASK, "Задача1",
                "Задача1.Описание",
                LocalDateTime.of(2022, 7, 6, 10, 30, 0),
                (long) (60 * 24 * 14)
        );

        taskManager.createTask(task);
        Task newCreatedTask = taskManager.getAllTasks().get(0);

        final int taskId = newCreatedTask.getId();
        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

}