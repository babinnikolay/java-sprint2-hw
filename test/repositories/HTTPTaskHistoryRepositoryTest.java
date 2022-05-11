package repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.domain.TaskHistoryList;
import repositories.services.TaskHistoryRepositoryService;
import services.kv.KVServer;
import services.kv.KVTaskClient;
import tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPTaskHistoryRepositoryTest extends AbstractTaskHistoryRepositoryTest<HTTPTaskHistoryRepository>{

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
        taskHistoryRepository = new HTTPTaskHistoryRepository(serviceStub, kvTaskClientStub);
    }

    @AfterEach
    public void setDown() {
        kvServer.stop();
    }

    @Test
    @Override
    public void shouldCallMethodAddWhenAdd() {
    }

    @Test
    @Override
    public void shouldCallMethodRemoveWhenRemove() {
    }

    @Test
    public void shouldSaveToKVServerAndRestoreHistoryTasksWhenAdd() {

        KVTaskClient client = new KVTaskClient("http://localhost:8078");

        TaskHistoryList taskHistory = new TaskHistoryList(new HashMap<>());
        TaskHistoryRepositoryService service = new TaskHistoryRepositoryService(taskHistory);

        taskHistoryRepository = new HTTPTaskHistoryRepository(service, client);

        Task task = new Task("name", "desc");
        task.setId(1);
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ZERO);

        taskHistoryRepository.add(task);

        taskHistoryRepository.loadFromPath();
        assertEquals(1, taskHistoryRepository.history().size());
    }

}