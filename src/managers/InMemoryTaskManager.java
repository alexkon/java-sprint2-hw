package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    //private final int historyMaxLength = 10;
    private int id = 0;
    private Map<Integer, Task> taskTask = new HashMap<>();
    private Map<Integer, Epic> taskEpic = new HashMap<>();
    private Map<Integer, Subtask> taskSubtask = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void createTask(Task task) {
        int number = getId();
        task.setId(number);
        this.taskTask.put(number, task);
    }

    @Override
    public void createSubTask(Subtask subtask) {
        int number = getId();
        subtask.setId(number);
        this.taskSubtask.put(number, subtask);
        addSubTaskInEpic(getEpicById(subtask.getIdEpic()), subtask);
        setStatusEpic(subtask.getIdEpic());
    }

    @Override
    public void createEpic(Epic epic) {
        int number = getId();
        epic.setId(number);
        this.taskEpic.put(number, epic);
    }

    public Map<Integer, Task> getTask() {
        return taskTask;
    }

    public Map<Integer, Epic> getEpic() {
        return taskEpic;
    }

    public Map<Integer, Subtask> getSubTask() {
        return taskSubtask;
    }

    @Override
    public void updatedTask(Task task) {
        this.taskTask.put(task.getId(), task);
    }

    @Override
    public void updatedEpic(Epic epic) {
        this.taskEpic.put(epic.getId(), epic);
        setStatusEpic(epic.getId());
    }

    @Override
    public void updatedSubTask(Subtask subTask) {
        this.taskSubtask.put(subTask.getId(), subTask);
        taskEpic.get(subTask.getIdEpic()).updatedListSubTask(subTask);
        setStatusEpic(subTask.getIdEpic());
    }

    @Override
    public Task getTaskById(int number) {
        historyManager.add(taskTask.getOrDefault(number, null));
        return taskTask.getOrDefault(number, null);
    }

    @Override
    public Subtask getSubTaskById(int number) {
        historyManager.add(taskTask.getOrDefault(number, null));
        return taskSubtask.getOrDefault(number, null);
    }

    @Override
    public Epic getEpicById(int number) {
        historyManager.add(taskTask.getOrDefault(number, null));
        return taskEpic.getOrDefault(number, null);
    }

    @Override
    public List<Subtask> getListSubTasks(int number) {
        if (getEpicById(number) != null) {
            return getEpicById(number).getListSubTask();
        } else {
            System.out.println("Эпика с номером " + number + " не существует!");
            return null;
        }
    }

    @Override
    public StatusTask getStatusById(int number) {
        if (taskTask.containsKey(number)) {
            return taskTask.get(number).getStatus();
        } else if (taskEpic.containsKey(number)) {
            return taskEpic.get(number).getStatus();
        } else if (taskSubtask.containsKey(number)) {
            return taskSubtask.get(number).getStatus();
        } else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
            return null;
        }
    }

    @Override
    public void deleteTaskById(int number) {
        if (taskTask.containsKey(number)) {
            taskTask.remove(number);
        } else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
        }
    }

    @Override
    public void deleteSubTaskById(int number) {
        if (taskSubtask.containsKey(number)) {
            int numberEpic = taskSubtask.get(number).getIdEpic();
            taskEpic.get(numberEpic).deleteSubTask(taskSubtask.get(number));
            setStatusEpic(numberEpic);
            taskSubtask.remove(number);
        } else {
            System.out.println("Подзадачи с номером " + number + " нет в списке!");
        }
    }

    @Override
    public void deleteEpicById(int number) {
        if (taskEpic.containsKey(number)) {
            for (Subtask subTaskEpic : taskEpic.get(number).getListSubTask()) {
                taskSubtask.remove(subTaskEpic.getId());
            }
            taskEpic.remove(number);
        } else {
            System.out.println("Эпика с номером " + number + " нет в списке!");
        }
    }

    @Override
    public void deleteAllTask() {
        if (!taskTask.isEmpty()) {
            taskTask.clear();
        }
        if (!taskSubtask.isEmpty()) {
            taskSubtask.clear();
        }
        if (!taskEpic.isEmpty()) {
            taskEpic.clear();
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
            taskEpic.get(idEpic).setStatus(StatusTask.DONE);
        } else if (isStatusNew && !isStatusInProgess && !isStatusDone) {
            taskEpic.get(idEpic).setStatus(StatusTask.NEW);
        } else {
            taskEpic.get(idEpic).setStatus(StatusTask.IN_PROGRESS);
        }
    }

    private void addSubTaskInEpic(Epic epic, Subtask subTask) {
        epic.setListSubTask(subTask);
    }
}
