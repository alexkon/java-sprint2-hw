package tasks;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String type, String title, String content, int epicId) {
        super(type, title, content);
        this.epicId = epicId;
    }

    public int getIdEpic() {
        return epicId;
    }

    public void setIdEpic(int idEpic) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Subtask otherTask = (Subtask) obj;
        return (id == otherTask.id) &&
                Objects.equals(type, otherTask.type) &&
                Objects.equals(title, otherTask.title) &&
                Objects.equals(content, otherTask.content) &&
                Objects.equals(status, otherTask.status) &&
                (epicId == otherTask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.title, this.content, this.status, this.epicId);
    }
}
