package util;

import services.HistoryManager;
import services.Managers;
import services.TaskManager;
import tasks.AbstractTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class TaskHistoryTester {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = taskManager.getHistoryManager();
        Task task1 = new Task("task1", "");
        Task task2 = new Task("task2", "");
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("epic3", "");
        Epic epic2 = new Epic("epic4", "");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        for (int i = 1; i <= 3; i++) {
            SubTask subTask = new SubTask("subTask" + (i + 4), "", epic1);
            taskManager.createSubTask(subTask);
        }

        taskManager.getTaskById(2);
        taskManager.getTaskById(1);
        taskManager.getEpicById(4);
        taskManager.getEpicById(3);

        printHistory(historyManager);

        taskManager.getSubTaskById(7);
        taskManager.getSubTaskById(5);

        printHistory(historyManager);

        taskManager.getSubTaskById(6);
        taskManager.getSubTaskById(5);
        taskManager.getSubTaskById(7);

        printHistory(historyManager);

        taskManager.deleteTaskById(2);

        printHistory(historyManager);

        taskManager.deleteEpicById(3);

        printHistory(historyManager);

    }
    private static void printHistory(HistoryManager historyManager) {
        System.out.println("***********************");
        for (AbstractTask task : historyManager.getHistory()) {
            System.out.println(task);
        }
    }
}
