package repositories;

import repositories.domain.TaskHistoryList;
import repositories.services.TaskHistoryRepositoryService;
import tasks.AbstractTask;

import java.util.HashMap;
import java.util.List;

public class InMemoryTaskHistoryRepository implements TaskHistoryRepository{
    private final TaskHistoryRepositoryService service;

    public InMemoryTaskHistoryRepository() {
        service = new TaskHistoryRepositoryService(new TaskHistoryList(new HashMap()));
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
