package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.TaskHistoryRepository;
import tasks.AbstractTask;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoryManagerServiceTest {

    @Mock
    TaskHistoryRepository historyRepositoryStub;

    @Mock
    AbstractTask abstractTaskStub;

    HistoryManagerService historyManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        historyManager = new HistoryManagerService(historyRepositoryStub);
    }

    @Test
    public void Should_CallHistoryRepositoryMethodAdd_When_Add() {
        historyManager.add(abstractTaskStub);
        verify(historyRepositoryStub).add(abstractTaskStub);
    }

    @Test
    public void Should_CallHistoryRepositoryMethodRemove_When_Remove() {
        historyManager.remove(1);
        verify(historyRepositoryStub).remove(1);
    }

    @Test
    public void Return_ListOfAbstractTask_When_GetHistory() {
        List<AbstractTask> listOfTask = List.of(abstractTaskStub);
        when(historyRepositoryStub.history()).thenReturn(listOfTask);

        assertEquals(listOfTask, historyManager.getHistory());
    }

}