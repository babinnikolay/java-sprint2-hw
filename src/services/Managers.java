package services;

import repositories.InMemoryTaskHistoryRepository;
import repositories.InMemoryTaskRepository;


public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        return new TaskManagerService(new InMemoryTaskRepository(), getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new HistoryManagerService(new InMemoryTaskHistoryRepository());
    }
}
