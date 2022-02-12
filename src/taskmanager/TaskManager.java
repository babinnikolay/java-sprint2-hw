package taskmanager;

import repositories.TaskRepository;
import services.TaskManagerService;
import tasks.*;

import java.util.List;

public class TaskManager {
    private final TaskManagerService service;

    public TaskManager(TaskRepository repository) {
        this.service = new TaskManagerService(repository);
    }

    public List<Task> getAllTasks() {
        return service.getAllTask();
    }

    public List<Epic> getAllEpics() {
        return service.getAllEpics();
    }

    public List<SubTask> getAllSubTasks() {
        return service.getAllSubTasks();
    }

    public void removeAllTasks() {
        service.removeAllTasks();
    }

    public void removeAllEpics() {
        service.removeAllEpics();
    }

    public void removeAllSubTasks() {
        service.removeAllSubTasks();
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

    public void updateEpic(Epic epic) {
        service.updateEpic(epic);
    }

    public void updateSubTask(SubTask subTask) {
        service.updateSubTask(subTask);
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

    public List<SubTask> getAllSubTaskOfEpic(Epic epic) {
        return service.getAllSubTasksOfEpic(epic);
    }

}
