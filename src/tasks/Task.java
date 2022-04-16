package tasks;

import managers.StatusTask;

import java.util.Objects;

public class Task {
    protected int id;
    protected String type;
    protected String title;
    protected String content;
    protected StatusTask status = StatusTask.NEW;

    public Task(String type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public StatusTask getStatus() {
        return status;
    }

    @Override
    public String toString() {
        String result = "(номер задачи= " + id + ", тип задачи = " + type + ", статус= " + status + ", " +
                "название= " + title;
        result = result + ", описание: " + content + ") " + '\n';
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return (id == otherTask.id) &&
                Objects.equals(type, otherTask.type) &&
                Objects.equals(title, otherTask.title) &&
                Objects.equals(content, otherTask.content) &&
                Objects.equals(status, otherTask.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.title, this.content, this.status);
    }

}

