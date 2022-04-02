import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int id = 0;
    public HashMap<Integer, Task> task = new HashMap<>();
    public HashMap<Integer, Epic> epic = new HashMap<>();
    public HashMap<Integer, SubTask> subTask = new HashMap<>();

    public void createTask(Task task) {
        this.id++;
        this.task.put(id, task);
    }

    public void createSubTask(SubTask subTask) {
        this.id++;
        this.subTask.put(id, subTask);
        setIdSubTaskForEpic(subTask.getIdEpic(), id);
        setStatusEpic(subTask.getIdEpic());
    }

    public void createEpic(Epic epic) {
        this.id++;
        this.epic.put(id, epic);
    }

    // обновление информации по задачам и статусам
    public void setUpdatedTask(int number, Task task) {
        Task taskUpdated = task;
        this.task.put(number, taskUpdated);
    }

    public void setUpdatedEpicTask(int number, Epic epic) {
        Epic epicUpdated = epic;
        this.epic.put(number, epicUpdated);
        setStatusEpic(number);
    }

    public void setUpdatedSubTask(int number, SubTask subTask) {
        SubTask subTaskUpdated = subTask;
        this.subTask.put(number, subTaskUpdated);
        setStatusEpic(subTaskUpdated.getIdEpic());
    }

    public void setIdSubTaskForEpic (int idEpic, int idSubTask) {
        Epic epicBySubTask = getEpicById(idEpic);
        epicBySubTask.setIdSubTask(idSubTask);
    }

    public Task getTaskById (int number) {
        if (task.containsKey(number)) {
            return task.get(number);
        } else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
            return null;
        }
    }

    public SubTask getSubTaskById (int number) {
        if (subTask.containsKey(number)) {
            return subTask.get(number);
        } else {
            System.out.println("Подзадачи с номером " + number + " нет в списке!");
            return null;
        }
    }

    public Epic getEpicById (int number) {
        if (epic.containsKey(number)) {
            return epic.get(number);
        } else {
            System.out.println("Эпика с номером " + number + " нет в списке!");
            return null;
        }
    }

    private void setStatusEpic(int idEpic) {
        ArrayList<SubTask> listSubTask = getListSubTasks(idEpic);
        boolean isStatusNew = false;
        boolean isStatusInProgess = false;
        boolean isStatusDone = false;

        for (SubTask list : listSubTask) {
            if (list.getIdEpic() == idEpic) {
                switch (list.status) {
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
        }

        if (!isStatusNew && !isStatusInProgess && isStatusDone) {
            epic.get(idEpic).status = ("DONE");
        } else if (isStatusNew && isStatusInProgess && !isStatusDone) {
            epic.get(idEpic).status = ("NEW");
        } else {
            epic.get(idEpic).status = ("IN_PROGRESS");
        }
    }

    public ArrayList getListSubTasks(int number) {
        if (epic.containsKey(number)) {
            Epic epic = getEpicById(number);
            ArrayList<SubTask> subTasksEpic = new ArrayList<>();
            int k = 0;
            for (int idSubTaskEpic : epic.getIdSubTask()) {
                SubTask subTask = getSubTaskById(idSubTaskEpic);
                subTasksEpic.add(k, subTask);
                k++;
            }
            return subTasksEpic;
        } else {
            System.out.println("Эпика с номером " + number + " не существует!");
            return null;
        }
    }

    public String getStatusById(int number) {
        if (task.containsKey(number)) {
            return task.get(number).status;
        } else if (epic.containsKey(number)) {
            return epic.get(number).status;
        } else if (subTask.containsKey(number)) {
            return subTask.get(number).status;
        }
        else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
            return null;
        }
    }

    public void deleteTaskById (int number) {
        if (task.containsKey(number)) {
            task.remove(number);
        } else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
        }
    }

    public void deleteSubTaskById(int number) {
        if (subTask.containsKey(number)) {
            // удаляем подзадачу и ее id у эпика
            int idEpic = subTask.get(number).getIdEpic();
            Integer idSubTaskValue = number;
            epic.get(idEpic).getIdSubTask().remove(idSubTaskValue);
            subTask.remove(number);
            setStatusEpic(idEpic);
        } else {
            System.out.println("Подзадачи с номером " + number + " нет в списке!");
        }
    }

    public void deleteEpicById(int number) {
        if (epic.containsKey(number)) {
            for (int idSubTask : epic.get(number).getIdSubTask()) {
                subTask.remove(idSubTask);
            }
            epic.remove(number);
        } else {
            System.out.println("Эпика с номером " + number + " нет в списке!");
        }
    }

    public void deleteAllTask() {
        if ( !task.isEmpty() ) {
            task.clear();
        }
        if ( !subTask.isEmpty() ) {
            subTask.clear();
        }
        if ( !epic.isEmpty() ) {
            epic.clear();
        }
        this.id = 0;
    }
}
