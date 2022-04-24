package repositories.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskRepositoryServiceTest {

    @Mock
    Task taskStub1;

    @Mock
    Task taskStub2;

    @Mock
    Epic epicStub1;

    @Mock
    Epic epicStub2;

    @Mock
    SubTask subTask1Stub;

    @Mock
    SubTask subTask2Stub;

    TaskRepositoryService taskRepositoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskRepositoryService = new TaskRepositoryService();
    }

    @Test
    public void Should_Return1_When_GetNextIdNewService() {
        assertEquals(1, taskRepositoryService.getNexId());
    }

    // tasks
    @Test
    public void Should_ListOfTaskAndNotNull_When_GetAllTasks() {
        when(taskStub1.getId()).thenReturn(1);
        taskRepositoryService.createTask(taskStub1);
        assertEquals(1, taskRepositoryService.getAllTasks().size());
        assertNotNull(taskRepositoryService.getAllTasks());
    }

    @Test
    public void Should_ClearAllTasks_When_RemoveAllByTypeTask() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub2.getId()).thenReturn(2);

        taskRepositoryService.createTask(taskStub1);
        taskRepositoryService.createTask(taskStub2);

        taskRepositoryService.removeAllByType(TaskType.TASK);
        assertEquals(0, taskRepositoryService.getAllTasks().size());
    }

    @Test
    public void Should_ReturnTask_When_GetTaskByExistentId() {
        when(taskStub1.getId()).thenReturn(1);

        taskRepositoryService.createTask(taskStub1);
        assertEquals(1, taskRepositoryService.getTaskById(1).getId());
        assertNotNull(taskRepositoryService.getTaskById(1));
    }

    @Test
    public void Should_ReturnNull_When_GetTaskByNonExistentId() {
        assertNull(taskRepositoryService.getTaskById(-1));
    }

    @Test
    public void Should_CreateTasks_When_CreateTask() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub2.getId()).thenReturn(2);

        taskRepositoryService.createTask(taskStub1);
        taskRepositoryService.createTask(taskStub2);

        assertEquals(taskStub1, taskRepositoryService.getTaskById(1));
        assertEquals(taskStub2, taskRepositoryService.getTaskById(2));
    }

    @Test
    public void Should_GetUpdatedTask_When_UpdateTask() {
        when(taskStub1.getId()).thenReturn(1);
        when(taskStub1.getName()).thenReturn("original");

        taskRepositoryService.createTask(taskStub1);
        assertEquals("original", taskRepositoryService.getTaskById(1).getName());

        when(taskStub1.getName()).thenReturn("updated");
        taskRepositoryService.updateTask(taskStub1);
        assertEquals("updated", taskRepositoryService.getTaskById(1).getName());
    }

    @Test
    public void Should_RemoveTask_When_DeleteTaskByExistentId() {
        when(taskStub1.getId()).thenReturn(1);
        taskRepositoryService.createTask(taskStub1);

        assertEquals(1, taskRepositoryService.getAllTasks().size());
        taskRepositoryService.deleteTaskById(1);
        assertEquals(0, taskRepositoryService.getAllTasks().size());
    }

    @Test
    public void Should_RemoveTask_When_DeleteTaskByNonExistentId() {
        assertDoesNotThrow(
                () -> taskRepositoryService.deleteTaskById(-1)
        );
    }

    // epics
    @Test
    public void Should_ListOfEpicAndNotNull_When_GetAllEpics() {
        when(epicStub1.getId()).thenReturn(1);
        taskRepositoryService.createEpic(epicStub1);
        assertEquals(1, taskRepositoryService.getAllEpics().size());
        assertNotNull(taskRepositoryService.getAllEpics());
    }

    @Test
    public void Should_ClearAllEpicsAndSubTasks_When_RemoveAllByTypeEpic() {
        when(epicStub1.getId()).thenReturn(1);
        when(epicStub2.getId()).thenReturn(2);

        taskRepositoryService.createEpic(epicStub1);
        taskRepositoryService.createEpic(epicStub2);

        taskRepositoryService.removeAllByType(TaskType.EPIC);
        assertEquals(0, taskRepositoryService.getAllEpics().size());
        assertEquals(0, taskRepositoryService.getAllSubTasks().size());
    }

    @Test
    public void Should_ReturnNull_When_GetEpicByNonExistentId() {
        assertNull(taskRepositoryService.getEpicById(-1));
    }

    @Test
    public void Should_CreateEpics_When_CreateEpic() {
        when(epicStub1.getId()).thenReturn(1);
        when(epicStub2.getId()).thenReturn(2);

        taskRepositoryService.createEpic(epicStub1);
        taskRepositoryService.createEpic(epicStub2);

        assertEquals(epicStub1, taskRepositoryService.getEpicById(1));
        assertEquals(epicStub2, taskRepositoryService.getEpicById(2));
    }

    @Test
    public void Should_GetUpdatedEpic_When_UpdateEpic() {
        when(epicStub1.getId()).thenReturn(1);
        when(epicStub1.getName()).thenReturn("original");

        taskRepositoryService.createEpic(epicStub1);
        assertEquals("original", taskRepositoryService.getEpicById(1).getName());

        when(epicStub1.getName()).thenReturn("updated");
        taskRepositoryService.updateEpic(epicStub1);
        assertEquals("updated", taskRepositoryService.getEpicById(1).getName());
    }

    @Test
    public void Should_RemoveEpic_When_DeleteEpicByExistentId() {
        when(epicStub1.getId()).thenReturn(1);
        taskRepositoryService.createEpic(epicStub1);

        assertEquals(1, taskRepositoryService.getAllEpics().size());
        taskRepositoryService.deleteEpicById(1);
        assertEquals(0, taskRepositoryService.getAllEpics().size());
    }

    @Test
    public void Should_RemoveEpic_When_DeleteEpicByNonExistentId() {
        assertDoesNotThrow(
                () -> taskRepositoryService.deleteEpicById(-1)
        );
    }

    // subtasks
    @Test
    public void Should_ListOfSubtasksAndNotNull_When_GetAllSubTasks() {
        when(subTask1Stub.getId()).thenReturn(1);
        when(subTask2Stub.getId()).thenReturn(2);

        taskRepositoryService.createSubTask(subTask1Stub);
        taskRepositoryService.createSubTask(subTask2Stub);

        assertEquals(2, taskRepositoryService.getAllSubTasks().size());
        assertNotNull(taskRepositoryService.getAllSubTasks());
    }

    @Test
    public void Should_ClearAllSubTasks_When_RemoveAllByTypeSubTask() {
        when(subTask1Stub.getId()).thenReturn(1);

        taskRepositoryService.createSubTask(subTask1Stub);

        taskRepositoryService.removeAllByType(TaskType.SUB_TASK);
        assertEquals(0, taskRepositoryService.getAllSubTasks().size());
    }

    @Test
    public void Should_ReturnNull_When_GetSubTaskByNonExistentId() {
        assertNull(taskRepositoryService.getSubTaskById(-1));
    }

    @Test
    public void Should_CreateSubtasks_When_CreateSubTask() {
        when(subTask1Stub.getId()).thenReturn(1);
        when(subTask2Stub.getId()).thenReturn(2);

        taskRepositoryService.createSubTask(subTask1Stub);
        taskRepositoryService.createSubTask(subTask2Stub);

        assertEquals(subTask1Stub, taskRepositoryService.getSubTaskById(1));
        assertEquals(subTask2Stub, taskRepositoryService.getSubTaskById(2));
    }

    @Test
    public void Should_GetUpdatedSubTask_When_UpdateSubTask() {
        when(subTask1Stub.getId()).thenReturn(1);
        when(subTask1Stub.getName()).thenReturn("original");

        taskRepositoryService.createSubTask(subTask1Stub);
        assertEquals("original", taskRepositoryService.getSubTaskById(1).getName());

        when(subTask1Stub.getName()).thenReturn("updated");
        taskRepositoryService.updateSubTask(subTask1Stub);
        assertEquals("updated", taskRepositoryService.getSubTaskById(1).getName());
    }

    @Test
    public void Should_RemoveSubTask_When_DeleteSubTaskByExistentId() {
        when(subTask1Stub.getId()).thenReturn(1);
        taskRepositoryService.createSubTask(subTask1Stub);

        assertEquals(1, taskRepositoryService.getAllSubTasks().size());
        taskRepositoryService.deleteSubTaskById(1);
        assertEquals(0, taskRepositoryService.getAllSubTasks().size());
    }

    @Test
    public void Should_RemoveSubTask_When_DeleteSubTaskByNonExistentId() {
        assertDoesNotThrow(
                () -> taskRepositoryService.deleteSubTaskById(-1)
        );
    }

    // other

    @Test
    public void Should_ReturnListOfEpics_When_GetAllSubTasksOfEpic() {
        List<SubTask> subTasksList = List.of(subTask1Stub, subTask2Stub);
        when(epicStub1.getSubTasks()).thenReturn(subTasksList);

        assertEquals(subTasksList, taskRepositoryService.getAllSubTasksOfEpic(epicStub1));
    }

}