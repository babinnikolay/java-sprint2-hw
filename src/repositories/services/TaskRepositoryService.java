package repositories.services;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskRepositoryService implements Serializable {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;
    private int lastId = 1;

    public TaskRepositoryService() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public int getNexId() {
        return lastId++;
    }

    public List<Task> getAllTasks() {
        return tasks.values()
                .stream()
                .map(Task.class::cast)
                .collect(Collectors.toList());
    }

    public List<Epic> getAllEpics() {
        return epics.values()
                .stream()
                .map(Epic.class::cast)
                .collect(Collectors.toList());
    }

    public List<SubTask> getAllSubTasks() {
        return subTasks.values()
                .stream()
                .map(SubTask.class::cast)
                .collect(Collectors.toList());
    }

    public void removeAllByType(TaskType type) {
        switch (type) {
            case EPIC:
                epics.clear();
                break;
            case SUB_TASK:
                subTasks.clear();
                break;
            case TASK:
                tasks.clear();
                break;
        }
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {

        return epics.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void createSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    public void deleteSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            subTasks.remove(id);
        }
    }

    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
        }
    }

    public List<SubTask> getAllSubTasksOfEpic(Epic epic) {
        return epic.getSubTasks();
    }
}
