package util;

import repositories.FileBackedTaskHistoryRepository;
import repositories.FileBackedTaskRepository;
import services.HistoryManager;
import services.HistoryManagerService;
import services.TaskManager;
import services.TaskManagerService;
import tasks.AbstractTask;

public class FileBackedTaskRepositoryTester {

    public static void main(String[] args) {

        FileBackedTaskRepository taskRepository = new FileBackedTaskRepository();
        taskRepository.loadFromPath();

        FileBackedTaskHistoryRepository historyRepository = new FileBackedTaskHistoryRepository();
        historyRepository.loadFromPath();

        HistoryManager historyManager = new HistoryManagerService(historyRepository);
        TaskManager taskManager = new TaskManagerService(taskRepository, historyManager);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTasks());

        printHistory(historyManager);

    }

    private static void printHistory(HistoryManager historyManager) {
        System.out.println("***********************");
        for (AbstractTask task : historyManager.getHistory()) {
            System.out.println(task);
        }
    }
}
