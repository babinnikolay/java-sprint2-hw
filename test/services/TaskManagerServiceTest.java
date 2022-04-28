package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import repositories.TaskRepository;
import tasks.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerServiceTest {

    @Mock
    TaskRepository taskRepositoryStub;

    @Mock
    HistoryManager historyManagerStub;

    @Mock
    Task taskStub;

    @Mock
    Epic epicStub;

    @Mock
    SubTask subTask1Stub;

    @Mock
    SubTask subTask2Stub;

    TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
    }

    // Task
    @Test
    public void Should_ReturnEmptyListOfTasks_When_TasksListIsEmpty() {
        assertEquals(0, taskManager.getAllTasks().size());
        assertNotNull(taskManager.getAllTasks());
    }

    @Test
    public void Should_ReturnNotEmptyListOfTasks_When_CreateTask() {
        List<Task> taskList = List.of(taskStub);
        when(taskRepositoryStub.getAllTasks()).thenReturn(taskList);
        taskManager.createTask(taskStub);
        assertEquals(1, taskManager.getAllTasks().size());
        assertNotNull(taskManager.getAllTasks());
    }

    @Test
    public void Should_ReturnTaskAndCallHistoryAdd_When_GetTaskById() {
        when(taskRepositoryStub.getTaskById(1)).thenReturn(taskStub);

        assertEquals(taskStub, taskManager.getTaskById(1));
        verify(historyManagerStub).add(taskStub);
    }

    @Test
    public void Should_ThrowException_When_GetNonExistentTaskId() {
        when(taskRepositoryStub.getTaskById(10)).thenThrow(NullPointerException.class);
        assertThrows(
                NullPointerException.class,
                () -> taskManager.getTaskById(10),
                "non-existent id"
        );
    }

    @Test
    public void Should_CallRepositoryMethodRemoveAllByTypeTask_When_RemoveAllTasks() {
        taskManager.removeAllTasks();
        verify(taskRepositoryStub).removeAllByType(TaskType.TASK);
    }

    @Test
    public void Should_CallRepositoryMethodUpdateTask_When_UpdateTask() {
        taskManager.updateTask(taskStub);
        verify(taskRepositoryStub).updateTask(taskStub);
    }

    @Test
    public void Should_CallRepositoryMethodDeleteTaskById_When_DeleteTaskById() {
        taskManager.deleteTaskById(taskStub.getId());
        verify(taskRepositoryStub).deleteTaskById(taskStub.getId());
    }

    @Test
    public void Should_ThrowException_When_DeleteTaskByNonExistentId() {
        doThrow(NullPointerException.class)
                .when(taskRepositoryStub)
                .deleteTaskById(10);

        assertThrows(NullPointerException.class,
                () -> taskManager.deleteTaskById(10),
                "non-existent id");
    }

    // Epics
    @Test
    public void Should_ReturnEmptyListOfEpics_When_EpicsListIsEmpty() {
        assertEquals(0, taskManager.getAllEpics().size());
        assertNotNull(taskManager.getAllEpics());
    }

    @Test
    public void Should_ReturnNotEmptyListOfEpics_When_CreateEpic() {
        List<Epic> epicList = List.of(epicStub);
        when(taskRepositoryStub.getAllEpics()).thenReturn(epicList);
        taskManager.createEpic(epicStub);
        assertEquals(1, taskManager.getAllEpics().size());
        assertNotNull(taskManager.getAllEpics());
    }

    @Test
    public void Should_CallRepositoryMethodRemoveAllByTypeEpicSubtask_When_RemoveAllEpics() {
        taskManager.removeAllEpics();
        verify(taskRepositoryStub).removeAllByType(TaskType.EPIC);
        verify(taskRepositoryStub).removeAllByType(TaskType.SUB_TASK);
    }

    @Test
    public void Should_ReturnEpicAndCallHistoryAdd_WhenGetEpicById() {
        when(taskRepositoryStub.getEpicById(1)).thenReturn(epicStub);

        assertEquals(epicStub, taskManager.getEpicById(1));
        verify(historyManagerStub).add(epicStub);
    }

    @Test
    public void Should_ThrowException_When_GetNonExistentEpicId() {
        when(taskRepositoryStub.getEpicById(10)).thenThrow(NullPointerException.class);
        assertThrows(
                NullPointerException.class,
                () -> taskManager.getEpicById(10),
                "non-existent id"
        );
    }

    @Test
    public void Should_CallEpicSetIdAndRepositoryCreateEpic_When_CreateEpic() {
        taskManager.createEpic(epicStub);
        verify(epicStub).setId(taskRepositoryStub.getNextId());
        verify(taskRepositoryStub).createEpic(epicStub);
    }

    @Test
    public void Should_CallRepositoryMethodUpdateEpicAndEpicUpdateStatus_When_UpdateEpic() {
        taskManager.updateEpic(epicStub);
        verify(taskRepositoryStub).updateEpic(epicStub);
        verify(epicStub).updateStatus();
    }

    @Test
    public void Should_CallRepositoryMethodDeleteEpicByIdAndDeleteSubtasks_When_DeleteEpicById() {
        List<SubTask> subTaskList = List.of(subTask1Stub);
        when(epicStub.getSubTasks()).thenReturn(subTaskList);
        when(taskRepositoryStub.getEpicById(epicStub.getId())).thenReturn(epicStub);

        taskManager.deleteEpicById(epicStub.getId());
        verify(taskRepositoryStub).deleteSubTaskById(subTask1Stub.getId());
        verify(taskRepositoryStub).deleteEpicById(epicStub.getId());
    }

    @Test
    public void Should_ThrowException_When_DeleteEpicByNonExistentId() {
        doThrow(NullPointerException.class)
                .when(taskRepositoryStub)
                .deleteEpicById(10);

        assertThrows(NullPointerException.class,
                () -> taskManager.deleteEpicById(10),
                "non-existent id");
    }

    // Subtasks
    @Test
    public void Should_ReturnEmptyListOfSubTasks_When_SubTasksListIsEmpty() {
        assertEquals(0, taskManager.getAllSubTasks().size());
        assertNotNull(taskManager.getAllSubTasks());
    }

    @Test
    public void Should_ReturnNotEmptyListOfSubTasks_When_CreateSubTask() {
        List<SubTask> subTaskList = List.of(subTask1Stub);
        when(taskRepositoryStub.getAllSubTasks()).thenReturn(subTaskList);
        when(subTask1Stub.getParent()).thenReturn(epicStub);
        taskManager.createSubTask(subTask1Stub);
        assertEquals(1, taskManager.getAllSubTasks().size());
        verify(subTask1Stub).setId(taskRepositoryStub.getNextId());
        verify(taskRepositoryStub).createSubTask(subTask1Stub);
    }

    @Test
    public void Should_CallRepositoryMethodRemoveAllByTypeSubTask_When_RemoveAllSubTasks() {
        taskManager.removeAllSubTasks();
        verify(taskRepositoryStub).removeAllByType(TaskType.SUB_TASK);
    }

    @Test
    public void Should_ClearEpicsSubtaskList_When_RemoveAllSubTasks() {
        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(subTask1Stub);

        List<Epic> epicList = new ArrayList<>();
        epicList.add(epicStub);

        when(taskRepositoryStub.getAllEpics()).thenReturn(epicList);
        when(epicStub.getSubTasks()).thenReturn(subTaskList);

        taskManager.createEpic(epicStub);
        taskManager.removeAllSubTasks();
        verify(epicStub).getSubTasks();
        verify(epicStub).updateStatus();
        assertEquals(0, epicStub.getSubTasks().size());
    }

    @Test
    public void Should_ReturnSubtaskAndHistoryAdd_When_GetSubtaskById() {
        when(taskRepositoryStub.getSubTaskById(1)).thenReturn(subTask1Stub);

        assertEquals(subTask1Stub, taskManager.getSubTaskById(1));
        verify(historyManagerStub).add(subTask1Stub);
    }

    @Test
    public void Should_ThrowException_When_GetNonExistentSubtaskId() {
        when(taskRepositoryStub.getSubTaskById(10)).thenThrow(NullPointerException.class);
        assertThrows(
                NullPointerException.class,
                () -> taskManager.getSubTaskById(10),
                "non-existent id"
        );
    }

    @Test
    public void Should_ReturnNotNull_When_GetEpicOfSubtask() {
        SubTask subTask = new SubTask("SubtaskName", "", epicStub);
        taskManager.createSubTask(subTask);
        assertEquals(epicStub, subTask.getParent());
        assertNotNull(subTask.getParent());
    }

    @Test
    public void Should_CallRepositoryMethodUpdateSubtaskAndParentEpicUpdateStatus_When_UpdateSubtask() {
        when(subTask1Stub.getParent()).thenReturn(epicStub);
        taskManager.updateSubTask(subTask1Stub);
        verify(taskRepositoryStub).updateSubTask(subTask1Stub);
        verify(subTask1Stub, times(2)).getParent();
        verify(epicStub).updateStatus();
    }

    @Test
    public void Should_RemoveFromEpicAndCallRepositoryMethodDeleteSubtaskById_When_DeleteSubtaskById() {
        List<SubTask> subTaskListStub = mock(List.class);
        subTaskListStub.add(subTask1Stub);
        when(taskRepositoryStub.getSubTaskById(subTask1Stub.getId())).thenReturn(subTask1Stub);
        when(subTask1Stub.getParent()).thenReturn(epicStub);
        when(epicStub.getSubTasks()).thenReturn(subTaskListStub);

        taskManager.deleteSubTaskById(subTask1Stub.getId());

        verify(epicStub).getSubTasks();
        verify(subTaskListStub).remove(subTask1Stub);
        verify(epicStub).updateStatus();
        verify(taskRepositoryStub).deleteSubTaskById(subTask1Stub.getId());
    }

    @Test
    public void Should_ReturnSubTaskList_When_GetAllSubTasksOfEpic() {
        List<SubTask> subTaskList = List.of(subTask1Stub, subTask2Stub);
        when(taskRepositoryStub.getAllSubTasksOfEpic(epicStub)).thenReturn(subTaskList);

        assertEquals(subTaskList, taskManager.getAllSubTasksOfEpic(epicStub));
    }

    // Other
    @Test
    public void Should_ReturnHistoryManager_When_GetHistoryManager() {
        assertEquals(historyManagerStub, taskManager.getHistoryManager());
    }

}