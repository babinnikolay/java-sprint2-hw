import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import services.HistoryManagerService;
import tasks.AbstractTask;
import tasks.Task;

import java.util.List;

public class HistoryManagerTest {

    @Mock
    HistoryManagerService historyManagerServiceStub;

    @Mock
    List<AbstractTask> listStub;

    @Mock
    Task taskStub;

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("HistoryManagerTest");
    }

    public HistoryManagerTest() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenGetHistoryFromHistoryManager_thenGetListOfHistory() {
        listStub.add(taskStub);
        Mockito.when(historyManagerServiceStub.getHistory()).thenReturn(listStub);
        Assert.assertEquals(listStub, historyManagerServiceStub.getHistory());
    }

    @Test
    public void whenAddToHistory_thenVerifyCallMethod() {
        historyManagerServiceStub.add(taskStub);
        Mockito.verify(historyManagerServiceStub).add(taskStub);
    }

}
