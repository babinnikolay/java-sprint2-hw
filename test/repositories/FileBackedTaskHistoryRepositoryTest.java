package repositories;

import exceptions.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.SubTask;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskHistoryRepositoryTest {

    FileBackedTaskHistoryRepository taskHistoryRepository;

    @Mock
    SubTask subTaskStub;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskHistoryRepository = new FileBackedTaskHistoryRepository();
    }

    @Test
    public void Should_Save_When_SaveEmptyServiceList() {
        assertThrows(
                ManagerSaveException.class,
                () -> taskHistoryRepository.save(),
                "non-existent id"
        );
    }

}