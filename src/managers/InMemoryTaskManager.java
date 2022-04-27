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
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(task);
        }
        return allTasks;
    }

    @Override
    public List<Epic> getAllEpics() {
        List<Epic> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epic);
        }
        return allEpics;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allSubtasks.add(subtask);
        }
        return allSubtasks;
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
        Epic epic = getEpicById(subtask.getIdEpic());
        epic.setListSubTask(subtask);
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
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Subtask getSubTaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
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
        Task removed = tasks.remove(id);
        if (removed == null) {
            System.out.println("Задачи с номером " + id + " нет в списке!");
        } else {
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        Subtask removed = subtasks.remove(id);
        if (removed == null) {
            System.out.println("Подзадачи с номером " + id + " нет в списке!");
        } else {
            historyManager.remove(id);
            int idEpic = removed.getIdEpic();
            epics.get(idEpic).deleteSubTask(removed);
            setStatusEpic(idEpic);
        }
    }

    @Override
    public void deleteEpicById(int id) {
        Epic removed = epics.remove(id);
        if (removed == null) {
            System.out.println("Эпика с номером " + id + " нет в списке!");
        } else {
            historyManager.remove(id);
            for (Subtask subTaskEpic : removed.getListSubTask()) {
                int idSubtask = subTaskEpic.getId();
                subtasks.remove(idSubtask);
                historyManager.remove(idSubtask);
            }
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
}
