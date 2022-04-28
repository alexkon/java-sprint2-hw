package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String type, String title, String content) {
        super(type, title, content);
        this.subtasks = new ArrayList<>();
    }

    public void setListSubTask(Subtask subTask) {
        this.subtasks.add(subTask);
    }

    public List<Subtask> getListSubTask() {
        return subtasks;
    }

    public void deleteSubTask(Subtask subTask) {
        this.subtasks.remove(subTask);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Epic otherTask = (Epic) obj;
        return (id == otherTask.id) &&
                Objects.equals(type, otherTask.type) &&
                Objects.equals(title, otherTask.title) &&
                Objects.equals(content, otherTask.content) &&
                Objects.equals(status, otherTask.status) &&
                Objects.equals(subtasks, otherTask.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.title, this.content, this.status, this.subtasks);
    }
}
