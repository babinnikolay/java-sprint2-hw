package repositories;

import repositories.services.TaskRepositoryService;
import tasks.*;

import java.util.*;

public class InMemoryTaskRepository implements TaskRepository{
    private final TaskRepositoryService service;

    public InMemoryTaskRepository() {
        this.service = new TaskRepositoryService();
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
    }

    @Override
    public void createEpic(Epic epic) {
        service.createEpic(epic);
    }

    @Override
    public void createSubTask(SubTask subTask) {
        service.createSubTask(subTask);
    }

    @Override
    public void updateTask(Task task) {
        service.updateTask(task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        service.updateSubTask(subTask);
    }

    @Override
    public void updateEpic(Epic epic) {
        service.updateEpic(epic);
    }

    @Override
    public void deleteTaskById(int id) {
        service.deleteTaskById(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        service.deleteSubTaskById(id);
    }

    @Override
    public void deleteEpicById(int id) {
        service.deleteEpicById(id);
    }

    @Override
    public List<SubTask> getAllSubTasksOfEpic(Epic epic) {
        return service.getAllSubTasksOfEpic(epic);
    }

}
