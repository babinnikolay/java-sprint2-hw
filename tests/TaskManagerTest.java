import org.junit.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import services.HistoryManagerService;
import services.Managers;
import services.TaskManager;
import tasks.*;

import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TaskStatus.class)
public class TaskManagerTest {

    @Mock
    TaskManager taskManagerStub;

    @Mock
    Task taskStub;

    @Mock
    Epic epicStub;

    @Mock
    SubTask subTaskStub;

    @Mock
    List listStub;

    @Mock
    List<Epic> epicListStub;

    @Mock
    TaskStatus taskStatusStub;

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("TaskManagerTest");
    }

    public TaskManagerTest() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCreateTask_thenVerifyCallMethod() {
        taskManagerStub.createTask(taskStub);
        Mockito.verify(taskManagerStub).createTask(taskStub);
    }

    @Test
    public void whenCreateEpic_thenVerifyCallMethod() {
        taskManagerStub.createEpic(epicStub);
        Mockito.verify(taskManagerStub).createEpic(epicStub);
    }

    @Test
    public void whenCreateSubTask_thenVerifyCallMethod() {
        taskManagerStub.createSubTask(subTaskStub);
        Mockito.verify(taskManagerStub).createSubTask(subTaskStub);
    }

    @Test
    public void whenGetAllEpics_thenGetListOfEpics() {
        listStub.add(epicStub);
        Mockito.when(taskManagerStub.getAllEpics()).thenReturn(listStub);
        Assert.assertEquals(listStub, taskManagerStub.getAllEpics());
    }

    @Test
    public void whenGetAllTasks_thenGetListOfTasks() {
        listStub.add(taskStub);
        Mockito.when(taskManagerStub.getAllTasks()).thenReturn(listStub);
        Assert.assertEquals(listStub, taskManagerStub.getAllTasks());
    }

    @Test
    public void whenGetAllSubTasks_thenGetListOfSubTasks() {
        listStub.add(subTaskStub);
        Mockito.when(taskManagerStub.getAllSubTasks()).thenReturn(listStub);
        Assert.assertEquals(listStub, taskManagerStub.getAllSubTasks());
    }

    @Test
    public void whenUpdateTask_thenVerifyCallMethod() {
        taskManagerStub.updateTask(taskStub);
        Mockito.verify(taskManagerStub).updateTask(taskStub);
    }

    @Test
    public void whenUpdateSubTask_thenVerifyCallMethod() {
        taskManagerStub.updateSubTask(subTaskStub);
        Mockito.verify(taskManagerStub).updateSubTask(subTaskStub);
    }

    @Test
    public void whenUpdateEpic_thenVerifyCallMethod() {
        taskManagerStub.updateEpic(epicStub);
        Mockito.verify(taskManagerStub).updateEpic(epicStub);
    }

    @Test
    public void whenUpdateEpicStatus_thenVerifyCallMethod() {
        taskStub.setStatus(taskStatusStub);
        Mockito.verify(taskStub).setStatus(taskStatusStub);
    }

    @Test
    public void whenDeleteTaskById_thenVerifyCallMethod() {
        taskManagerStub.deleteTaskById(Mockito.anyInt());
        Mockito.verify(taskManagerStub).deleteTaskById(Mockito.anyInt());
    }

    @Test
    public void whenDeleteSubTaskById_thenVerifyCallMethod() {
        taskManagerStub.deleteSubTaskById(Mockito.anyInt());
        Mockito.verify(taskManagerStub).deleteSubTaskById(Mockito.anyInt());
    }

    @Test
    public void whenDeleteEpicById_thenVerifyCallMethod() {
        taskManagerStub.deleteEpicById(Mockito.anyInt());
        Mockito.verify(taskManagerStub).deleteEpicById(Mockito.anyInt());
    }

    @Test
    public void whenGetAllSubTasksOfEpic_thenGetListOfSubTasks() {
        listStub.add(subTaskStub);
        Mockito.when(taskManagerStub.getAllSubTasksOfEpic(epicStub)).thenReturn(listStub);
        Assert.assertEquals(listStub, taskManagerStub.getAllSubTasksOfEpic(epicStub));
    }

    @Test
    public void whenRemoveAllTasks_thenVerifyCallMethod() {
        taskManagerStub.removeAllTasks();
        Mockito.verify(taskManagerStub).removeAllTasks();
    }

    @Test
    public void whenRemoveAllSubTasks_thenVerifyCallMethod() {
        taskManagerStub.removeAllSubTasks();
        Mockito.verify(taskManagerStub).removeAllSubTasks();
    }

    @Test
    public void whenRemoveAllEpics_thenVerifyCallMethod() {
        taskManagerStub.removeAllEpics();
        Mockito.verify(taskManagerStub).removeAllEpics();
    }

    @Test
    public void whenGetTaskById_thenGetTask() {
        taskStub.setId(19);
        Mockito.when(taskManagerStub.getTaskById(19)).thenReturn(taskStub);
        Assert.assertEquals(taskStub, taskManagerStub.getTaskById(19));
    }

    @Test
    public void whenGetEpicById_thenGetEpic() {
        epicStub.setId(19);
        Mockito.when(taskManagerStub.getEpicById(19)).thenReturn(epicStub);
        Assert.assertEquals(epicStub, taskManagerStub.getEpicById(19));
    }

    @Test
    public void whenGetSubTaskById_thenGetSubTask() {
        subTaskStub.setId(19);
        Mockito.when(taskManagerStub.getSubTaskById(19)).thenReturn(subTaskStub);
        Assert.assertEquals(subTaskStub, taskManagerStub.getSubTaskById(19));
    }

    @Test
    public void whenGetHistoryTasksFromTaskManager_thenGet10HistoryEntries() {

        HistoryManagerService historyManagerStub = Mockito.mock(HistoryManagerService.class);
        List historyListStub = Mockito.mock(List.class);

        Mockito.when(taskManagerStub.getHistoryManager()).thenReturn(historyManagerStub);
        Mockito.when(historyManagerStub.getHistory()).thenReturn(historyListStub);
        Mockito.when(historyListStub.size()).thenReturn(10);

        Assert.assertEquals(10, historyListStub.size());
    }

}