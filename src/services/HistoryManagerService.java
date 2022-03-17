package services;

import repositories.TaskHistoryRepository;
import tasks.AbstractTask;

import java.util.List;

public class HistoryManagerService implements HistoryManager {

    private final TaskHistoryRepository historyRepository;

    public HistoryManagerService(TaskHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void add(AbstractTask task) {
        historyRepository.add(task);
    }

    @Override
    public void remove(int id) {
        historyRepository.remove(id);
    }

    @Override
    public List<AbstractTask> getHistory() {
        return historyRepository.history();
    }
}
