import java.util.ArrayList;

public class Task {
    protected String type;
    protected String title;
    protected String content;
    protected String status;

    public Task(String type, String title, String content, String status) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        String result = "(тип задачи = " + type + ", статус= " + status + ", название= " + title;
        result = result + ", описание:" + content +") " +'\n';
        return result;
    }
}

class Epic extends Task {

    private ArrayList<Integer> idSubTask;

    public Epic(String type, String title, String content, String status) {
        super(type, title, content, status);
        this.idSubTask = new ArrayList<>();
    }

    public void setIdSubTask(int idSubTask) {
        this.idSubTask.add(idSubTask);
    }

    public ArrayList<Integer> getIdSubTask() {
        return idSubTask;
    }
}

class SubTask extends Epic {
    private int idEpic;

    public SubTask (int idEpic, String type, String title, String content, String status) {
        super(type, title, content, status);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }
}
