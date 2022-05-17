import managers.*;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        FileBackedTasksManager manager = new FileBackedTasksManager("resources/tasks.csv");
        manager.loadFromFile("resources/tasks.csv");

        // Файл "resources/tasks.csv" содержит следуюшие задачи и историю:

        /*Task task1 = new Task(TypeTask.TASK, "Переезд", "Собрать коробки; Упаковать кошку;"
                + " Сказать слова прощания");
        manager.createTask(task1);
        Task task2 = new Task(TypeTask.TASK, "Спортивный корпоративный праздник",
                "Создать команду; Купить форму; Выбрать место; Купить призы");
        manager.createTask(task2);

        Epic task3 = new Epic(TypeTask.EPIC, "Юбилей бабушки",
                "Заказать ресторан; Купить подарок; Подготовить сценарий");
        manager.createEpic(task3);

        Subtask task4 = new Subtask(TypeTask.SUBTASK, "Заказать ресторан",
                "Украсить зал; Придумать меню", 3);
        manager.createSubTask(task4);
        Subtask task5 = new Subtask(TypeTask.SUBTASK, "Подготовить сценарий",
                "Найти аниматора; Согласовать конкурсы", 3);
        manager.createSubTask(task5);
        Subtask task6 = new Subtask(TypeTask.SUBTASK, "Купить подарок",
                "Заказать подарок; организовать доставку", 3);
        manager.createSubTask(task6);
        Epic task7 = new Epic(TypeTask.EPIC, "Поездка в отпуск",
                "Выбрать место отдыха; Собрать вещи");
        manager.createEpic(task7);*/

        // Загрузили из файла:
        System.out.println("Все таски: " + manager.getAllTasks());
        System.out.println("Все эпики: " + manager.getAllEpics());
        System.out.println("Все субтаски: " + manager.getAllSubtasks());

        System.out.println("История запросов: " + manager.getHistory());

        // Далее происходят следующие изменения и формируется новый файл "resources/tasks.csv":
        System.out.println("Задача № " + 3 + ": " + manager.getEpicById(3));
        System.out.println("Задача № " + 2 + ": " + manager.getTaskById(2));
        System.out.println("Задача № " + 7 + ": " + manager.getEpicById(7));
        System.out.println("Задача № " + 4 + ": " + manager.getSubTaskById(4));
        System.out.println("Задача № " + 4 + ": " + manager.getSubTaskById(4));
        System.out.println("Задача № " + 5 + ": " + manager.getSubTaskById(5));
        System.out.println("Задача № " + 1 + ": " + manager.getTaskById(1));
        System.out.println("Задача № " + 2 + ": " + manager.getTaskById(2));
        System.out.println("Задача № " + 6 + ": " + manager.getSubTaskById(6));
        System.out.println("История запросов: " + manager.getHistory());
        // удаление задач
        manager.deleteTaskById(1);
        System.out.println("У Эпика 3 следующие подзадачи:" + '\n' + manager.getListSubTasks(3));
        manager.deleteSubTaskById(4);
        System.out.println("История запросов: " + manager.getHistory());
        System.out.println("У Эпика 3 следующие подзадачи:" + '\n' + manager.getListSubTasks(3));
        manager.deleteEpicById(3);
        System.out.println("История запросов: " + manager.getHistory());
    }
}
