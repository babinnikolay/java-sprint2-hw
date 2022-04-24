package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Task
    @Test
    public void Should_ReturnEmptyListOfTasks_When_TasksListIsEmpty() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        assertEquals(0, taskManager.getAllTasks().size());
        assertNotNull(taskManager.getAllTasks());
    }

    @Test
    public void Should_ReturnNotEmptyListOfTasks_When_CreateTask() {
        List<Task> taskList = List.of(taskStub);
        Mockito.when(taskRepositoryStub.getAllTasks()).thenReturn(taskList);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createTask(taskStub);
        assertEquals(1, taskManager.getAllTasks().size());
        assertNotNull(taskManager.getAllTasks());
    }

    @Test
    public void Should_CallRepositoryMethodRemoveAllByTypeTask_When_RemoveAllTasks() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.removeAllTasks();
        Mockito.verify(taskRepositoryStub).removeAllByType(TaskType.TASK);
    }

    @Test
    public void Should_ThrowException_When_GetNonExistentTaskId() {
        Mockito.when(taskRepositoryStub.getTaskById(10)).thenThrow(NullPointerException.class);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getTaskById(10),
                "non-existent id"
        );
    }

    @Test
    public void Should_CallRepositoryMethodUpdateTask_When_UpdateTask() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.updateTask(taskStub);
        Mockito.verify(taskRepositoryStub).updateTask(taskStub);
    }

    // Epics
    @Test
    public void Should_ReturnEmptyListOfEpics_When_EpicsListIsEmpty() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        assertEquals(0, taskManager.getAllEpics().size());
        assertNotNull(taskManager.getAllEpics());
    }

    @Test
    public void Should_ReturnNotEmptyListOfEpics_When_CreateEpic() {
        Epic epic = new Epic("EpicName", "EpicDesc");
        List<Epic> epicList = List.of(epic);
        Mockito.when(taskRepositoryStub.getAllEpics()).thenReturn(epicList);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createEpic(epic);
        assertEquals(1, taskManager.getAllEpics().size());
        assertNotNull(taskManager.getAllEpics());
    }

    @Test
    public void Should_CallRepositoryMethodRemoveAllByTypeEpicSubtask_When_RemoveAllEpics() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.removeAllEpics();
        Mockito.verify(taskRepositoryStub).removeAllByType(TaskType.EPIC);
        Mockito.verify(taskRepositoryStub).removeAllByType(TaskType.SUB_TASK);
    }

    @Test
    public void Should_ThrowException_When_GetNonExistentEpicId() {
        Mockito.when(taskRepositoryStub.getEpicById(10)).thenThrow(NullPointerException.class);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getEpicById(10),
                "non-existent id"
        );
    }

    @Test
    public void Should_ReturnStatusNew_When_CreateEpic() {
        Epic epic = new Epic("EpicName", "EpicDesc");
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createEpic(epic);
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void Should_ReturnStatusNew_When_EpicHaveAllNewSubtasks() {
        Epic epic = new Epic("EpicName", "EpicDesc");
        Mockito.when(subTask1Stub.getStatus()).thenReturn(TaskStatus.NEW);
        Mockito.when(subTask2Stub.getStatus()).thenReturn(TaskStatus.NEW);
        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createEpic(epic);
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void Should_ReturnStatusDone_When_EpicHaveAllDoneSubtasks() {
        Epic epic = new Epic("EpicName", "EpicDesc");
        Mockito.when(subTask1Stub.getStatus()).thenReturn(TaskStatus.DONE);
        Mockito.when(subTask2Stub.getStatus()).thenReturn(TaskStatus.DONE);
        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createEpic(epic);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    public void Should_ReturnStatusInProgress_When_EpicHaveDifferentStatusSubtasks() {
        Epic epic = new Epic("EpicName", "EpicDesc");
        Mockito.when(subTask1Stub.getStatus()).thenReturn(TaskStatus.NEW);
        Mockito.when(subTask2Stub.getStatus()).thenReturn(TaskStatus.DONE);
        epic.addSubTask(subTask1Stub);
        epic.addSubTask(subTask2Stub);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createEpic(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void Should_CallRepositoryMethodUpdateEpicAndEpicUpdateStatus_When_UpdateEpic() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.updateEpic(epicStub);
        Mockito.verify(taskRepositoryStub).updateEpic(epicStub);
        Mockito.verify(epicStub).updateStatus();
    }

    // Subtasks
    @Test
    public void Should_ReturnEmptyListOfSubTasks_When_SubTasksListIsEmpty() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        assertEquals(0, taskManager.getAllSubTasks().size());
        assertNotNull(taskManager.getAllSubTasks());
    }

    @Test
    public void Should_ReturnNotEmptyListOfSubTasks_When_CreateSubTask() {
        SubTask subTask = new SubTask("SubtaskName", "", epicStub);
        List<SubTask> subtaskList = List.of(subTask);
        Mockito.when(taskRepositoryStub.getAllSubTasks()).thenReturn(subtaskList);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createSubTask(subTask);
        assertEquals(1, taskManager.getAllSubTasks().size());
        assertNotNull(taskManager.getAllSubTasks());
    }

    @Test
    public void Should_CallRepositoryMethodRemoveAllByTypeSubTask_When_RemoveAllSubTasks() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.removeAllSubTasks();
        Mockito.verify(taskRepositoryStub).removeAllByType(TaskType.SUB_TASK);
    }

    @Test
    public void Should_ClearEpicsSubtaskList_When_RemoveAllSubTasks() {
        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(subTask1Stub);

        List<Epic> epicList = new ArrayList<>();
        epicList.add(epicStub);

        Mockito.when(taskRepositoryStub.getAllEpics()).thenReturn(epicList);
        Mockito.when(epicStub.getSubTasks()).thenReturn(subTaskList);

        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createEpic(epicStub);
        taskManager.removeAllSubTasks();
        Mockito.verify(epicStub).getSubTasks();
        Mockito.verify(epicStub).updateStatus();
        assertEquals(0, epicStub.getSubTasks().size());
    }

    @Test
    public void Should_ThrowException_When_GetNonExistentSubtaskId() {
        Mockito.when(taskRepositoryStub.getSubTaskById(10)).thenThrow(NullPointerException.class);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> taskManager.getSubTaskById(10),
                "non-existent id"
        );
    }

    @Test
    public void Should_ReturnNotNull_When_GetEpicOfSubtask() {
        SubTask subTask = new SubTask("SubtaskName", "", epicStub);
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        taskManager.createSubTask(subTask);
        assertEquals(epicStub, subTask.getParent());
        assertNotNull(subTask.getParent());
    }

    @Test
    public void Should_CallRepositoryMethodUpdateSubtaskAndParentEpicUpdateStatus_When_UpdateSubtask() {
        TaskManager taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
        Mockito.when(subTask1Stub.getParent()).thenReturn(epicStub);
        taskManager.updateSubTask(subTask1Stub);
        Mockito.verify(taskRepositoryStub).updateSubTask(subTask1Stub);
        Mockito.verify(subTask1Stub).getParent();
        Mockito.verify(epicStub).updateStatus();
    }

}