package repositories.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.domain.TaskHistoryList;
import tasks.AbstractTask;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskHistoryRepositoryServiceTest {

    @Mock
    private TaskHistoryList taskHistoryStab;

    @Mock
    private AbstractTask abstractTaskStub;

    private TaskHistoryRepositoryService historyRepositoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        historyRepositoryService = new TaskHistoryRepositoryService(taskHistoryStab);
    }

    @Test
    public void shouldCallTaskHistoryLinkLastMethodWhenAdd() {
        historyRepositoryService.add(abstractTaskStub);
        verify(taskHistoryStab).linkLast(abstractTaskStub);
    }

    @Test
    public void shouldCallTaskHistoryRemoveMethodWhenRemove() {
        when(abstractTaskStub.getId()).thenReturn(1);
        historyRepositoryService.remove(abstractTaskStub.getId());
        verify(taskHistoryStab).remove(1);
    }

    @Test
    public void shouldReturnListOfAbstractTaskWhenHistory() {
        List<AbstractTask> abstractTaskList = List.of(abstractTaskStub);
        when(taskHistoryStab.getHistory()).thenReturn(abstractTaskList);
        assertEquals(abstractTaskList, historyRepositoryService.history());
        assertNotNull(historyRepositoryService.history());
    }

}