package repositories;

import exceptions.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.Epic;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileBackedTaskRepositoryTest {

    FileBackedTaskRepository taskRepository;

    @Mock
    Epic epicStub;

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
        when(epicStub.getStartTime()).thenReturn(LocalDateTime.MIN);
        assertThrows(
                ManagerSaveException.class,
                () -> taskRepository.createEpic(epicStub),
                "non-existent id"
        );
    }

}