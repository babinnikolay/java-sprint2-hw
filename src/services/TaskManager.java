package services;

import tasks.*;

import java.util.List;
import java.util.Set;

public interface TaskManager {

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<SubTask> getAllSubTasks();

    Set<AbstractTask> getPrioritizedTasks();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubTasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubTask(SubTask subTask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    void deleteTaskById(int id);

    void deleteSubTaskById(int id);

    void deleteEpicById(int id);

    List<SubTask> getAllSubTasksOfEpic(Epic epic);

    HistoryManager getHistoryManager();
}
