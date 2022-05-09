package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import repositories.AbstractTaskRepository;
import tasks.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerServiceTest {

    @Mock
    private AbstractTaskRepository taskRepositoryStub;

    @Mock
    private HistoryManager historyManagerStub;

    @Mock
    private Task taskStub;

    @Mock
    private Epic epicStub;

    @Mock
    private SubTask subTask1Stub;

    @Mock
    private SubTask subTask2Stub;

    private TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskManager = new TaskManagerService(taskRepositoryStub, historyManagerStub);
    }

    // Task
    @Test
    public void shouldReturnEmptyListOfTasksWhenTasksListIsEmpty() {
        assertEquals(0, taskManager.getAllTasks().size());
        assertNotNull(taskManager.getAllTasks());
    }

    @Test
    public void shouldReturnNotEmptyListOfTasksWhenCreateTask() {
        List<Task> taskList = List.of(taskStub);
        when(taskRepositoryStub.getAllTasks()).thenReturn(taskList);
        taskManager.createTask(taskStub);
        assertEquals(1, taskManager.getAllTasks().size());
        assertNotNull(taskManager.getAllTasks());
    }

    @Test
    public void shouldReturnTaskAndCallHistoryAddWhenGetTaskById() {
        when(taskRepositoryStub.getTaskById(1)).thenReturn(taskStub);

        assertEquals(taskStub, taskManager.getTaskById(1));
        verify(historyManagerStub).add(taskStub);
    }

    @Test
    public void shouldThrowExceptionWhenGetNonExistentTaskId() {
        when(taskRepositoryStub.getTaskById(10)).thenThrow(NullPointerException.class);
        assertThrows(
                NullPointerException.class,
                () -> taskManager.getTaskById(10),
                "non-existent id"
        );
    }

    @Test
    public void shouldCallRepositoryMethodRemoveAllByTypeTaskWhenRemoveAllTasks() {
        taskManager.removeAllTasks();
        verify(taskRepositoryStub).removeAllByType(TaskType.TASK);
    }

    @Test
    public void shouldCallRepositoryMethodUpdateTaskWhenUpdateTask() {
        taskManager.updateTask(taskStub);
        verify(taskRepositoryStub).updateTask(taskStub);
    }

    @Test
    public void shouldCallRepositoryMethodDeleteTaskByIdWhenDeleteTaskById() {
        taskManager.deleteTaskById(taskStub.getId());
        verify(taskRepositoryStub).deleteTaskById(taskStub.getId());
    }

    @Test
    public void shouldThrowExceptionWhenDeleteTaskByNonExistentId() {
        doThrow(NullPointerException.class)
                .when(taskRepositoryStub)
                .deleteTaskById(10);

        assertThrows(NullPointerException.class,
                () -> taskManager.deleteTaskById(10),
                "non-existent id");
    }

    // Epics
    @Test
    public void shouldReturnEmptyListOfEpicsWhenEpicsListIsEmpty() {
        assertEquals(0, taskManager.getAllEpics().size());
        assertNotNull(taskManager.getAllEpics());
    }

    @Test
    public void shouldReturnNotEmptyListOfEpicsWhenCreateEpic() {
        List<Epic> epicList = List.of(epicStub);
        when(taskRepositoryStub.getAllEpics()).thenReturn(epicList);
        taskManager.createEpic(epicStub);
        assertEquals(1, taskManager.getAllEpics().size());
        assertNotNull(taskManager.getAllEpics());
    }

    @Test
    public void shouldCallRepositoryMethodRemoveAllByTypeEpicSubtaskWhenRemoveAllEpics() {
        taskManager.removeAllEpics();
        verify(taskRepositoryStub).removeAllByType(TaskType.EPIC);
        verify(taskRepositoryStub).removeAllByType(TaskType.SUB_TASK);
    }

    @Test
    public void shouldReturnEpicAndCallHistoryAddWhenGetEpicById() {
        when(taskRepositoryStub.getEpicById(1)).thenReturn(epicStub);

        assertEquals(epicStub, taskManager.getEpicById(1));
        verify(historyManagerStub).add(epicStub);
    }

    @Test
    public void shouldThrowExceptionWhenGetNonExistentEpicId() {
        when(taskRepositoryStub.getEpicById(10)).thenThrow(NullPointerException.class);
        assertThrows(
                NullPointerException.class,
                () -> taskManager.getEpicById(10),
                "non-existent id"
        );
    }

    @Test
    public void shouldCallEpicSetIdAndRepositoryCreateEpicWhenCreateEpic() {
        taskManager.createEpic(epicStub);
        verify(epicStub).setId(taskRepositoryStub.getNextId());
        verify(taskRepositoryStub).createEpic(epicStub);
    }

    @Test
    public void shouldCallRepositoryMethodUpdateEpicAndEpicUpdateStatusWhenUpdateEpic() {
        taskManager.updateEpic(epicStub);
        verify(taskRepositoryStub).updateEpic(epicStub);
        verify(epicStub).updateStatus();
    }

    @Test
    public void shouldCallRepositoryMethodDeleteEpicByIdAndDeleteSubtasksWhenDeleteEpicById() {
        List<SubTask> subTaskList = List.of(subTask1Stub);
        when(epicStub.getSubTasks()).thenReturn(subTaskList);
        when(taskRepositoryStub.getEpicById(epicStub.getId())).thenReturn(epicStub);

        taskManager.deleteEpicById(epicStub.getId());
        verify(taskRepositoryStub).deleteSubTaskById(subTask1Stub.getId());
        verify(taskRepositoryStub).deleteEpicById(epicStub.getId());
    }

    @Test
    public void shouldThrowExceptionWhenDeleteEpicByNonExistentId() {
        doThrow(NullPointerException.class)
                .when(taskRepositoryStub)
                .deleteEpicById(10);

        assertThrows(NullPointerException.class,
                () -> taskManager.deleteEpicById(10),
                "non-existent id");
    }

    // Subtasks
    @Test
    public void shouldReturnEmptyListOfSubTasksWhenSubTasksListIsEmpty() {
        assertEquals(0, taskManager.getAllSubTasks().size());
        assertNotNull(taskManager.getAllSubTasks());
    }

    @Test
    public void shouldReturnNotEmptyListOfSubTasksWhenCreateSubTask() {
        List<SubTask> subTaskList = List.of(subTask1Stub);
        when(taskRepositoryStub.getAllSubTasks()).thenReturn(subTaskList);
        when(subTask1Stub.getParent()).thenReturn(epicStub);
        taskManager.createSubTask(subTask1Stub);
        assertEquals(1, taskManager.getAllSubTasks().size());
        verify(subTask1Stub).setId(taskRepositoryStub.getNextId());
        verify(taskRepositoryStub).createSubTask(subTask1Stub);
    }

    @Test
    public void shouldCallRepositoryMethodRemoveAllByTypeSubTaskWhenRemoveAllSubTasks() {
        taskManager.removeAllSubTasks();
        verify(taskRepositoryStub).removeAllByType(TaskType.SUB_TASK);
    }

    @Test
    public void shouldClearEpicsSubtaskListWhenRemoveAllSubTasks() {
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
    public void shouldReturnSubtaskAndHistoryAddWhenGetSubtaskById() {
        when(taskRepositoryStub.getSubTaskById(1)).thenReturn(subTask1Stub);

        assertEquals(subTask1Stub, taskManager.getSubTaskById(1));
        verify(historyManagerStub).add(subTask1Stub);
    }

    @Test
    public void shouldThrowExceptionWhenGetNonExistentSubtaskId() {
        when(taskRepositoryStub.getSubTaskById(10)).thenThrow(NullPointerException.class);
        assertThrows(
                NullPointerException.class,
                () -> taskManager.getSubTaskById(10),
                "non-existent id"
        );
    }

    @Test
    public void shouldReturnNotNullWhenGetEpicOfSubtask() {
        SubTask subTask = new SubTask("SubtaskName", "", epicStub);
        taskManager.createSubTask(subTask);
        assertEquals(epicStub, subTask.getParent());
        assertNotNull(subTask.getParent());
    }

    @Test
    public void shouldCallRepositoryMethodUpdateSubtaskAndParentEpicUpdateStatusWhenUpdateSubtask() {
        when(subTask1Stub.getParent()).thenReturn(epicStub);
        taskManager.updateSubTask(subTask1Stub);
        verify(taskRepositoryStub).updateSubTask(subTask1Stub);
        verify(subTask1Stub, times(2)).getParent();
        verify(epicStub).updateStatus();
    }

    @Test
    public void shouldRemoveFromEpicAndCallRepositoryMethodDeleteSubtaskByIdWhenDeleteSubtaskById() {
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
    public void shouldReturnSubTaskListWhenGetAllSubTasksOfEpic() {
        List<SubTask> subTaskList = List.of(subTask1Stub, subTask2Stub);
        when(taskRepositoryStub.getAllSubTasksOfEpic(epicStub)).thenReturn(subTaskList);

        assertEquals(subTaskList, taskManager.getAllSubTasksOfEpic(epicStub));
    }

    // Other
    @Test
    public void shouldReturnHistoryManagerWhenGetHistoryManager() {
        assertEquals(historyManagerStub, taskManager.getHistoryManager());
    }

}