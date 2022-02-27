package services;

import repositories.MemoryTaskHistoryRepository;
import repositories.MemoryTaskRepository;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        return new TaskManagerService(new MemoryTaskRepository());
    }

    public static HistoryManager getDefaultHistory() {
        return new HistoryManagerService(new MemoryTaskHistoryRepository());
    }
}
