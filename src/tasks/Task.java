package tasks;

import managers.StatusTask;

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
        String result = "(номер задачи= " + id + ", тип задачи = " + type + ", статус= " + status + ", название= " + title;
        result = result + ", описание: " + content + ") " + '\n';
        return result;
    }
}
