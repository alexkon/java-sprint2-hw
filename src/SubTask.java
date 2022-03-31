public class SubTask extends Epic {
    int idEpic;

    public SubTask (int idEpic, String type, String title, String content, String status) {
        super(type, title, content, status);
        this.idEpic = idEpic;
    }

}
