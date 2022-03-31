import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList <Integer> idSubTask;

    public Epic(String type, String title, String content, String status) {
        super(type, title, content, status);
        this.idSubTask = new ArrayList<>();
    }
}
