package repositories;

import repositories.services.TaskHistoryRepositoryService;

public class InMemoryTaskHistoryRepository extends AbstractTaskHistoryRepository {
    public InMemoryTaskHistoryRepository(TaskHistoryRepositoryService service) {
        super(service);
    }
}
