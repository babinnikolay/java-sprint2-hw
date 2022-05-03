package repositories;

import exceptions.ManagerSaveException;
import repositories.domain.TaskHistoryList;
import repositories.services.TaskHistoryRepositoryService;
import tasks.AbstractTask;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class FileBackedTaskHistoryRepository implements TaskHistoryRepository{
    private TaskHistoryRepositoryService service;

    public static final Path FILE_DB_PATH = Paths.get( "resources","fileHistoryTaskDB.ser");

    public FileBackedTaskHistoryRepository(TaskHistoryRepositoryService service) {
        this.service = service;
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_DB_PATH.toFile()))) {
            oos.writeObject(service);
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    public void loadFromPath() {
        if (FILE_DB_PATH.toFile().exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_DB_PATH.toFile()))) {
                service = (TaskHistoryRepositoryService) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new ManagerSaveException();
            }
        }
    }

}
