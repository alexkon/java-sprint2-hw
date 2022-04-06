import managers.Manager;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        Manager manager = new Manager();

        // Проверка : Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.

        Task task1 = new Task("task", "Переезд", "Собрать коробки, Упаковать кошку,"
                     + " Сказать слова прощания", "NEW");
        manager.createTask(task1);
        Task task2 = new Task("task", "Спортивный корпоративный праздник",
                    "Создать команду, Купить форму, Выбрать место, Купить призы", "NEW");
        manager.createTask(task2);
        Epic task3 = new Epic("epic", "Юбилей бабушки",
                    "Заказать ресторан, Купить подарок, Подготовить сценарий", "NEW");
        manager.createEpic(task3);
        SubTask task4 = new SubTask("subtask", "Заказать ресторан",
                    "Украсить зал, Придумать меню", "NEW", 3);
        manager.createSubTask(task4);
        SubTask task5 = new SubTask( "subtask", "Подготовить сценарий",
                       "Найти аниматора, Придумать конкурсы", "NEW", 3);
        manager.createSubTask(task5);
        Epic task6 = new Epic("epic", "Поездка в отпуск",
                    "Выбрать место отдыха, Собрать вещи", "NEW");
        manager.createEpic(task6);
        SubTask task7 = new SubTask("subtask", "Выбрать место отдыха",
                    "Забронировать гостиницу, Купить билеты", "NEW",6);
        manager.createSubTask(task7);

        // печать объектов по идентификатору:
        int numberTask = 6;
        System.out.println("Задача № "+ numberTask + ": " + manager.getEpicById(numberTask));
        numberTask = 2;
        System.out.println("Задача № "+ numberTask + ": " + manager.getTaskById(numberTask));
        numberTask = 7;
        System.out.println("Задача № "+ numberTask + ": " + manager.getSubTaskById(numberTask));

        // получение списка задач эпика № 3 по Epic.idSubTask:
        System.out.println("У Эпика 3 следующие подзадачи:" + '\n' + manager.getListSubTasks(3));
        // получение списка задач эпика № 6 по SubTask.idEpic:
        System.out.println("У Эпика 6 следующие подзадачи:" + '\n' + manager.getListSubTasks(6));

        // изменить статусы задач по идентификатору:
        // простая задача:
        numberTask = 1;
        System.out.println("Текущий статус Задачи № "+ numberTask + ": " + manager.getStatusById(numberTask));
        task1.setStatus("DONE");
        manager.updatedTask(task1);
        System.out.println("Новый статус Задачи № "+ numberTask + ": " + manager.getStatusById(numberTask));

        // подзадача:
        numberTask = 4;
        System.out.println("Текущий статус Задачи № "+ numberTask + ": " + manager.getStatusById(numberTask));
        task4.setStatus("DONE");
        manager.updatedSubTask(task4);
        System.out.println("Новый статус подзадачи № "+ numberTask + ": " + manager.getStatusById(numberTask));
        System.out.println("Новый статус Эпика № "+ (numberTask - 1)
                + ": " + manager.getStatusById(numberTask-1));

        numberTask = 5;
        System.out.println("Текущий статус Задачи № "+ numberTask + ": " + manager.getStatusById(numberTask));
        task5.setStatus("NEW");
        manager.updatedSubTask(task5);
        System.out.println("Новый статус подзадачи № "+ numberTask + ": " + manager.getStatusById(numberTask));
        System.out.println("Новый статус Эпика № 3: " + manager.getStatusById(3));
        task3.setContent("Заказать ресторан, Купить подарок, Подготовить сценарий, Составить список гостей");
        manager.updatedEpic(task3);
        System.out.println("Эпик № 3: " + manager.getEpicById(3));
        // удаление задач
        manager.deleteTaskById(1);
        System.out.println("Осталось задач - " + manager.getTask().size());
        manager.deleteSubTaskById(4);
        System.out.println("Новый статус Эпика № 3: " + manager.getStatusById(3));
        manager.deleteEpicById(3);
        System.out.println("Осталось эпиков - " + manager.getEpic().size());
        System.out.println("Осталось подзадач - " + manager.getSubTask());
        manager.deleteAllTask();
        System.out.println("Осталось задач - " + manager.getTask().size());
        System.out.println("Осталось эпиков - " + manager.getEpic().size());
        System.out.println("Осталось подзадач - " + manager.getSubTask().size());
    }
}
