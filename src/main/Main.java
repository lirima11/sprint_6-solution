package src.main;


import main.TaskStatus;
import src.manager.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Задача 1", "Описание задачи 1", TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", TaskStatus.NEW);
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW, epic1.getId());
        manager.createSubtask(subtask1);

        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Список задач:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Список эпиков:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);
            for (Subtask subtask : manager.getAllSubtasks()) {
                if (subtask.getEpicId() == epic.getId()) {
                    System.out.println("--> " + subtask);
                }
            }
        }

        System.out.println("История просмотров задач:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
