package repositories.services;

import exceptions.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskRepositoryServiceTest {

    @Mock
    private Task taskStub1;

    @Mock
    private Task taskStub2;

    @Mock
    private Epic epicStub1;

    @Mock
    private Epic epicStub2;

    @Mock
    private SubTask subTask1Stub;

    @Mock
    private SubTask subTask2Stub;

    private TaskRepositoryService taskRepositoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskRepositoryService = new TaskRepositoryService();
    }

    @Test
    public void shouldReturn1WhenGetNextIdNewService() {
        assertEquals(1, taskRepositoryService.getNexId());
    }

    // tasks
    @Test
    public void shouldListOfTaskAndNotNullWhenGetAllTasks() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub1.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub1.getEndTime()).thenReturn(LocalDateTime.MIN);
        taskRepositoryService.createTask(taskStub1);

        assertEquals(1, taskRepositoryService.getAllTasks().size());
        assertNotNull(taskRepositoryService.getAllTasks());
    }

    @Test
    public void shouldClearAllTasksWhenRemoveAllByTypeTask() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub2.getId()).thenReturn(2);
        when(taskStub1.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub1.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub2.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub2.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createTask(taskStub1);
        taskRepositoryService.createTask(taskStub2);

        taskRepositoryService.removeAllByType(TaskType.TASK);
        assertEquals(0, taskRepositoryService.getAllTasks().size());
    }

    @Test
    public void shouldReturnTaskWhenGetTaskByExistentId() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub1.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub1.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createTask(taskStub1);
        assertEquals(1, taskRepositoryService.getTaskById(1).getId());
        assertNotNull(taskRepositoryService.getTaskById(1));
    }

    @Test
    public void shouldReturnNullWhenGetTaskByNonExistentId() {
        assertNull(taskRepositoryService.getTaskById(-1));
    }

    @Test
    public void shouldCreateTasksWhenCreateTask() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub2.getId()).thenReturn(2);
        when(taskStub1.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub1.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub2.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub2.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createTask(taskStub1);
        taskRepositoryService.createTask(taskStub2);

        assertEquals(taskStub1, taskRepositoryService.getTaskById(1));
        assertEquals(taskStub2, taskRepositoryService.getTaskById(2));
    }

    @Test
    public void shouldGetUpdatedTaskWhenUpdateTask() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub1.getName()).thenReturn("original");
        when(taskStub1.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub1.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createTask(taskStub1);
        assertEquals("original", taskRepositoryService.getTaskById(1).getName());

        when(taskStub1.getName()).thenReturn("updated");
        taskRepositoryService.updateTask(taskStub1);
        assertEquals("updated", taskRepositoryService.getTaskById(1).getName());
    }

    @Test
    public void shouldRemoveTaskWhenDeleteTaskByExistentId() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub1.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(taskStub1.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createTask(taskStub1);

        assertEquals(1, taskRepositoryService.getAllTasks().size());
        taskRepositoryService.deleteTaskById(1);
        assertEquals(0, taskRepositoryService.getAllTasks().size());
    }

    @Test
    public void shouldRemoveTaskWhenDeleteTaskByNonExistentId() {
        assertDoesNotThrow(
                () -> taskRepositoryService.deleteTaskById(-1)
        );
    }

    // epics
    @Test
    public void shouldListOfEpicAndNotNullWhenGetAllEpics() {
        when(epicStub1.getId()).thenReturn(1);
        when(epicStub1.getStartTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createEpic(epicStub1);
        assertEquals(1, taskRepositoryService.getAllEpics().size());
        assertNotNull(taskRepositoryService.getAllEpics());
    }

    @Test
    public void shouldClearAllEpicsAndSubTasksWhenRemoveAllByTypeEpic() {
        when(epicStub1.getId()).thenReturn(1);
        when(epicStub2.getId()).thenReturn(2);
        when(epicStub1.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(epicStub2.getStartTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createEpic(epicStub1);
        taskRepositoryService.createEpic(epicStub2);

        taskRepositoryService.removeAllByType(TaskType.EPIC);
        assertEquals(0, taskRepositoryService.getAllEpics().size());
        assertEquals(0, taskRepositoryService.getAllSubTasks().size());
    }

    @Test
    public void shouldReturnNullWhenGetEpicByNonExistentId() {
        assertNull(taskRepositoryService.getEpicById(-1));
    }

    @Test
    public void shouldCreateEpicsWhenCreateEpic() {
        when(epicStub1.getId()).thenReturn(1);
        when(epicStub2.getId()).thenReturn(2);
        when(epicStub1.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(epicStub2.getStartTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createEpic(epicStub1);
        taskRepositoryService.createEpic(epicStub2);

        assertEquals(epicStub1, taskRepositoryService.getEpicById(1));
        assertEquals(epicStub2, taskRepositoryService.getEpicById(2));
    }

    @Test
    public void shouldGetUpdatedEpicWhenUpdateEpic() {
        when(epicStub1.getId()).thenReturn(1);
        when(epicStub1.getName()).thenReturn("original");
        when(epicStub1.getStartTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createEpic(epicStub1);
        assertEquals("original", taskRepositoryService.getEpicById(1).getName());

        when(epicStub1.getName()).thenReturn("updated");
        taskRepositoryService.updateEpic(epicStub1);
        assertEquals("updated", taskRepositoryService.getEpicById(1).getName());
    }

    @Test
    public void shouldRemoveEpicWhenDeleteEpicByExistentId() {
        when(epicStub1.getId()).thenReturn(1);
        when(epicStub1.getStartTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createEpic(epicStub1);

        assertEquals(1, taskRepositoryService.getAllEpics().size());
        taskRepositoryService.deleteEpicById(1);
        assertEquals(0, taskRepositoryService.getAllEpics().size());
    }

    @Test
    public void shouldRemoveEpicWhenDeleteEpicByNonExistentId() {
        assertDoesNotThrow(
                () -> taskRepositoryService.deleteEpicById(-1)
        );
    }

    // subtasks
    @Test
    public void shouldListOfSubtasksAndNotNullWhenGetAllSubTasks() {
        when(subTask1Stub.getId()).thenReturn(1);
        when(subTask2Stub.getId()).thenReturn(2);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createSubTask(subTask1Stub);
        taskRepositoryService.createSubTask(subTask2Stub);

        assertEquals(2, taskRepositoryService.getAllSubTasks().size());
        assertNotNull(taskRepositoryService.getAllSubTasks());
    }

    @Test
    public void shouldClearAllSubTasksWhenRemoveAllByTypeSubTask() {
        when(subTask1Stub.getId()).thenReturn(1);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createSubTask(subTask1Stub);

        taskRepositoryService.removeAllByType(TaskType.SUB_TASK);
        assertEquals(0, taskRepositoryService.getAllSubTasks().size());
    }

    @Test
    public void shouldReturnNullWhenGetSubTaskByNonExistentId() {
        assertNull(taskRepositoryService.getSubTaskById(-1));
    }

    @Test
    public void shouldCreateSubtasksWhenCreateSubTask() {
        when(subTask1Stub.getId()).thenReturn(1);
        when(subTask2Stub.getId()).thenReturn(2);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask2Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createSubTask(subTask1Stub);
        taskRepositoryService.createSubTask(subTask2Stub);

        assertEquals(subTask1Stub, taskRepositoryService.getSubTaskById(1));
        assertEquals(subTask2Stub, taskRepositoryService.getSubTaskById(2));
    }

    @Test
    public void shouldGetUpdatedSubTaskWhenUpdateSubTask() {
        when(subTask1Stub.getId()).thenReturn(1);
        when(subTask1Stub.getName()).thenReturn("original");
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createSubTask(subTask1Stub);
        assertEquals("original", taskRepositoryService.getSubTaskById(1).getName());

        when(subTask1Stub.getName()).thenReturn("updated");
        taskRepositoryService.updateSubTask(subTask1Stub);
        assertEquals("updated", taskRepositoryService.getSubTaskById(1).getName());
    }

    @Test
    public void shouldRemoveSubTaskWhenDeleteSubTaskByExistentId() {
        when(subTask1Stub.getId()).thenReturn(1);
        when(subTask1Stub.getStartTime()).thenReturn(LocalDateTime.MIN);
        when(subTask1Stub.getEndTime()).thenReturn(LocalDateTime.MIN);

        taskRepositoryService.createSubTask(subTask1Stub);

        assertEquals(1, taskRepositoryService.getAllSubTasks().size());
        taskRepositoryService.deleteSubTaskById(1);
        assertEquals(0, taskRepositoryService.getAllSubTasks().size());
    }

    @Test
    public void shouldRemoveSubTaskWhenDeleteSubTaskByNonExistentId() {
        assertDoesNotThrow(
                () -> taskRepositoryService.deleteSubTaskById(-1)
        );
    }

    // other
    @Test
    public void shouldReturnListOfEpicsWhenGetAllSubTasksOfEpic() {
        List<SubTask> subTasksList = List.of(subTask1Stub, subTask2Stub);
        when(epicStub1.getSubTasks()).thenReturn(subTasksList);

        assertEquals(subTasksList, taskRepositoryService.getAllSubTasksOfEpic(epicStub1));
    }

    @Test
    public void shouldReturnPrioritizedSetWhenGetPrioritizedTasks() {
        LocalDateTime minDateTimeStart
                = LocalDateTime.of(2022, 1, 1, 1, 1, 1);
        LocalDateTime minDateTimeEnd
                = LocalDateTime.of(2022, 1, 1, 1, 30, 1);

        LocalDateTime midDateTimeStart
                = LocalDateTime.of(2022, 1, 1, 2, 1, 1);
        LocalDateTime midDateTimeEnd
                = LocalDateTime.of(2022, 1, 1, 2, 30, 1);

        LocalDateTime maxDateTimeStart
                = LocalDateTime.of(2022, 1, 1, 3, 1, 1);
        LocalDateTime maxDateTimeEnd
                = LocalDateTime.of(2022, 1, 1, 3, 30, 1);

        when(taskStub1.getStartTime()).thenReturn(midDateTimeStart);
        when(taskStub2.getStartTime()).thenReturn(maxDateTimeStart);
        when(subTask1Stub.getStartTime()).thenReturn(minDateTimeStart);

        when(taskStub1.getEndTime()).thenReturn(midDateTimeEnd);
        when(taskStub2.getEndTime()).thenReturn(maxDateTimeEnd);
        when(subTask1Stub.getEndTime()).thenReturn(minDateTimeEnd);

        when(taskStub1.getId()).thenReturn(1);
        when(taskStub2.getId()).thenReturn(2);
        when(subTask1Stub.getId()).thenReturn(3);

        taskRepositoryService.createTask(taskStub1);
        taskRepositoryService.createTask(taskStub2);
        taskRepositoryService.createSubTask(subTask1Stub);

        Set<AbstractTask> prioritizedTasks = taskRepositoryService.getPrioritizedTasks();
        List<AbstractTask> prioritizedListTasks = new ArrayList<>(prioritizedTasks);
        assertEquals(minDateTimeStart, prioritizedListTasks.get(0).getStartTime());
        assertEquals(maxDateTimeStart, prioritizedListTasks.get(2).getStartTime());
    }

    @Test
    public void shouldDontAddTaskAndThrowExceptionWhenThereIsAnIntersection() {
        LocalDateTime startTime1
                = LocalDateTime.of(2022, 1, 2, 1, 1, 1);
        LocalDateTime endTime1
                = LocalDateTime.of(2022, 1, 3, 1, 30, 1);

        LocalDateTime startTime2
                = LocalDateTime.of(2022, 1, 2, 1, 10, 1);
        LocalDateTime endTime2
                = LocalDateTime.of(2022, 1, 2, 1, 45, 1);

        when(taskStub1.getStartTime()).thenReturn(startTime1);
        when(taskStub2.getStartTime()).thenReturn(startTime2);

        when(taskStub1.getEndTime()).thenReturn(endTime1);
        when(taskStub2.getEndTime()).thenReturn(endTime2);

        when(taskStub1.getId()).thenReturn(1);
        when(taskStub2.getId()).thenReturn(2);

        taskRepositoryService.createTask(taskStub1);
        assertThrows(
                ManagerSaveException.class,
                () -> taskRepositoryService.createTask(taskStub2),
                "Есть пересечения с другими задачами"
        );

        assertEquals(1, taskRepositoryService.getAllTasks().size());
    }

    @Test
    public void shouldAddTaskWhenThereIsNotAnIntersection() {
        LocalDateTime startTime1
                = LocalDateTime.of(2022, 1, 2, 1, 1, 1);
        LocalDateTime endTime1
                = LocalDateTime.of(2022, 1, 3, 1, 30, 1);

        LocalDateTime startTime2
                = LocalDateTime.of(2022, 1, 4, 1, 46, 1);
        LocalDateTime endTime2
                = LocalDateTime.of(2022, 1, 4, 1, 55, 1);

        when(taskStub1.getStartTime()).thenReturn(startTime1);
        when(taskStub2.getStartTime()).thenReturn(startTime2);

        when(taskStub1.getEndTime()).thenReturn(endTime1);
        when(taskStub2.getEndTime()).thenReturn(endTime2);

        when(taskStub1.getId()).thenReturn(1);
        when(taskStub2.getId()).thenReturn(2);

        taskRepositoryService.createTask(taskStub1);
        taskRepositoryService.createTask(taskStub2);

        assertEquals(2, taskRepositoryService.getAllTasks().size());
    }
}