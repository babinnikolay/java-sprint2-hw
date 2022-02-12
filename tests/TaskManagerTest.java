import org.junit.*;
import static org.junit.Assert.*;
import repositories.MemoryTaskRepository;
import taskmanager.TaskManager;
import tasks.*;

import java.util.List;

public class TaskManagerTest {

    TaskManager taskManager;

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("TaskManagerTest");
    }

    public TaskManagerTest() {
    }

    @Before
    public void setUp() {
        taskManager = new TaskManager(new MemoryTaskRepository());

        Task task = new Task("task", "desc");
        taskManager.createTask(task);

        Epic epic = new Epic("epic", "desc");
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask("subtask", "desc", epic);
        taskManager.createSubTask(subTask);
    }

    @Test
    public void getAllTasks() {
        assertEquals("task", taskManager.getAllTasks().get(0).getName());
    }

    @Test
    public void getAllEpics() {
        assertEquals("epic", taskManager.getAllEpics().get(0).getName());
    }

    @Test
    public void getAllSubTasks() {
        assertEquals("subtask", taskManager.getAllSubTasks().get(0).getName());
    }

    @Test
    public void createTask() {
        Task task = new Task("newtask", "desc");
        taskManager.createTask(task);
        assertEquals("newtask", taskManager.getTaskById(4).getName());
    }

    @Test
    public void createEpic() {
        Epic epic = new Epic("newepic", "desc");
        taskManager.createEpic(epic);
        assertEquals("newepic", taskManager.getEpicById(4).getName());
    }

    @Test
    public void createSubTask() {
        Epic epic = new Epic("name", "desc");
        SubTask subTask = new SubTask("newsubtask", "desc", epic);
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        assertEquals("newsubtask", taskManager.getSubTaskById(5).getName());
    }

    @Test
    public void updateTask() {
        Task task = taskManager.getTaskById(1);
        task.setName("updated task");
        taskManager.updateTask(task);
        assertEquals("updated task", taskManager.getTaskById(1).getName());
    }

    @Test
    public void updateSubTask() {
        SubTask subTask = taskManager.getSubTaskById(3);
        subTask.setName("updated subtask");
        taskManager.updateSubTask(subTask);
        assertEquals("updated subtask", taskManager.getSubTaskById(3).getName());
    }

    @Test
    public void updateEpic() {
        Epic epic = taskManager.getEpicById(2);
        epic.setName("updated epic");
        taskManager.updateEpic(epic);
        assertEquals("updated epic", taskManager.getEpicById(2).getName());
    }

    @Test
    public void updateEpicStatus() {
        Epic epic = taskManager.getEpicById(2);
        SubTask subTask = epic.getSubTasks().get(0);

        subTask.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, epic.getStatus());

        subTask.setStatus(TaskStatus.NEW);
        assertEquals(TaskStatus.NEW, epic.getStatus());

        subTask.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());

        taskManager.removeAllSubTasks();
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void deleteTaskById() {
        taskManager.deleteTaskById(1);
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void deleteSubTaskById() {
        taskManager.deleteSubTaskById(3);
        assertEquals(0, taskManager.getAllSubTasks().size());
    }

    @Test
    public void deleteEpicById() {
        taskManager.deleteEpicById(2);
        assertEquals(0, taskManager.getAllEpics().size());
    }

    @Test
    public void getAllSubTaskOfEpic() {
        Epic epic = taskManager.getEpicById(2);
        List<SubTask> allSubTaskOfEpic = taskManager.getAllSubTaskOfEpic(epic);
        assertEquals(1, allSubTaskOfEpic.size());
    }

    @Test
    public void removeAllTasks() {
        taskManager.removeAllTasks();
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void removeAllSubTasks() {
        taskManager.removeAllSubTasks();
        assertEquals(0, taskManager.getAllSubTasks().size());
    }

    @Test
    public void removeAllEpics() {
        taskManager.removeAllEpics();
        assertEquals(0, taskManager.getAllEpics().size());
    }

}