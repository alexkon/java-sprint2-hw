package tasks;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList <SubTask> listSubTask;

    public Epic(String type, String title, String content, String status) {
        super(type, title, content, status);
        this.listSubTask = new ArrayList<>();
    }

    public void setListSubTask(SubTask subTask) {
        this.listSubTask.add(subTask);
    }

    public ArrayList<SubTask> getListSubTask() {
        return listSubTask;
    }

    public void deleteSubTask(SubTask subTask) {
        this.listSubTask.remove(subTask);
    }
}
