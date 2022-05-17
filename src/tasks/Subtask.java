package tasks;

import managers.TypeTask;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(TypeTask type, String name, String description, int epicId) {
        super(type, name, description);
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
                Objects.equals(name, otherTask.name) &&
                Objects.equals(description, otherTask.description) &&
                Objects.equals(status, otherTask.status) &&
                (epicId == otherTask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.name, this.description, this.status, this.epicId);
    }

}
