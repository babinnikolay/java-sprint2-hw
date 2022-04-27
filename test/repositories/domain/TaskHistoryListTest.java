package repositories.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.AbstractTask;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskHistoryListTest {

    TaskHistoryList taskHistoryList;

    @Mock
    HashMap nodesStub;

    @Mock
    AbstractTask abstractTask1Stub;

    @Mock
    AbstractTask abstractTask2Stub;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskHistoryList = new TaskHistoryList(nodesStub);
    }

    @Test
    public void Should_AddLastAbstractTask_When_LinkLast() {
        when(abstractTask1Stub.getId()).thenReturn(1);
        when(abstractTask2Stub.getId()).thenReturn(2);

        taskHistoryList.linkLast(abstractTask1Stub);
        taskHistoryList.linkLast(abstractTask2Stub);

        assertEquals(2, taskHistoryList.getHistory().size());
    }

    @Test
    public void Should_ThrowException_When_RemoveByNonExistentId() {
        assertThrows(
                NoSuchElementException.class,
                () -> taskHistoryList.remove(10),
                "non-existent id"
        );
    }

    @Test
    public void Should_ReturnListOfAbstractTask_When_GetHistory() {
        assertNotNull(taskHistoryList.getHistory());
    }

}