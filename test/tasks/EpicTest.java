package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EpicTest {

    Epic epic;

    @Mock
    SubTask subTask1Stub;

    @Mock
    SubTask subTask2Stub;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        epic = new Epic("Name", "Desc");
    }

    @Test
    public void Should_ReturnListOfSubTask_When_GetSubTasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(2, epic.getSubTasks().size());
        assertNotNull(epic.getSubTasks());
    }

    @Test
    public void Should_ReturnStatusNew_When_EpicHaveAllNewSubtasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void Should_ReturnStatusDone_When_EpicHaveAllDoneSubtasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.DONE);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.DONE);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    public void Should_ReturnStatusOnProgress_When_EpicHaveDifferentStatusSubtasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.DONE);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void Should_ReturnStatusOnProgress_When_EpicHaveInProgressSubtasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void Should_ReturnStatusNew_When_EpicDoesntHaveSubtasks() {
        epic.updateStatus();
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void Should_ReturnMinAndMaxSubtasksTime_When_AddSubTasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);

        when(subTask1Stub.getStartTime()).thenReturn(
                LocalDateTime.of(2022, 1, 1, 1, 1, 1));
        when(subTask2Stub.getStartTime()).thenReturn(
                LocalDateTime.of(2022, 1, 1, 1, 1, 2));
        when(subTask1Stub.getEndTime()).thenReturn(
                LocalDateTime.of(2022, 1, 1, 1, 1, 10));
        when(subTask2Stub.getEndTime()).thenReturn(
                LocalDateTime.of(2022, 1, 1, 1, 1, 20));

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        epic.updateTimeFields();

        assertEquals(subTask1Stub.getStartTime(), epic.getStartTime());
        assertEquals(subTask2Stub.getEndTime(), epic.getEndTime());
    }

    @Test
    public void Should_ReturnMinAndMaxSubtask2Time_When_AddSubTasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);

        when(subTask1Stub.getStartTime()).thenReturn(
                LocalDateTime.of(2022, 1, 1, 1, 1, 2));
        when(subTask2Stub.getStartTime()).thenReturn(
                LocalDateTime.of(2022, 1, 1, 1, 1, 1));
        when(subTask1Stub.getEndTime()).thenReturn(
                LocalDateTime.of(2022, 1, 1, 1, 1, 10));
        when(subTask2Stub.getEndTime()).thenReturn(
                LocalDateTime.of(2022, 1, 1, 1, 1, 20));

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        epic.updateTimeFields();

        assertEquals(subTask2Stub.getStartTime(), epic.getStartTime());
        assertEquals(subTask2Stub.getEndTime(), epic.getEndTime());
    }

    @Test
    public void Should_ReturnNull_When_EpicDoesNotHaveSubtasksGetStartEndTime() {
        epic.updateTimeFields();

        assertNull(epic.getStartTime());
        assertNull(epic.getEndTime());
        assertEquals(Duration.ZERO, epic.getDuration());
    }

}