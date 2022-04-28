package repositories.services;

import exceptions.ManagerSaveException;
import tasks.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class TaskRepositoryService implements Serializable {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;
    private int lastId = 1;
    private Set<AbstractTask> prioritizedTasks;

    public TaskRepositoryService() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        prioritizedTasks = new TreeSet<AbstractTask>((t1, t2) -> {
            return t1.getStartTime().isAfter(t2.getStartTime()) ? 1 : -1;
        });
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
                prioritizedTasks.removeAll(epics.values());
                epics.clear();
                break;
            case SUB_TASK:
                prioritizedTasks.removeAll(subTasks.values());
                subTasks.clear();
                break;
            case TASK:
                prioritizedTasks.removeAll(tasks.values());
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
        if (taskNoTimeCrossing(task)) {
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        } else {
            throw new ManagerSaveException();
        }
    }

    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        prioritizedTasks.add(epic);
    }

    public void createSubTask(SubTask subTask) {
        if (taskNoTimeCrossing(subTask)) {
            subTasks.put(subTask.getId(), subTask);
            prioritizedTasks.add(subTask);
        } else {
            throw new ManagerSaveException();
        }
    }

    public void updateTask(Task task) {
        if (taskNoTimeCrossing(task)) {
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        } else {
            throw new ManagerSaveException();
        }
    }

    public void updateSubTask(SubTask subTask) {
        if (taskNoTimeCrossing(subTask)) {
            subTasks.put(subTask.getId(), subTask);
            prioritizedTasks.add(subTask);
        } else {
            throw new ManagerSaveException();
        }
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        prioritizedTasks.add(epic);
    }

    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            prioritizedTasks.remove(tasks.get(id));
            tasks.remove(id);
        }
    }

    public void deleteSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            prioritizedTasks.remove(subTasks.get(id));
            subTasks.remove(id);
        }
    }

    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            prioritizedTasks.remove(epics.get(id));
            epics.remove(id);
        }
    }

    public List<SubTask> getAllSubTasksOfEpic(Epic epic) {
        return epic.getSubTasks();
    }

    public Set<AbstractTask> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    private boolean taskNoTimeCrossing(AbstractTask taskToCheck) {
        for (AbstractTask task : prioritizedTasks) {
            LocalDateTime startTime = taskToCheck.getStartTime();
            if (startTime.isAfter(task.getStartTime()) && startTime.isBefore(task.getEndTime())) {
                return false;
            }
        }
        return true;
    }
}
