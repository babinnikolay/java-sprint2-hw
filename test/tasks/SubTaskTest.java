package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubTaskTest {

    @Mock
    private Epic epicStub;

    private SubTask subTask;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subTask = new SubTask("Name", "Desc", epicStub);
    }

    @Test
    public void shouldReturnEpicWhenGetParent() {
        assertEquals(epicStub, subTask.getParent());
        assertNotNull(subTask.getParent());
    }

    @Test
    public void shouldHaveStatusDoneWhenSetStatusDone() {
        when(epicStub.getStatus()).thenReturn(TaskStatus.DONE);

        subTask.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, subTask.getStatus());
        assertEquals(TaskStatus.DONE, subTask.getParent().getStatus());
    }

    @Test
    public void shouldContainsSubStringSubTaskWhenToString() {
        assertTrue(subTask.toString().contains("SubTask"));
    }

}