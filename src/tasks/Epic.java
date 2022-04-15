package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> listSubTask;

    public Epic(String type, String title, String content) {
        super(type, title, content);
        this.listSubTask = new ArrayList<>();
    }

    public void setListSubTask(SubTask subTask) {
        this.listSubTask.add(subTask);
    }

    public ArrayList<SubTask> getListSubTask() {
        return listSubTask;
    }

    public void updatedListSubTask(SubTask subTask) {
        int indexSubTask = this.listSubTask.indexOf(subTask);
        this.listSubTask.set(indexSubTask, subTask);
    }

    public void deleteSubTask(SubTask subTask) {
        this.listSubTask.remove(subTask);
    }
}
