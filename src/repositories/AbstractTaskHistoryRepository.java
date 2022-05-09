package repositories;

import repositories.services.TaskHistoryRepositoryService;
import tasks.AbstractTask;

import java.util.List;

public abstract class AbstractTaskHistoryRepository {
    protected TaskHistoryRepositoryService service;

    public AbstractTaskHistoryRepository(TaskHistoryRepositoryService service) {
        this.service = service;
    }

    public void add(AbstractTask task) {
        service.add(task);
    }

    public void remove(int id) {
        service.remove(id);
    }

    public List<AbstractTask> history() {
        return service.history();
    }
}
