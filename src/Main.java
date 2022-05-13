
import exceptions.ManagerSaveException;
import repositories.HTTPTaskRepository;
import repositories.services.TaskRepositoryService;
import services.kv.KVServer;
import services.kv.KVTaskClient;
import tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) throws IOException {
        //тестирование в main это не нормально
        //тест есть в HTTPTaskRepositoryTest.shouldSaveToKVServerAndRestoreTasksWhenCreateTask()

        KVServer kvServer;
        try {
            kvServer = new KVServer();
            kvServer.start();
        } catch (IOException e) {
            throw new ManagerSaveException();
        }

        KVTaskClient client = new KVTaskClient("http://localhost:8078");

        HTTPTaskRepository taskRepository = new HTTPTaskRepository(new TaskRepositoryService(), client);

        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ZERO);

        taskRepository.createTask(task);

        taskRepository.loadFromPath();

        System.out.println("expect - 1, get - " + taskRepository.getAllTasks().size());
        kvServer.stop();

    }
}
