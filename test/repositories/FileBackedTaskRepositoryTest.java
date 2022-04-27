package repositories;

import exceptions.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.domain.TaskHistoryList;
import tasks.SubTask;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskRepositoryTest {

    FileBackedTaskRepository taskRepository;

    @Mock
    SubTask subTaskStub;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskRepository = new FileBackedTaskRepository();
    }

    @Test
    public void Should_Save_When_SaveEmptyServiceList() {
        assertThrows(
                ManagerSaveException.class,
                () -> taskRepository.save(),
                "non-existent id"
        );
    }

    @Test
    public void Should_Save_When_SaveEpicWithoutSubTask() {
        assertThrows(
                ManagerSaveException.class,
                () -> taskRepository.createSubTask(subTaskStub),
                "non-existent id"
        );
    }

}