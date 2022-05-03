package repositories;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import repositories.services.TaskRepositoryService;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

abstract class AbstractTaskRepositoryTest<T extends TaskRepository> {

    protected T taskRepository;

    @Mock
    protected Task taskStub;

    @Mock
    protected Epic epicStub;

    @Mock
    protected SubTask subTaskStub;

    @Mock
    protected TaskRepositoryService serviceStub;

    @Test
    public void shouldReturn1WhenGetNextIdInNewTaskRepository() {
        when(serviceStub.getNexId()).thenReturn(1);

        assertEquals(1, taskRepository.getNextId());
        verify(serviceStub).getNexId();
    }

    @Test
    public void shouldReturnTasksListWhenGetAllTasks() {
        when(serviceStub.getAllTasks()).thenReturn(new ArrayList<>());

        assertEquals(0, taskRepository.getAllTasks().size());
        verify(serviceStub).getAllTasks();
    }

    @Test
    public void shouldReturnTasksListWhenGetAllEpics() {
        when(serviceStub.getAllEpics()).thenReturn(new ArrayList<>());

        assertEquals(0, taskRepository.getAllEpics().size());
        verify(serviceStub).getAllEpics();
    }

    @Test
    public void shouldReturnTasksListWhenGetAllSubTasks() {
        when(serviceStub.getAllSubTasks()).thenReturn(new ArrayList<>());

        assertEquals(0, taskRepository.getAllSubTasks().size());
        verify(serviceStub).getAllSubTasks();
    }

    @Test
    public void shouldCallMethodCreateTasksWhenCreateTask() {
        taskRepository.createTask(taskStub);
        verify(serviceStub).createTask(taskStub);
    }

    @Test
    public void shouldCreateEpicsWhenCreateEpic() {
        taskRepository.createEpic(epicStub);
        verify(serviceStub).createEpic(epicStub);
    }

    @Test
    public void shouldCreateSubtasksWhenCreateSubTask() {
        taskRepository.createSubTask(subTaskStub);
        verify(serviceStub).createSubTask(subTaskStub);
    }

    @Test
    public void shouldGetUpdatedTaskWhenUpdateTask() {
        taskRepository.updateTask(taskStub);
        verify(serviceStub).updateTask(taskStub);
    }

    @Test
    public void shouldGetUpdatedEpicWhenUpdateEpic() {
        taskRepository.updateEpic(epicStub);
        verify(serviceStub).updateEpic(epicStub);
    }

    @Test
    public void shouldGetUpdatedSubTaskWhenUpdateSubTask() {
        taskRepository.updateSubTask(subTaskStub);
        verify(serviceStub).updateSubTask(subTaskStub);
    }


    @Test
    public void shouldCallMethodDeleteTaskByIdWhenDeleteTaskById() {
        taskRepository.deleteTaskById(1);
        verify(serviceStub).deleteTaskById(1);
    }

    @Test
    public void shouldCallMethodDeleteEpicByIdWhenDeleteEpicById() {
        taskRepository.deleteEpicById(1);
        verify(serviceStub).deleteEpicById(1);
    }

    @Test
    public void shouldCallMethodDeleteSubTaskByIdWhenDeleteSubTaskById() {
        taskRepository.deleteSubTaskById(1);
        verify(serviceStub).deleteSubTaskById(1);
    }

    @Test
    public void shouldCallMethodGetAllSubTasksOfEpicWhenGetAllSubTasksOfEpic() {
        assertEquals(0, taskRepository.getAllSubTasksOfEpic(epicStub).size());
        verify(serviceStub).getAllSubTasksOfEpic(epicStub);
    }


}