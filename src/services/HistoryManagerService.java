package services;

import repositories.AbstractTaskHistoryRepository;
import tasks.AbstractTask;

import java.util.List;

public class HistoryManagerService implements HistoryManager {

    //>>Для чего абстрактный класс вместо интерфейса?
    //за компанию с AbstractTaskRepository
    private final AbstractTaskHistoryRepository historyRepository;

    public HistoryManagerService(AbstractTaskHistoryRepository historyRepository) {
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
