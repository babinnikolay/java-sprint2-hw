package util;

import repositories.HTTPTaskHistoryRepository;
import repositories.HTTPTaskRepository;
import repositories.domain.TaskHistoryList;
import repositories.services.TaskHistoryRepositoryService;
import repositories.services.TaskRepositoryService;
import services.HistoryManager;
import services.HistoryManagerService;
import services.TaskManager;
import services.TaskManagerService;
import services.kv.KVTaskClient;

import java.util.HashMap;

public class Managers {
    public static TaskManager getDefaultTaskManager() {

        TaskRepositoryService taskRepositoryService = new TaskRepositoryService();

        TaskHistoryRepositoryService taskHistoryRepositoryService =
                new TaskHistoryRepositoryService(new TaskHistoryList(new HashMap<>()));

        KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078");
        HTTPTaskRepository taskRepository = new HTTPTaskRepository(taskRepositoryService, kvTaskClient);

        HTTPTaskHistoryRepository taskHistoryRepository =
                new HTTPTaskHistoryRepository(taskHistoryRepositoryService, kvTaskClient);

        HistoryManager historyManager = new HistoryManagerService(taskHistoryRepository);

        return new TaskManagerService(taskRepository, historyManager);
    }

    private Managers() {

    }
}
