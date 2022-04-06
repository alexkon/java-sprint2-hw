package managers;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int id = 0;
    private HashMap<Integer, Task> taskTask = new HashMap<>();
    // не знаю, как по-другому создать новую мапу, чтобы не нарушить инкапсуляцию!!!
    private HashMap<Integer, Epic> taskEpic = new HashMap<>();
    private HashMap<Integer, SubTask> taskSubTask = new HashMap<>();

    public int getId() {
        return ++this.id;
    }

    public void createTask(Task task) {
        int number = getId();
        task.setId(number);
        this.taskTask.put(number, task);
    }

    public HashMap<Integer, Task> getTask() {
        return taskTask;
    }

    public HashMap<Integer, Epic> getEpic() {
        return taskEpic;
    }

    public HashMap<Integer, SubTask> getSubTask() {
        return taskSubTask;
    }

    public void createSubTask(SubTask subTask) {
        int number = getId();
        subTask.setId(number);
        this.taskSubTask.put(number, subTask);
        addSubTaskInEpic(getEpicById(subTask.getIdEpic()), subTask);
        setStatusEpic(subTask.getIdEpic());
    }

    public void createEpic(Epic epic) {
        int number = getId();
        epic.setId(number);
        this.taskEpic.put(number, epic);
    }

    public void updatedTask(Task task) {
        this.taskTask.put(task.getId(), task);
    }

    public void updatedEpic(Epic epic) {
        this.taskEpic.put(epic.getId(), epic);
        setStatusEpic(epic.getId());
    }

    public void updatedSubTask(SubTask subTask) {
        this.taskSubTask.put(subTask.getId(), subTask);
        setStatusEpic(subTask.getIdEpic());
    }

    public Task getTaskById (int number) {
        return taskTask.getOrDefault(number, null);
    }

    public SubTask getSubTaskById (int number) {
        return taskSubTask.getOrDefault(number, null);
    }

    public Epic getEpicById (int number) {
        return taskEpic.getOrDefault(number, null);
    }

    public ArrayList<SubTask> getListSubTasks(int number) {
        if (!(getEpicById(number) == null)) {
            return getEpicById(number).getListSubTask();
        } else {
            System.out.println("Эпика с номером " + number + " не существует!");
            return null;
        }
    }

    public String getStatusById(int number) {
        if (taskTask.containsKey(number)) {
            return taskTask.get(number).getStatus();
        } else if (taskEpic.containsKey(number)) {
            return taskEpic.get(number).getStatus();
        } else if (taskSubTask.containsKey(number)) {
            return taskSubTask.get(number).getStatus();
        }
        else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
            return null;
        }
    }

    public void deleteTaskById (int number) {
        if (taskTask.containsKey(number)) {
            taskTask.remove(number);
        } else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
        }
    }

    public void deleteSubTaskById(int number) {
        if (taskSubTask.containsKey(number)) {
            // удаляем подзадачу у эпика
            int numberEpic = taskSubTask.get(number).getIdEpic();
            taskEpic.get(numberEpic).deleteSubTask(taskSubTask.get(number));
            setStatusEpic(numberEpic);
            taskSubTask.remove(number);
        } else {
            System.out.println("Подзадачи с номером " + number + " нет в списке!");
        }
    }

    public void deleteEpicById(int number) {
        if (taskEpic.containsKey(number)) {
            for (SubTask subTaskEpic : taskEpic.get(number).getListSubTask()) {
                taskSubTask.remove(subTaskEpic.getId());
            }
            taskEpic.remove(number);
        } else {
            System.out.println("Эпика с номером " + number + " нет в списке!");
        }
    }

    public void deleteAllTask() {
        if (!taskTask.isEmpty()) {
            taskTask.clear();
        }
        if (!taskSubTask.isEmpty()) {
            taskSubTask.clear();
        }
        if (!taskEpic.isEmpty()) {
            taskEpic.clear();
        }
        this.id = 0;
    }

    private void setStatusEpic(int idEpic) {
        ArrayList<SubTask> listSubTask = getListSubTasks(idEpic);
        boolean isStatusNew = false;
        boolean isStatusInProgess = false;
        boolean isStatusDone = false;

        for (SubTask list : listSubTask) {
            switch (list.getStatus()) {
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
            taskEpic.get(idEpic).setStatus("DONE");
        } else if (isStatusNew && !isStatusInProgess && !isStatusDone) {
            taskEpic.get(idEpic).setStatus("NEW");
        } else {
            taskEpic.get(idEpic).setStatus("IN_PROGRESS");
        }
    }

    private void addSubTaskInEpic(Epic epic, SubTask subTask) {
        epic.setListSubTask(subTask);
    }
}
