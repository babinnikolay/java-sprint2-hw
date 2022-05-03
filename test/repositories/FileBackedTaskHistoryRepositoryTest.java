package repositories;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import repositories.domain.TaskHistoryList;
import repositories.services.TaskHistoryRepositoryService;

import java.util.HashMap;

public class FileBackedTaskHistoryRepositoryTest {

    private FileBackedTaskHistoryRepository taskHistoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TaskHistoryList taskHistory = new TaskHistoryList(new HashMap<>());
        TaskHistoryRepositoryService service = new TaskHistoryRepositoryService(taskHistory);
        taskHistoryRepository = new FileBackedTaskHistoryRepository(service);
    }

}