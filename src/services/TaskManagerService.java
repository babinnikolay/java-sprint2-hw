package services;

import repositories.TaskRepository;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;
import tasks.TaskType;
import java.util.List;

public class TaskManagerService implements TaskManager {
    private final TaskRepository repository;
    private final HistoryManager history;

    public TaskManagerService(TaskRepository taskRepository) {
        this.repository = taskRepository;
        this.history = Managers.getDefaultHistory();
    }

    public HistoryManager getHistoryManager() {
        return history;
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.getAllTasks();
    }

    @Override
    public List<Epic> getAllEpics() {
        return repository.getAllEpics();
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return repository.getAllSubTasks();
    }

    @Override
    public void removeAllTasks() {
        repository.removeAllByType(TaskType.TASK);
    }

    @Override
    public void removeAllEpics() {
        repository.removeAllByType(TaskType.EPIC);
        repository.removeAllByType(TaskType.SUB_TASK);
    }

    @Override
    public void removeAllSubTasks() {
        repository.removeAllByType(TaskType.SUB_TASK);
        for (Epic epic : repository.getAllEpics()) {
            epic.getSubTasks().clear();
            epic.updateStatus();
        }
    }

    @Override
    public Task getTaskById(int id) {
        Task task = repository.getTaskById(id);
        history.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = repository.getEpicById(id);
        history.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = repository.getSubTaskById(id);
        history.add(subTask);
        return subTask;
    }

    @Override
    public void createTask(Task task) {
        task.setId(repository.getNextId());
        repository.createTask(task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(repository.getNextId());
        repository.createEpic(epic);
    }

    @Override
    public void createSubTask(SubTask subTask) {
        subTask.setId(repository.getNextId());
        repository.createSubTask(subTask);
        subTask.getParent().addSubTask(subTask);
    }

    @Override
    public void updateTask(Task task) {
        repository.updateTask(task);
    }

    @Override
    public void updateEpic(Epic epic) {
        repository.updateEpic(epic);
        epic.updateStatus();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        repository.updateSubTask(subTask);
        subTask.getParent().updateStatus();
    }

    @Override
    public void deleteTaskById(int id) {
        repository.deleteTaskById(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        SubTask subTask = repository.getSubTaskById(id);

        Epic epic = subTask.getParent();
        epic.getSubTasks().remove(subTask);
        epic.updateStatus();

        repository.deleteSubTaskById(id);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = repository.getEpicById(id);

        List<SubTask> subTasks = epic.getSubTasks();
        for (SubTask subTask : subTasks) {
            repository.deleteSubTaskById(subTask.getId());
        }

        repository.deleteEpicById(id);
    }

    @Override
    public List<SubTask> getAllSubTasksOfEpic(Epic epic) {
        return repository.getAllSubTasksOfEpic(epic);
    }
}
