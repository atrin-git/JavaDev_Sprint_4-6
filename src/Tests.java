import java.util.List;

public class Tests {

    private final List<Task> testTaskList = List.of(
            new Task(1, "Ответить на письма"),
            new Task(2, "Отправить посылку по почте")
    );

    private final List<Epic> testEpicList = List.of(
            new Epic(3, "Сделать уборку"),
            new Epic(4, "Пройти спринт на Практикуме")
    );

    private final List<Subtask> testSubtaskList = List.of(
            new Subtask(5, "Вытереть пыль", testEpicList.get(0).getId()),
            new Subtask(6, "Помыть полы", testEpicList.get(0).getId()),
            new Subtask(7, "Вымыть сантехнику", testEpicList.get(0).getId()),
            new Subtask(8, "Вынести мусор", testEpicList.get(0).getId()),
            new Subtask(9, "Пройти теорию", testEpicList.get(1).getId()),
            new Subtask(10, "Посетить вебинар", testEpicList.get(1).getId()),
            new Subtask(11, "Выполнить практику", testEpicList.get(1).getId())
    );

    public void testAdd(TaskManager taskManager) {
        System.out.println("1. Добавление задач, эпиков и подзадач.");
        for (Task task : testTaskList) {
            taskManager.addTask(task);
        }

        for (Epic epic : testEpicList) {
            taskManager.addEpic(epic);
        }

        for (Subtask subtask : testSubtaskList) {
            taskManager.addSubtask(subtask);
        }
        System.out.println("Добавлено: " + taskManager.getTaskList().size() + " задач, " + taskManager.getEpicList().size() + " эпиков, " + taskManager.getSubtaskList().size() + " подзадач\n");
    }

    public void testEdit(TaskManager taskManager) {
        System.out.println("2. Редактирование задач, эпиков и подзадач.");
        taskManager.editTask(new Task(testTaskList.get(1).getId(), "Отправить посылку через СДЭК"));
        taskManager.editEpic(new Epic(testEpicList.get(1).getId(), "Пройти спринт на Практикуме", "5 спринт"));
        taskManager.editSubtask(new Subtask(testSubtaskList.get(3).getId(), "Вынести мусор из всех комнат", "Кабинет, кухня, ванная", testSubtaskList.get(3).getEpicId()));
        System.out.println();
    }

    public void testGetTasks(TaskManager taskManager) {
        System.out.println("Перечень всех задач в таск-менеджере:");
        System.out.println("Задачи: " + taskManager.getTaskList().toString());
        System.out.println("Эпики: " + taskManager.getEpicList().toString());
        System.out.println("Подзадачи: " + taskManager.getSubtaskList().toString());
        System.out.println();
    }

    public void updateStatuses(TaskManager taskManager) {
        System.out.println("3. Обновление статусов задач, эпиков и подзадач.");

        System.out.println("3.1. Задача");
        System.out.println(testTaskList.get(1) + "\n");

        System.out.println(Status.TO_DO + " -> " + Status.IN_PROGRESS);
        Task taskToProgress = new Task(testTaskList.get(1));
        taskToProgress.setStatus(Status.IN_PROGRESS);
        taskManager.editTask(taskToProgress);
        System.out.println("Текущий статус таски: " + taskManager.getTaskById(2).getStatus().toString());

        System.out.println(Status.IN_PROGRESS + " -> " + Status.DONE);
        Task taskToDone = new Task(testTaskList.get(1));
        taskToDone.setStatus(Status.DONE);
        taskManager.editTask(taskToDone);
        System.out.println("Текущий статус таски: " + taskManager.getTaskById(2).getStatus().toString());

        System.out.println();

        System.out.println("3.2. Эпики + подзадачи");
        System.out.println("Эпик: " + testEpicList.get(1));
        System.out.println("Подзадачи: " + testSubtaskList.get(4) + "\n" + testSubtaskList.get(5) + "\n" + testSubtaskList.get(6) + "\n");

        System.out.println(Status.TO_DO + " -> " + Status.IN_PROGRESS);
        updateSubtasksToCustomStatus(taskManager,Status.IN_PROGRESS, testSubtaskList.get(4), testSubtaskList.get(5), testSubtaskList.get(6));
        System.out.println("Текущий статус эпика: " + taskManager.getEpicById(4).getStatus().toString() + "\n");

        System.out.println(Status.IN_PROGRESS + " -> " + Status.DONE);
        updateSubtasksToCustomStatus(taskManager, Status.DONE, testSubtaskList.get(4), testSubtaskList.get(5), testSubtaskList.get(6));
        System.out.println("Текущий статус эпика: " + taskManager.getEpicById(4).getStatus().toString() + "\n");

        System.out.println(Status.DONE + " -> " + Status.IN_PROGRESS);
        updateSubtasksToCustomStatus(taskManager,Status.TO_DO, testSubtaskList.get(4));
        System.out.println("Текущий статус эпика: " + taskManager.getEpicById(4).getStatus().toString());

        System.out.println();
    }

    private void updateSubtasksToCustomStatus(TaskManager taskManager, Status status, Subtask... subtaskList) {
        for (Subtask subtask : subtaskList) {
            Subtask tempSubtask = new Subtask(subtask);
            tempSubtask.setStatus(status);
            taskManager.editSubtask(tempSubtask);

            System.out.println("Для подзадачи с номером " + subtask.getId() + " из эпика " + subtask.getEpicId() +
                    " установлен статус " + status);
        }
    }

    public void testDelete(TaskManager taskManager) {
        System.out.println("4. Удаление задач, эпиков и подзадач.");

        taskManager.deleteTaskById(1);
        taskManager.deleteSubtaskById(5);
        taskManager.deleteEpicById(4);
        taskManager.deleteAllTasks();
        taskManager.deleteAllSubtasks();
        taskManager.deleteAllEpics();
    }
}