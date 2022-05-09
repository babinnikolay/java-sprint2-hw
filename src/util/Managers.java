package util;

import repositories.FileBackedTaskHistoryRepository;
import repositories.FileBackedTaskRepository;
import repositories.domain.FileBackedHelper;
import repositories.domain.TaskHistoryList;
import repositories.services.TaskHistoryRepositoryService;
import repositories.services.TaskRepositoryService;
import services.HistoryManager;
import services.HistoryManagerService;
import services.TaskManager;
import services.TaskManagerService;

import java.nio.file.Paths;
import java.util.HashMap;

public class Managers {
    public static TaskManager getDefaultTaskManager() {
        TaskRepositoryService taskRepositoryService = new TaskRepositoryService();

        FileBackedHelper<TaskRepositoryService> fileBackedHelperTasks =
                new FileBackedHelper<>(Paths.get("fileTaskDB.ser"));
        FileBackedHelper<TaskHistoryRepositoryService> fileBackedHelperHistoryTasks =
                new FileBackedHelper<>(Paths.get("fileHistoryTaskDB.ser"));

        TaskHistoryRepositoryService taskHistoryRepositoryService =
                new TaskHistoryRepositoryService(new TaskHistoryList(new HashMap<>()));

        FileBackedTaskRepository taskRepository =
                new FileBackedTaskRepository(taskRepositoryService, fileBackedHelperTasks);
        if (fileBackedHelperTasks.isFileExists()) {
            taskRepository.loadFromPath();
        }

        FileBackedTaskHistoryRepository taskHistoryRepository =
                new FileBackedTaskHistoryRepository(taskHistoryRepositoryService, fileBackedHelperHistoryTasks);
        if (fileBackedHelperHistoryTasks.isFileExists()) {
            taskHistoryRepository.loadFromPath();
        }

        HistoryManager historyManager = new HistoryManagerService(taskHistoryRepository);

        return new TaskManagerService(taskRepository, historyManager);
    }
}
