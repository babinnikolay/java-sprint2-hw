package repositories;

import repositories.domain.FileBackedHelper;
import repositories.services.TaskHistoryRepositoryService;
import tasks.AbstractTask;

import java.util.List;

public class FileBackedTaskHistoryRepository extends AbstractTaskHistoryRepository {
    private FileBackedHelper<TaskHistoryRepositoryService> fileBackedHelper;

    public FileBackedTaskHistoryRepository(TaskHistoryRepositoryService service,
                                           FileBackedHelper<TaskHistoryRepositoryService> fileBackedHelper) {
        super(service);
        this.fileBackedHelper = fileBackedHelper;
    }

    @Override
    public void add(AbstractTask task) {
        super.add(task);
        save();
    }

    @Override
    public void remove(int id) {
        super.remove(id);
        save();
    }

    @Override
    public List<AbstractTask> history() {
        return super.history();
    }

    protected void save() {
        fileBackedHelper.save(service);
    }

    public void loadFromPath() {
        service = fileBackedHelper.loadFromPath();
    }

}
