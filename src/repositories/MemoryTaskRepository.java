package repositories;

import tasks.*;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryTaskRepository implements TaskRepository{
    private final Map<Integer, Task> tasks;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;
    private int lastId = 1;

    public MemoryTaskRepository() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    @Override
    public int getNextId() {
        return lastId++;
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.values()
                .stream()
                .map(Task.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<Epic> getAllEpics() {
        return epics.values()
                .stream()
                .map(Epic.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return subTasks.values()
                .stream()
                .map(SubTask.class::cast)
                .collect(Collectors.toList());
    }

    @Override
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

    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            subTasks.remove(id);
        }
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
        }
    }

    @Override
    public List<SubTask> getAllSubTasksOfEpic(Epic epic) {
        return epic.getSubTasks();
    }

}
