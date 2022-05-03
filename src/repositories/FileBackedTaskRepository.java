package repositories;

import repositories.domain.FileBackedHelper;
import repositories.services.TaskRepositoryService;
import tasks.*;

import java.util.List;
import java.util.Set;

public class FileBackedTaskRepository implements TaskRepository{
    private TaskRepositoryService service;
    private FileBackedHelper fileBackedHelper;

    public FileBackedTaskRepository(TaskRepositoryService service, FileBackedHelper fileBackedHelper) {
        this.service = service;
        this.fileBackedHelper = fileBackedHelper;
    }

    @Override
    public int getNextId() {
        return service.getNexId();
    }

    @Override
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @Override
    public List<Epic> getAllEpics() {
        return service.getAllEpics();
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return service.getAllSubTasks();
    }

    @Override
    public Set<AbstractTask> getPrioritizedTasks() {
        return service.getPrioritizedTasks();
    }
    @Override
    public void removeAllByType(TaskType type) {
        service.removeAllByType(type);
    }

    @Override
    public Task getTaskById(int id) {
        return service.getTaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return service.getEpicById(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        return service.getSubTaskById(id);
    }

    @Override
    public void createTask(Task task) {
        service.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        service.createEpic(epic);
        save();
    }

    @Override
    public void createSubTask(SubTask subTask) {
        service.createSubTask(subTask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        service.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        service.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        service.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        service.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        service.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        service.deleteEpicById(id);
        save();
    }

    @Override
    public List<SubTask> getAllSubTasksOfEpic(Epic epic) {
        return service.getAllSubTasksOfEpic(epic);
    }

    public void save() {
        fileBackedHelper.save(service);
    }

    public void loadFromPath() {
        service = fileBackedHelper.loadFromPath();
    }
}
