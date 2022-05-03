package repositories.domain;

import exceptions.ManagerSaveException;
import repositories.services.TaskRepositoryService;

import java.io.*;
import java.nio.file.Path;

public class FileBackedHelper {

    private Path FILE_DB_PATH;

    public FileBackedHelper(Path FILE_DB_PATH) {
        this.FILE_DB_PATH = FILE_DB_PATH;
    }

    public void save(TaskRepositoryService service) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_DB_PATH.toFile()))) {
            oos.writeObject(service);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException();
        }
    }

    public TaskRepositoryService loadFromPath() {
        if (FILE_DB_PATH.toFile().exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_DB_PATH.toFile()))) {
                TaskRepositoryService service = (TaskRepositoryService) ois.readObject();
                service.initPrioritizedTasks();
                return service;
            } catch (IOException | ClassNotFoundException e) {
                throw new ManagerSaveException();
            }
        } else {
            throw new ManagerSaveException();
        }
    }

}
