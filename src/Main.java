import managers.*;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        InMemoryTaskManager manager = new InMemoryTaskManager();

        // Проверка : Создайте 2 задачи, один эпик с 3 подзадачами, а другой эпик без подзадач.
        Task task1 = new Task("task", "Переезд", "Собрать коробки, Упаковать кошку,"
                + " Сказать слова прощания");
        manager.createTask(task1);
        Task task2 = new Task("task", "Спортивный корпоративный праздник",
                "Создать команду, Купить форму, Выбрать место, Купить призы");
        manager.createTask(task2);

        Epic task3 = new Epic("epic", "Юбилей бабушки",
                "Заказать ресторан, Купить подарок, Подготовить сценарий");
        manager.createEpic(task3);

        Subtask task4 = new Subtask("subtask", "Заказать ресторан",
                "Украсить зал, Придумать меню", 3);
        manager.createSubTask(task4);
        Subtask task5 = new Subtask("subtask", "Подготовить сценарий",
                "Найти аниматора, Придумать конкурсы", 3);
        manager.createSubTask(task5);
        Subtask task6 = new Subtask("subtask", "Купить подарок",
                "Заказать подарок, организовать доставку", 3);
        manager.createSubTask(task6);
        Epic task7 = new Epic("epic", "Поездка в отпуск",
                "Выбрать место отдыха, Собрать вещи");
        manager.createEpic(task7);

        System.out.println("История запросов: " + manager.getHistory());
        // печать задач по идентификатору:
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
        manager.deleteSubTaskById(4);
        System.out.println("История запросов: " + manager.getHistory());
        manager.deleteEpicById(3);
        System.out.println("История запросов: " + manager.getHistory());
    }
}
