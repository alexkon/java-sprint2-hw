package managers;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    void addHistory(Task task);

    void createTask(Task task);

    void createSubTask(SubTask subTask);

    void createEpic(Epic epic);

    void updatedTask(Task task);

    void updatedEpic(Epic epic);

    void updatedSubTask(SubTask subTask);

    Task getTaskById(int number);

    Epic getEpicById(int number);

    SubTask getSubTaskById(int number);

    ArrayList<SubTask> getListSubTasks(int number);

    StatusTask getStatusById(int number);

    void deleteTaskById(int number);

    void deleteSubTaskById(int number);

    void deleteEpicById(int number);

    void deleteAllTask();
}
