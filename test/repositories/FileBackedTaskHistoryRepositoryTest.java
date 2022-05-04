package repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.domain.FileBackedHelper;
import repositories.domain.TaskHistoryList;
import repositories.services.TaskHistoryRepositoryService;
import tasks.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class FileBackedTaskHistoryRepositoryTest extends AbstractTaskHistoryRepositoryTest<FileBackedTaskHistoryRepository>{

    @Mock
    private FileBackedHelper<TaskHistoryRepositoryService> fileBackedHelperStub;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskHistoryRepository = new FileBackedTaskHistoryRepository(serviceStub, fileBackedHelperStub);
    }

    @Override
    @Test
    public void shouldCallMethodAddWhenAdd() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).add(taskStub1);

        taskHistoryRepository.add(taskStub1);

        verify(serviceStub).add(taskStub1);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldCallMethodRemoveWhenRemove() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).remove(1);

        taskHistoryRepository.remove(1);

        verify(serviceStub).remove(1);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Test
    public void shouldSaveToFileAndRestoreHistoryTasksWhenAdd() {
        Path filePath = Paths.get("fileHistoryTaskDB.ser");

        FileBackedHelper<TaskHistoryRepositoryService> fileBackedHelper
                = new FileBackedHelper(filePath);

        TaskHistoryList taskHistory = new TaskHistoryList(new HashMap<>());
        TaskHistoryRepositoryService service = new TaskHistoryRepositoryService(taskHistory);
        taskHistoryRepository = new FileBackedTaskHistoryRepository(service, fileBackedHelper);

        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.MIN);
        task.setDuration(Duration.ZERO);

        taskHistoryRepository.add(task);

        assertTrue(Files.exists(filePath));

        taskHistory = new TaskHistoryList(new HashMap<>());
        service = new TaskHistoryRepositoryService(taskHistory);
        taskHistoryRepository = new FileBackedTaskHistoryRepository(service, fileBackedHelper);
        taskHistoryRepository.loadFromPath();
        assertEquals(1, taskHistoryRepository.history().size());
    }
}