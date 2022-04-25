package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        task = new Task("Name", "Desc");
    }

    @Test
    public void Should_ReturnId_When_GetId() {
        task.setId(10);
        assertEquals(10, task.getId());
    }

    @Test
    public void Should_ReturnDesc_When_getDescription() {
        assertEquals("Desc", task.getDescription());
    }

    @Test
    public void Should_ReturnNewName_When_changeName() {
        task.setName("NewName");
        assertEquals("NewName", task.getName());
    }

    @Test
    public void Should_ReturnStatusDone_When_SetStatusDone() {
        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
    }

    @Test
    public void Should_ContainsSubStringAbstractTask_When_ToString() {
        assertTrue(task.toString().contains("AbstractTask"));
    }

}