package services;

import repositories.TaskRepository;
import tasks.*;

import java.util.List;

public class TaskManagerService {
    private final TaskRepository repository;


    public TaskManagerService(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    public List<Task> getAllTask() {
        return repository.getAllTasks();
    }

    public List<Epic> getAllEpics() {
        return repository.getAllEpics();
    }

    public List<SubTask> getAllSubTasks() {
        return repository.getAllSubTasks();
    }

    public void removeAllTasks() {
        repository.removeAllByType(TaskType.TASK);
    }

    public void removeAllEpics() {
        repository.removeAllByType(TaskType.EPIC);
        repository.removeAllByType(TaskType.SUB_TASK);
    }

    public void removeAllSubTasks() {
        repository.removeAllByType(TaskType.SUB_TASK);
        for (Epic epic : repository.getAllEpics()) {
            epic.getSubTasks().clear();
            epic.updateStatus();
        }
    }

    public Task getTaskById(int id) {
        return repository.getTaskById(id);
    }

    public Epic getEpicById(int id) {
        return repository.getEpicById(id);
    }

    public SubTask getSubTaskById(int id) {
        return repository.getSubTaskById(id);
    }

    public void createTask(Task task) {
        task.setId(repository.getNextId());
        repository.createTask(task);
    }

    public void createEpic(Epic epic) {
        epic.setId(repository.getNextId());
        repository.createEpic(epic);
    }

    public void createSubTask(SubTask subTask) {
        subTask.setId(repository.getNextId());
        repository.createSubTask(subTask);
        subTask.getParent().addSubTask(subTask);
    }

    public void updateTask(Task task) {
        repository.updateTask(task);
    }

    public void updateEpic(Epic epic) {
        repository.updateEpic(epic);
        epic.updateStatus();
    }

    public void updateSubTask(SubTask subTask) {
        repository.updateSubTask(subTask);
    }

    public void deleteTaskById(int id) {
        repository.deleteTaskById(id);
    }

    public void deleteSubTaskById(int id) {
        Epic epic = repository.getSubTaskById(id).getParent();
        repository.deleteSubTaskById(id);
        epic.updateStatus();
    }

    public void deleteEpicById(int id) {
        repository.deleteEpicById(id);
    }

    public List<SubTask> getAllSubTasksOfEpic(Epic epic) {
        return repository.getAllSubTasksOfEpic(epic);
    }
}
