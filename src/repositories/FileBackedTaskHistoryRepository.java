package repositories;

import repositories.domain.FileBackedHelper;
import repositories.services.TaskHistoryRepositoryService;
import tasks.AbstractTask;

import java.util.List;

public class FileBackedTaskHistoryRepository implements TaskHistoryRepository{
    private TaskHistoryRepositoryService service;
    private FileBackedHelper<TaskHistoryRepositoryService> fileBackedHelper;

    public FileBackedTaskHistoryRepository(TaskHistoryRepositoryService service,
                                           FileBackedHelper<TaskHistoryRepositoryService> fileBackedHelper) {
        this.service = service;
        this.fileBackedHelper = fileBackedHelper;
    }

    @Override
    public void add(AbstractTask task) {
        service.add(task);
        save();
    }

    @Override
    public void remove(int id) {
        service.remove(id);
        save();
    }

    @Override
    public List<AbstractTask> history() {
        return service.history();
    }

    public void save() {
        fileBackedHelper.save(service);
    }

    public void loadFromPath() {
        service = fileBackedHelper.loadFromPath();
    }

}
