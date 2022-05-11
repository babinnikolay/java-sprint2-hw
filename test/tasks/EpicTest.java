package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EpicTest {

    private Epic epic;

    @Mock
    private SubTask subTask1Stub;

    @Mock
    private SubTask subTask2Stub;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        epic = new Epic("Name", "Desc");
    }

    @Test
    public void shouldReturnListOfSubTaskWhenGetSubTasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getDuration()).thenReturn(Duration.ZERO);
        when(subTask2Stub.getDuration()).thenReturn(Duration.ZERO);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(2, epic.getSubTasks().size());
        assertNotNull(epic.getSubTasks());
    }

    @Test
    public void shouldReturnStatusNewWhenEpicHaveAllNewSubtasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getDuration()).thenReturn(Duration.ZERO);
        when(subTask2Stub.getDuration()).thenReturn(Duration.ZERO);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void shouldReturnStatusDoneWhenEpicHaveAllDoneSubtasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.DONE);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.DONE);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getDuration()).thenReturn(Duration.ZERO);
        when(subTask2Stub.getDuration()).thenReturn(Duration.ZERO);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    public void shouldReturnStatusOnProgressWhenEpicHaveDifferentStatusSubtasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.NEW);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.DONE);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getDuration()).thenReturn(Duration.ZERO);
        when(subTask2Stub.getDuration()).thenReturn(Duration.ZERO);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldReturnStatusOnProgressWhenEpicHaveIN_PROGRESSSubtasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getDuration()).thenReturn(Duration.ZERO);
        when(subTask2Stub.getDuration()).thenReturn(Duration.ZERO);

        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldReturnStatusNewWhenEpicDoesntHaveSubtasks() {
        epic.updateStatus();
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void shouldReturnMinAndMaxSubtasksTimeWhenAddSubTasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask1Stub.getDuration()).thenReturn(Duration.ZERO);
        when(subTask2Stub.getDuration()).thenReturn(Duration.ZERO);

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
    public void shouldReturnMinAndMaxSubtask2TimeWhenAddSubTasks() {
        when(subTask1Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask2Stub.getStatus()).thenReturn(TaskStatus.IN_PROGRESS);
        when(subTask1Stub.getDuration()).thenReturn(Duration.ZERO);
        when(subTask2Stub.getDuration()).thenReturn(Duration.ZERO);

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
    public void shouldReturnNullWhenEpicDoesNotHaveSubtasksGetStartEndTime() {
        epic.updateTimeFields();

        assertNotNull(epic.getStartTime());
        assertNotNull(epic.getEndTime());
        assertEquals(Duration.ZERO, epic.getDuration());
    }

}