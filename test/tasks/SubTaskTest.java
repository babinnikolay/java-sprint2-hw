package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubTaskTest {

    @Mock
    Epic epicStub;

    SubTask subTask;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subTask = new SubTask("Name", "Desc", epicStub);
    }

    @Test
    public void Should_ReturnEpic_When_GetParent() {
        assertEquals(epicStub, subTask.getParent());
        assertNotNull(subTask.getParent());
    }

    @Test
    public void Should_HaveStatusDone_When_SetStatusDone() {
        when(epicStub.getStatus()).thenReturn(TaskStatus.DONE);

        subTask.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, subTask.getStatus());
        assertEquals(TaskStatus.DONE, subTask.getParent().getStatus());
    }

    @Test
    public void Should_ContainsSubStringSubTask_When_ToString() {
        assertTrue(subTask.toString().contains("SubTask"));
    }

}