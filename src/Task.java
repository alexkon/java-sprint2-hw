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
