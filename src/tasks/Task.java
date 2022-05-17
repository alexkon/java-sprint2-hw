package tasks;

import managers.StatusTask;
import managers.TypeTask;

import java.util.Objects;

public class Task {
    protected int id;
    protected TypeTask type;
    protected String name;
    protected StatusTask status = StatusTask.NEW;
    protected String description;


    public Task(TypeTask type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public TypeTask getType() {
        return type;
    }

    public void setType(TypeTask type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
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
                "название= " + name;
        result = result + ", описание: " + description + ") " + '\n';
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
                Objects.equals(name, otherTask.name) &&
                Objects.equals(description, otherTask.description) &&
                Objects.equals(status, otherTask.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.name, this.description, this.status);
    }

}

