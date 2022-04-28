package repositories;

import exceptions.ManagerSaveException;
import repositories.services.TaskRepositoryService;
import tasks.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

// >>А почему нельзя было наследоваться от TaskRepositoryService и избавиться от всех этих однострочных методов?
// >>Однострочные методы, состоящие из одной super, не добавляют никакой новой функциональности. Поэтому они не нужны.
//
// Наследования нет, о каких вызовах super идет речь?
// Мне не нравится идея применять в этом месте наследование, композиция прозрачнее
public class FileBackedTaskRepository implements TaskRepository{
    private TaskRepositoryService service;

    public static final Path FILE_DB_PATH = Paths.get( "resources","fileTaskDB.ser");

    public FileBackedTaskRepository() {
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_DB_PATH.toFile()))) {
            oos.writeObject(service);
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    public void loadFromPath() {
        if (FILE_DB_PATH.toFile().exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_DB_PATH.toFile()))) {
                service = (TaskRepositoryService) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new ManagerSaveException();
            }
        }
    }
}
