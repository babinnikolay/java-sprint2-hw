package repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.services.TaskRepositoryService;
import services.kv.KVServer;
import services.kv.KVTaskClient;
import tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPTaskRepositoryTest extends AbstractTaskRepositoryTest<FileBackedTaskRepository> {

    @Mock
    private KVTaskClient kvTaskClientStub;

    private KVServer kvServer;

    @BeforeEach
    public void setUp() {
        try {
            kvServer = new KVServer();
            kvServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MockitoAnnotations.initMocks(this);
        taskRepository = new HTTPTaskRepository(serviceStub, kvTaskClientStub);
    }

    @AfterEach
    public void setDown() {
        kvServer.stop();
    }

    @Test
    public void shouldSaveToKVServerAndRestoreTasksWhenCreateTask() {

        KVTaskClient client = new KVTaskClient("http://localhost:8078");

        taskRepository = new HTTPTaskRepository(new TaskRepositoryService(), client);

        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.MIN);
        task.setDuration(Duration.ZERO);

        taskRepository.createTask(task);

        taskRepository.loadFromPath();

        assertEquals(1, taskRepository.getAllTasks().size());
    }

}