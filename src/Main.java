public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        Manager manager = new Manager();

        // Проверка : Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
        // создание 2-х задач
        Task task1 = new Task("task", "Переезд", "Собрать коробки, Упаковать кошку,"
                + " Сказать слова прощания", "NEW");
        manager.createTask(task1);
        Task task2 = new Task("task", "Спортивный корпоративный праздник",
                "Создать команду, Купить форму, Выбрать место, Купить призы", "NEW");
        manager.createTask(task2);

        // создание эпика с 2-мя подзадачами
        Epic task3 = new Epic("epic", "Юбилей бабушки",
                "Заказать ресторан, Купить подарок, Подготовить сценарий", "NEW");
        manager.createTask(task3);

        SubTask task4 = new SubTask(3, "subtask", "Заказать ресторан",
                "Украсить зал, Придумать меню", "NEW");
        manager.createTask(task4);
        manager.setIdSubTaskForEpic(3,4);
        SubTask task5 = new SubTask(3, "subtask", "Подготовить сценарий",
                "Найти аниматора, Придумать конкурсы", "NEW");
        manager.createTask(task5);
        manager.setIdSubTaskForEpic(3,5);
        // создание эпика с 1-й подзадачей
        Epic task6 = new Epic("epic", "Поездка в отпуск",
                "Выбрать место отдыха, Собрать вещи", "NEW");
        manager.createTask(task6);

        SubTask task7 = new SubTask(6, "subtask", "Выбрать место отдыха",
                "Забронировать гостиницу, Купить билеты", "NEW");
        manager.createTask(task7);

        // Задание: печать списка задач, эпиков, подзадач
        System.out.println(manager.toString());

        // печать объектов по идентификатору:
        int numberTask = 6;
        System.out.println("Задача № "+ numberTask + ": " + manager.getTaskById(numberTask));
        numberTask = 2;
        System.out.println("Задача № "+ numberTask + ": " + manager.getTaskById(numberTask));

        // получение списка задач эпика № 3 по Epic.idSubTask:
        System.out.println("У Эпика следующие задачи:" + '\n' + manager.getListTasksEpic1(3));
        // получение списка задач эпика № 6 по SubTask.idEpic:
        System.out.println("У Эпика следующие задачи:" + '\n' + manager.getListTasksEpic(6));

        // изменить статусы задач по идентификатору:
        // простая задача:
        numberTask = 1;
        System.out.println("Текущий статус Задачи № "+ numberTask + ": " + manager.getStatusById(numberTask));
        Task task8 = new Task("task", "Переезд", "Собрать коробки, Упаковать кошку,"
                + " Сказать слова прощания", "DONE");
        manager.setUpdatedTask(numberTask, task8);
        System.out.println("Новый статус Задачи № "+ numberTask + ": " + manager.getStatusById(numberTask));

        // подзадача:
        numberTask = 4;
        System.out.println("Текущий статус Задачи № "+ numberTask + ": " + manager.getStatusById(numberTask));
        SubTask task9 = new SubTask(3, "subtask", "Заказать ресторан",
                "Украсить зал, Придумать меню", "DONE");
        manager.setUpdatedTask(numberTask, task9);
        System.out.println("Новый статус подзадачи № "+ numberTask + ": " + manager.getStatusById(numberTask));
        System.out.println("Новый статус Эпика № "+ (numberTask - 1)
                + ": " + manager.getStatusById(numberTask-1));
        // удаление задач
        manager.deleteTaskById(1);
        System.out.println("Осталось задач - " + manager.objectTask.size());
        manager.deleteTaskById(3);
        System.out.println("Осталось задач - " + manager.objectTask.size());
        manager.deleteAllTask();
        System.out.println("Осталось задач - " + manager.objectTask.size());
    }
}
