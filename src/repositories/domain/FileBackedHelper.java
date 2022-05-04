package repositories.domain;

import exceptions.ManagerSaveException;

import java.io.*;
import java.nio.file.Path;

public class FileBackedHelper<T> {

    private Path FILE_DB_PATH;

    public FileBackedHelper(Path FILE_DB_PATH) {
        this.FILE_DB_PATH = FILE_DB_PATH;
    }

    public void save(T service) {
        // формат CSV в задании был рекомендуемым, не вижу проблем в использовании сериализации
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_DB_PATH.toFile()))) {
            oos.writeObject(service);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException();
        }
    }

    public T loadFromPath() {
        if (FILE_DB_PATH.toFile().exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_DB_PATH.toFile()))) {
                return (T) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new ManagerSaveException();
            }
        } else {
            throw new ManagerSaveException();
        }
    }

}
