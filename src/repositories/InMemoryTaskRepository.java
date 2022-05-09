package repositories;

import repositories.services.TaskRepositoryService;

public class InMemoryTaskRepository extends AbstractTaskRepository {
    public InMemoryTaskRepository(TaskRepositoryService service) {
        super(service);
    }
}
