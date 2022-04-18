package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void createTask(Task task) {
        int id = getId();
        task.setId(id);
        this.tasks.put(id, task);
    }

    @Override
    public void createSubTask(Subtask subtask) {
        int id = getId();
        subtask.setId(id);
        this.subtasks.put(id, subtask);
        addSubTaskInEpic(getEpicById(subtask.getIdEpic()), subtask);
        setStatusEpic(subtask.getIdEpic());
    }

    @Override
    public void createEpic(Epic epic) {
        int id = getId();
        epic.setId(id);
        this.epics.put(id, epic);
    }

    public Map<Integer, Task> getTask() {
        return tasks;
    }

    public Map<Integer, Epic> getEpic() {
        return epics;
    }

    public Map<Integer, Subtask> getSubTask() {
        return subtasks;
    }

    @Override
    public void updatedTask(Task task) {
        this.tasks.put(task.getId(), task);
    }

    @Override
    public void updatedEpic(Epic epic) {
        this.epics.put(epic.getId(), epic);
        setStatusEpic(epic.getId());
    }

    @Override
    public void updatedSubTask(Subtask subTask) {
        this.subtasks.put(subTask.getId(), subTask);
        epics.get(subTask.getIdEpic()).updatedListSubTask(subTask);
        setStatusEpic(subTask.getIdEpic());
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.getOrDefault(id, null);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getSubTaskById(int id) {
        Subtask subtask = subtasks.getOrDefault(id, null);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.getOrDefault(id, null);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public List<Subtask> getListSubTasks(int id) {
        if (getEpicById(id) != null) {
            return getEpicById(id).getListSubTask();
        } else {
            System.out.println("Эпика с номером " + id + " не существует!");
            return null;
        }
    }

    @Override
    public StatusTask getStatusById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id).getStatus();
        } else if (epics.containsKey(id)) {
            return epics.get(id).getStatus();
        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id).getStatus();
        } else {
            System.out.println("Задачи с номером " + id + " нет в списке!");
            return null;
        }
    }

    @Override
    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Задачи с номером " + id + " нет в списке!");
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        if (subtasks.containsKey(id)) {
            int idEpic = subtasks.get(id).getIdEpic();
            epics.get(idEpic).deleteSubTask(subtasks.get(id));
            setStatusEpic(idEpic);
            subtasks.remove(id);
        } else {
            System.out.println("Подзадачи с номером " + id + " нет в списке!");
        }
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            for (Subtask subTaskEpic : epics.get(id).getListSubTask()) {
                subtasks.remove(subTaskEpic.getId());
            }
            epics.remove(id);
        } else {
            System.out.println("Эпика с номером " + id + " нет в списке!");
        }
    }

    @Override
    public void deleteAllTask() {
        if (!tasks.isEmpty()) {
            tasks.clear();
        }
        if (!subtasks.isEmpty()) {
            subtasks.clear();
        }
        if (!epics.isEmpty()) {
            epics.clear();
        }
        this.id = 0;
    }

    private int getId() {
        return ++this.id;
    }

    private void setStatusEpic(int idEpic) {
        List<Subtask> listSubTask = getListSubTasks(idEpic);
        boolean isStatusNew = false;
        boolean isStatusInProgess = false;
        boolean isStatusDone = false;

        for (Subtask list : listSubTask) {
            switch (list.getStatus().toString()) {
                case ("NEW"):
                    isStatusNew = true;
                    break;
                case ("IN_PROGRESS"):
                    isStatusInProgess = true;
                    break;
                case ("DONE"):
                    isStatusDone = true;
                    break;
            }
        }

        if (!isStatusNew && !isStatusInProgess && isStatusDone) {
            epics.get(idEpic).setStatus(StatusTask.DONE);
        } else if (isStatusNew && !isStatusInProgess && !isStatusDone) {
            epics.get(idEpic).setStatus(StatusTask.NEW);
        } else {
            epics.get(idEpic).setStatus(StatusTask.IN_PROGRESS);
        }
    }

    private void addSubTaskInEpic(Epic epic, Subtask subTask) {
        epic.setListSubTask(subTask);
    }
}
