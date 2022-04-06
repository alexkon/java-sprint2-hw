package tasks;

public class SubTask extends Task {
        private int idEpic;

        public SubTask (String type, String title, String content, String status, int idEpic) {
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
