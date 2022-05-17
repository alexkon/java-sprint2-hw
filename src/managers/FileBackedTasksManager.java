package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private String fileName;
    CSVSerializator serializator = new CSVSerializator();

    public FileBackedTasksManager(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubTask(Subtask subtask) {
        super.createSubTask(subtask);
        save();
    }

    @Override
    public void updatedTask(Task task) {
        super.updatedTask(task);
        save();
    }

    @Override
    public void updatedEpic(Epic epic) {
        super.updatedEpic(epic);
        save();
    }

    @Override
    public void updatedSubTask(Subtask subTask) {
        super.updatedSubTask(subTask);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubTaskById(int id) {
        Subtask subtask = super.getSubTaskById(id);
        save();
        return subtask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public List<Subtask> getListSubTasks(int id) {
        return super.getListSubTasks(id);
    }

    @Override
    public StatusTask getStatusById(int id) {
        return super.getStatusById(id);
    }

    @Override
    public Map<Integer, Task> getTask() {
        return super.getTask();
    }

    @Override
    public Map<Integer, Epic> getEpic() {
        return super.getEpic();
    }

    @Override
    public Map<Integer, Subtask> getSubTask() {
        return super.getSubTask();
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    public void loadFromFile(String file) {

        int nextId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();

                if (line.isEmpty() || line.isBlank()) {
                    break;
                }
                Task task = serializator.fromString(line);
                int taskId = task.getId();

                switch (task.getType()) {
                    case TASK:
                        tasks.put(taskId, task);
                        break;
                    case EPIC:
                        Epic epic = (Epic) task;
                        epics.put(taskId, epic);
                        break;
                    case SUBTASK:
                        Subtask subtask = (Subtask) task;
                        subtasks.put(taskId, subtask);
                        Epic epicOfSubtask = getEpicById(subtask.getIdEpic());
                        epicOfSubtask.setListSubTask(subtask);
                        break;
                    default:
                        break;
                }

                if (nextId < taskId) {
                    nextId = taskId;
                }
            }

            setId(nextId);

            String lineHistory = br.readLine();
            List<Integer> history = CSVSerializator.fromStringHistory(lineHistory);
            for (int taskId : history) {
                if (tasks.containsKey(taskId)) {
                    historyManager.add(getTaskById(taskId));
                } else if (epics.containsKey(taskId)) {
                    historyManager.add(getEpicById(taskId));
                } else {
                    historyManager.add(getSubTaskById(taskId));
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName, StandardCharsets.UTF_8))) {
            bw.write("id,type,name,status,description,epic" + "\n");
            for (Task task : getAllTasks()) {
                bw.write(serializator.toString(task));
            }
            for (Epic epic : getAllEpics()) {
                bw.write(serializator.toString(epic));
            }
            for (Subtask subtask : getAllSubtasks()) {
                bw.write(serializator.toString(subtask));
            }
            bw.write("\n");
            bw.write(CSVSerializator.toStringHistory(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }
}
