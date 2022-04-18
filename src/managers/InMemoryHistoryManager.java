package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>();
    private final int historyMaxLength = 10;

    @Override
    public void add(Task task) {
        if (task != null) {
            if (history.size() == 10) {
                history.remove(0);
            }
            this.history.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return this.history;
    }
}
