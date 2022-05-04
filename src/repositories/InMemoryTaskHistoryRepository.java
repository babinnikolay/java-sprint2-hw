package repositories;

import repositories.services.TaskHistoryRepositoryService;
import tasks.AbstractTask;

import java.util.List;

public class InMemoryTaskHistoryRepository implements TaskHistoryRepository{
    private final TaskHistoryRepositoryService service;

    public InMemoryTaskHistoryRepository(TaskHistoryRepositoryService service) {
        this.service = service;
    }

    @Override
    public void add(AbstractTask task) {
        service.add(task);
    }

    @Override
    public void remove(int id) {
        service.remove(id);
    }

    @Override
    public List<AbstractTask> history() {
        return service.history();
    }

}
