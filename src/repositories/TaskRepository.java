package repositories;

import repositories.services.TaskRepositoryService;
import tasks.*;

import java.util.List;
import java.util.Set;

public interface TaskRepository {

    int getNextId();

    List<Task> getAllTasks();
    List<Epic> getAllEpics();
    List<SubTask> getAllSubTasks();

    Set<AbstractTask> getPrioritizedTasks();

    void removeAllByType(TaskType type);

    Task getTaskById(int id);
    Epic getEpicById(int id);
    SubTask getSubTaskById(int id);

    void createTask(Task task);
    void createEpic(Epic epic);
    void createSubTask(SubTask subTask);

    void updateTask(Task task);
    void updateSubTask(SubTask subTask);
    void updateEpic(Epic epic);

    void deleteTaskById(int id);
    void deleteSubTaskById(int id);
    void deleteEpicById(int id);

    List<SubTask> getAllSubTasksOfEpic(Epic epic);

}
