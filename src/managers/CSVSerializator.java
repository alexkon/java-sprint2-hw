package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class CSVSerializator {

    public static List<Integer> fromStringHistory(String value) {
        List<Integer> history = new ArrayList<>();
        String[] valueId = value.split(",");

        for (String element : valueId) {
            history.add(Integer.valueOf(element));
        }
        return history;
    }

    public static String toStringHistory(HistoryManager historyManager) {
        StringBuilder lineHistory = new StringBuilder("");

        for (Task task : historyManager.getHistory()) {
            lineHistory.append(task.getId());
            lineHistory.append(",");
        }

        if (lineHistory.length() > 0) {
            lineHistory.setLength(lineHistory.length() - 1);
        }
        return lineHistory.toString();
    }

    public Task fromString(String value) {
        String[] elements = value.split(",");

        if (elements[0].equals("id")) {
            return null;
        }

        int id = Integer.parseInt(elements[0]);
        TypeTask type = TypeTask.valueOf(elements[1]);
        String name = elements[2];
        StatusTask status = StatusTask.valueOf(elements[3]);
        String description = elements[4];
        int epicId = 0;

        if (type == TypeTask.SUBTASK) {
            epicId = Integer.parseInt(elements[5]);
        }

        switch (type) {
            case TASK:
                Task task = new Task(type, name, description);
                task.setId(id);
                task.setStatus(status);
                return task;
            case EPIC:
                Epic epic = new Epic(type, name, description);
                epic.setId(id);
                epic.setStatus(status);
                return epic;
            case SUBTASK:
                Subtask subtask = new Subtask(type, name, description, epicId);
                subtask.setId(id);
                subtask.setStatus(status);
                return subtask;
        }
        return null;
    }

    public String toString(Task task) {
        String lineTask = task.getId() + "," + task.getType() + ","
                + task.getName() + "," + task.getStatus() + "," + task.getDescription() + ",";

        if (task.getType() == TypeTask.SUBTASK) {
            Subtask subtask = (Subtask) task;
            lineTask = lineTask + subtask.getIdEpic();
        }
        return lineTask + "\n";
    }

}
