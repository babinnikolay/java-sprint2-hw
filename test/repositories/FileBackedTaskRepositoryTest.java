package repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.domain.FileBackedHelper;
import repositories.services.TaskRepositoryService;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FileBackedTaskRepositoryTest extends AbstractTaskRepositoryTest<FileBackedTaskRepository>{

    @Mock
    private FileBackedHelper fileBackedHelperStub;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskRepository = new FileBackedTaskRepository(serviceStub, fileBackedHelperStub);
    }

    @Override
    @Test
    public void shouldCallMethodCreateTasksWhenCreateTask() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).createTask(taskStub);

        taskRepository.createTask(taskStub);

        verify(serviceStub).createTask(taskStub);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldCreateEpicsWhenCreateEpic() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).createEpic(epicStub);

        taskRepository.createEpic(epicStub);

        verify(serviceStub).createEpic(epicStub);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldCreateSubtasksWhenCreateSubTask() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).createSubTask(subTaskStub);

        taskRepository.createSubTask(subTaskStub);

        verify(serviceStub).createSubTask(subTaskStub);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldGetUpdatedTaskWhenUpdateTask() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).updateTask(taskStub);

        taskRepository.updateTask(taskStub);

        verify(serviceStub).updateTask(taskStub);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldGetUpdatedEpicWhenUpdateEpic() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).updateEpic(epicStub);

        taskRepository.updateEpic(epicStub);

        verify(serviceStub).updateEpic(epicStub);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldGetUpdatedSubTaskWhenUpdateSubTask() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).updateSubTask(subTaskStub);

        taskRepository.updateSubTask(subTaskStub);

        verify(serviceStub).updateSubTask(subTaskStub);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldCallMethodDeleteTaskByIdWhenDeleteTaskById() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).deleteTaskById(1);

        taskRepository.deleteTaskById(1);

        verify(serviceStub).deleteTaskById(1);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldCallMethodDeleteEpicByIdWhenDeleteEpicById() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).deleteEpicById(1);

        taskRepository.deleteEpicById(1);

        verify(serviceStub).deleteEpicById(1);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Override
    @Test
    public void shouldCallMethodDeleteSubTaskByIdWhenDeleteSubTaskById() {
        doNothing().when(fileBackedHelperStub).save(serviceStub);
        doNothing().when(serviceStub).deleteSubTaskById(1);

        taskRepository.deleteSubTaskById(1);

        verify(serviceStub).deleteSubTaskById(1);
        verify(fileBackedHelperStub).save(serviceStub);
    }

    @Test
    public void shouldSaveToFileAndRestoreTasksWhenCreateTask() {
        Path filePath = Paths.get("resources", "fileTaskDB.ser");

        FileBackedHelper fileBackedHelper
                = new FileBackedHelper(filePath);

        taskRepository = new FileBackedTaskRepository(new TaskRepositoryService(), fileBackedHelper);
        Task task = new Task("name", "desc");
        task.setStartTime(LocalDateTime.MIN);
        task.setDuration(Duration.ZERO);

        taskRepository.createTask(task);

        assertTrue(Files.exists(filePath));

        taskRepository = new FileBackedTaskRepository(new TaskRepositoryService(), fileBackedHelper);
        taskRepository.loadFromPath();
        assertEquals(1, taskRepository.getAllTasks().size());
    }

}