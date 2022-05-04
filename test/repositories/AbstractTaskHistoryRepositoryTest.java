package repositories;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import repositories.domain.TaskHistoryList;
import repositories.services.TaskHistoryRepositoryService;
import tasks.AbstractTask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public abstract class AbstractTaskHistoryRepositoryTest<T extends TaskHistoryRepository> {

    protected T taskHistoryRepository;

    @Mock
    protected TaskHistoryRepositoryService serviceStub;

    @Mock
    protected Task taskStub1;

    @Mock
    protected Task taskStub2;

    @Test
    public void shouldCallMethodAddWhenAdd() {
        taskHistoryRepository.add(taskStub1);
        verify(serviceStub).add(taskStub1);
    }

    @Test
    public void shouldCallMethodRemoveWhenRemove() {
        taskHistoryRepository.remove(1);
        verify(serviceStub).remove(1);
    }

    @Test
    public void shouldReturnListOfAbstractTaskWhenHistory() {
        List<AbstractTask> taskList = List.of(taskStub1, taskStub2);
        when(serviceStub.history()).thenReturn(taskList);

        assertEquals(taskList, serviceStub.history());
    }
}