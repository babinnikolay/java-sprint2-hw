package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        task = new Task("Name", "Desc");
    }

    @Test
    public void shouldReturnIdWhenGetId() {
        task.setId(10);
        assertEquals(10, task.getId());
    }

    @Test
    public void shouldReturnDescWhenGetDescription() {
        assertEquals("Desc", task.getDescription());
    }

    @Test
    public void shouldReturnNewNameWhenChangeName() {
        task.setName("NewName");
        assertEquals("NewName", task.getName());
    }

    @Test
    public void shouldReturnStatusDoneWhenSetStatusDone() {
        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
    }

    @Test
    public void shouldContainsSubStringAbstractTaskWhenToString() {
        assertTrue(task.toString().contains("AbstractTask"));
    }

    @Test
    public void shouldReturnCalculateEndTimeWhenGetEndTime() {
        LocalDateTime controlEndTime
                = LocalDateTime.of(2022, 1, 1, 1, 1, 20);

        task.setStartTime(
                LocalDateTime.of(2022, 1, 1, 1, 1, 1));
        task.setDuration(Duration.ofSeconds(19));

        assertEquals(controlEndTime, task.getEndTime());
    }

    @Test
    public void shouldReturnTaskWhenGetType() {
        task.setType(TaskType.TASK);
        assertEquals(TaskType.TASK, task.getType());
    }

    @Test
    public void shouldReturnNewDescriptionWhenSetDescription() {
        task.setDescription("new desc");
        assertEquals("new desc", task.getDescription());
    }

    @Test
    public void shouldReturnNewDurationWhenSetDuration() {
        task.setDuration(Duration.ofSeconds(10));
        assertEquals(Duration.ofSeconds(10), task.getDuration());
    }

}