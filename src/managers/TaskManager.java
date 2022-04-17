package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    //void addHistory(Task task);

    void createTask(Task task);

    void createSubTask(Subtask subTask);

    void createEpic(Epic epic);

    void updatedTask(Task task);

    void updatedEpic(Epic epic);

    void updatedSubTask(Subtask subTask);

    Task getTaskById(int number);

    Epic getEpicById(int number);

    Subtask getSubTaskById(int number);

    List<Subtask> getListSubTasks(int number);

    StatusTask getStatusById(int number);

    void deleteTaskById(int number);

    void deleteSubTaskById(int number);

    void deleteEpicById(int number);

    void deleteAllTask();
}
