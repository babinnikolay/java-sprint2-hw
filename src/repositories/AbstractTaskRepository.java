package repositories;

import repositories.services.TaskRepositoryService;
import tasks.*;

import java.util.List;
import java.util.Set;

public abstract class AbstractTaskRepository {

    protected TaskRepositoryService service;

    public AbstractTaskRepository(TaskRepositoryService service) {
        this.service = service;
    }

    public int getNextId() {
        return service.getNexId();
    }

    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    public List<Epic> getAllEpics() {
        return service.getAllEpics();
    }

    public List<SubTask> getAllSubTasks() {
        return service.getAllSubTasks();
    }

    public Set<AbstractTask> getPrioritizedTasks() {
        return service.getPrioritizedTasks();
    }

    public void removeAllByType(TaskType type) {
        service.removeAllByType(type);
    }

    public Task getTaskById(int id) {
        return service.getTaskById(id);
    }

    public Epic getEpicById(int id) {
        return service.getEpicById(id);
    }

    public SubTask getSubTaskById(int id) {
        return service.getSubTaskById(id);
    }

    public void createTask(Task task) {
        service.createTask(task);
    }

    public void createEpic(Epic epic) {
        service.createEpic(epic);
    }

    public void createSubTask(SubTask subTask) {
        service.createSubTask(subTask);
    }

    public void updateTask(Task task) {
        service.updateTask(task);
    }

    public void updateSubTask(SubTask subTask) {
        service.updateSubTask(subTask);
    }

    public void updateEpic(Epic epic) {
        service.updateEpic(epic);
    }

    public void deleteTaskById(int id) {
        service.deleteTaskById(id);
    }

    public void deleteSubTaskById(int id) {
        service.deleteSubTaskById(id);
    }

    public void deleteEpicById(int id) {
        service.deleteEpicById(id);
    }

    public List<SubTask> getAllSubTasksOfEpic(Epic epic) {
        return service.getAllSubTasksOfEpic(epic);
    }

}
