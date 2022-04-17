package managers;

import tasks.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            this.history.add(task);
            System.out.println("История запросов: " + history); // для проверки
        }
    }

    @Override
    public List<Task> getHistory() {
        return this.history;
    }
}
