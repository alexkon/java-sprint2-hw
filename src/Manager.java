import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    static int id = 0;
    HashMap<Integer, Task> objectTask = new HashMap<>();

    public void createTask(Object obj) {
        this.id++;
        if (obj.getClass() == Task.class) {
            Task task = (Task) obj;
            objectTask.put(this.id, task);
        }
        if (obj.getClass() == Epic.class) {
            Epic task = (Epic) obj;
            objectTask.put(this.id, task);
        }
        if (obj.getClass() == SubTask.class) {
            SubTask task = (SubTask) obj;
            objectTask.put(this.id, task);
        }
    }

    public void setUpdatedTask(int number, Object obj) {
        // обновление информации по задачам и статусам
        if (obj.getClass() == Task.class) {
            Task task = (Task) obj;
            objectTask.put(number, task);
        }
        if (obj.getClass() == Epic.class) {
            Epic task = (Epic) obj;
            objectTask.put(number, task);
        }
        if (obj.getClass() == SubTask.class) {
            SubTask task = (SubTask) obj;
            objectTask.put(number, task);
            // обновили статус у subtask - устанавливаем статус эпика
            setStatusEpic(task.idEpic);
        }
    }
    protected void setIdSubTaskForEpic (int idEpic, int idSubTask) {
        Task task = objectTask.get(idEpic);
        Epic epic = (Epic) task;
        epic.idSubTask.add(idSubTask);
    }

    public void setStatusEpic(int idEpic) {
        ArrayList<SubTask> listSubTask = getListTasksEpic(idEpic);
        boolean isStatusNew = false;
        boolean isStatusInProgess = false;
        boolean isStatusDone = false;

        for (SubTask list : listSubTask) {
            if (list.idEpic == idEpic) {
                if (list.status.equals("NEW")) {
                    isStatusNew = true;
                } else if (list.status.equals("IN_PROGRESS")) {
                    isStatusInProgess = true;
                } else if (list.status.equals("DONE")) {
                    isStatusDone = true;
                }
            }
        }

        if (!isStatusNew && !isStatusInProgess && isStatusDone) {
            objectTask.get(idEpic).status = ("DONE");
        } else if (isStatusNew && isStatusInProgess && !isStatusDone) {
            objectTask.get(idEpic).status = ("NEW");
        } else {
            objectTask.get(idEpic).status = ("IN_PROGRESS");
        }
    }

    public ArrayList getListTasksEpic(int number) {
        // возвращает список подзадач эпика по SubTask.idEpic
        if ((objectTask.containsKey(number)) && (getTaskById(number).getClass() == Epic.class)) {
            ArrayList<SubTask> subTasksEpic = new ArrayList<>();
            int k = 0;
            for (int i = 1; i <= objectTask.size(); i++) {
                if (getTaskById(i).getClass() == SubTask.class) {
                    SubTask subTask = (SubTask) getTaskById(i);
                    if (subTask.idEpic == number) {
                        subTasksEpic.add(k, subTask);
                        k++;
                    }
                }
            }
            return subTasksEpic;
        } else {
            System.out.println("Эпика с номером " + number + " не существует!");
            return null;
        }
    }

    public ArrayList getListTasksEpic1(int number) {
        // возвращает список подзадач эпика по Epic.idSubTask
        if ((objectTask.containsKey(number)) && (getTaskById(number).getClass() == Epic.class)) {
            Epic epic = (Epic) getTaskById(number);
            ArrayList<SubTask> subTasksEpic = new ArrayList<>();
            int k = 0;
            for (int idSubTaskEpic : epic.idSubTask) {
                SubTask subTask = (SubTask) getTaskById(idSubTaskEpic);
                subTasksEpic.add(k, subTask);
                k++;
            }
            return subTasksEpic;
        } else {
            System.out.println("Эпика с номером " + number + " не существует!");
            return null;
        }
    }

    public Object getTaskById(int number) {
        // возвращает задачу по идентификатору
        if (objectTask.containsKey(number)) {
            return objectTask.get(number);
        } else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
            return null;
        }
    }

    public String getStatusById(int number) {
        // получить статус задачи по номеру
        if (objectTask.containsKey(number)) {
            return objectTask.get(number).status;
        } else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
            return null;
        }
    }
    @Override
    public String toString() {
        String result = "(Всего задач " + id + ": " + '\n' + " " + objectTask.toString() + '\n';
        return result;
    }

    public void deleteTaskById (int number) {
        if (objectTask.containsKey(number)) {
            // простая задача
            Task task = objectTask.get(number);
            if (task.getClass() == Task.class) {
                objectTask.remove(number);
            }
            if (task.getClass() == SubTask.class) {
                // удаляем подзадачу и ее id у эпика
                SubTask subTask = (SubTask) task;
                Task epic = objectTask.get(subTask.idEpic);
                Epic epicBySubtask = (Epic) epic;
                epicBySubtask.idSubTask.remove(number);
            }
            if (task.getClass() == Epic.class) {
                // удаляем эпик и все подзадачи
                Epic epic = (Epic) task;
                for (int idSubTask : epic.idSubTask) {
                    objectTask.remove(idSubTask);
                }
                objectTask.remove(number);
            }
        } else {
            System.out.println("Задачи с номером " + number + " нет в списке!");
        }
    }

    public void deleteAllTask() {
        if ( !objectTask.isEmpty() ) {
            objectTask.clear();
            this.id = 0;
        }
    }
}
