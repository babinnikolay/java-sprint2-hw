package repositories;

import repositories.domain.FileBackedHelper;
import repositories.services.TaskRepositoryService;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class FileBackedTaskRepository extends AbstractTaskRepository {
    private FileBackedHelper<TaskRepositoryService> fileBackedHelper;

    public FileBackedTaskRepository(TaskRepositoryService service,
                                    FileBackedHelper<TaskRepositoryService> fileBackedHelper) {
        super(service);
        this.fileBackedHelper = fileBackedHelper;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    protected void save() {
        fileBackedHelper.save(service);
    }

    public void loadFromPath() {
        service = fileBackedHelper.loadFromPath();
        service.initPrioritizedTasks();
    }
}
