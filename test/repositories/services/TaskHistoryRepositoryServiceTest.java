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

class TaskHistoryRepositoryServiceTest {

    @Mock
    TaskHistoryList taskHistoryStab;

    @Mock
    AbstractTask abstractTaskStub;

    TaskHistoryRepositoryService historyRepositoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        historyRepositoryService = new TaskHistoryRepositoryService(taskHistoryStab);
    }

    @Test
    public void Should_CallTaskHistoryLinkLastMethod_When_Add() {
        historyRepositoryService.add(abstractTaskStub);
        verify(taskHistoryStab).linkLast(abstractTaskStub);
    }

    @Test
    public void Should_CallTaskHistoryRemoveMethod_When_Remove() {
        when(abstractTaskStub.getId()).thenReturn(1);
        historyRepositoryService.remove(abstractTaskStub.getId());
        verify(taskHistoryStab).remove(1);
    }

    @Test
    public void Should_ReturnListOfAbstractTask_When_history() {
        List<AbstractTask> abstractTaskList = List.of(abstractTaskStub);
        when(taskHistoryStab.getHistory()).thenReturn(abstractTaskList);
        assertEquals(abstractTaskList, historyRepositoryService.history());
        assertNotNull(historyRepositoryService.history());
    }

}