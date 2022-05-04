package repositories.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.AbstractTask;
import tasks.Task;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskHistoryListTest {

    private TaskHistoryList taskHistoryList;

    @Mock
    private HashMap nodesStub;

    @Mock
    private AbstractTask abstractTask1Stub;

    @Mock
    private AbstractTask abstractTask2Stub;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskHistoryList = new TaskHistoryList(nodesStub);
    }

    @Test
    public void shouldAddLastAbstractTaskWhenLinkLast() {
        when(abstractTask1Stub.getId()).thenReturn(1);
        when(abstractTask2Stub.getId()).thenReturn(2);

        taskHistoryList.linkLast(abstractTask1Stub);
        taskHistoryList.linkLast(abstractTask2Stub);

        assertEquals(2, taskHistoryList.getHistory().size());
    }

    @Test
    public void shouldThrowExceptionWhenRemoveByNonExistentId() {
        assertThrows(
                NoSuchElementException.class,
                () -> taskHistoryList.remove(10),
                "non-existent id"
        );
    }

    @Test
    public void shouldNotAddAbstractTaskWhenTaskHistoryHaveDuplicate() {

        taskHistoryList = new TaskHistoryList(new HashMap<>());
        Task task1 = new Task("t1", "d");
        task1.setId(1);
        Task task2 = new Task("t2", "d");
        task2.setId(2);

        taskHistoryList.linkLast(task1);
        taskHistoryList.linkLast(task2);
        taskHistoryList.linkLast(task1);
        taskHistoryList.linkLast(task2);
        taskHistoryList.linkLast(task2);
        taskHistoryList.linkLast(task2);

        assertEquals(2, taskHistoryList.getHistory().size());
    }

    @Test
    public void shouldReturnListOfAbstractTaskWhenGetHistory() {
        assertNotNull(taskHistoryList.getHistory());
    }

}