package repositories.services;

import exceptions.ManagerSaveException;
import tasks.*;

import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class TaskRepositoryService implements Serializable {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;
    private int lastId = 1;
    private Set<AbstractTask> prioritizedTasks;
    private Map<Long, Boolean> planingPeriods = new HashMap<>();
    private static final int MINUTES_INTERVAL = 15;

    public TaskRepositoryService() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        prioritizedTasks = new TreeSet<>((t1, t2) -> {
            if (t1.getStartTime().isAfter(t2.getStartTime())) {
                return 1;
            }
            if (t1.getStartTime().isBefore(t2.getStartTime())) {
                return -1;
            }
            return  0;
        });
        fillPlaningPeriods();

    }

    private void fillPlaningPeriods() {
        int year = LocalDateTime.now().getYear();
        LocalDateTime yearStart = LocalDateTime.of(year, 1, 1, 1, 1,1);
        LocalDateTime yearEnd = LocalDateTime.of(year, 12, 31, 23 , 59, 59);

        long totalMinutes = ChronoUnit.MINUTES.between(yearStart, yearEnd);
        long intervalCount = totalMinutes / MINUTES_INTERVAL;
        for (long i = 0; i < intervalCount; i++) {
            long timeKey = yearStart.plusMinutes(i * MINUTES_INTERVAL).toEpochSecond(ZoneOffset.UTC);
            planingPeriods.put(timeKey, false);
        }
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
                for (SubTask subTask : subTasks.values()) {
                    removePeriodsTaskFromPlaning(subTask);
                }
                subTasks.clear();
                break;
            case TASK:
                prioritizedTasks.removeAll(tasks.values());
                for (Task task : tasks.values()) {
                    removePeriodsTaskFromPlaning(task);
                }
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
            addPeriodsTaskToPlaning(task);
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
            addPeriodsTaskToPlaning(subTask);
        }
    }

    public void updateTask(Task task) {
        if (taskNoTimeCrossing(task)) {
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
            removePeriodsTaskFromPlaning(task);
            addPeriodsTaskToPlaning(task);
        }
    }

    public void updateSubTask(SubTask subTask) {
        if (taskNoTimeCrossing(subTask)) {
            subTasks.put(subTask.getId(), subTask);
            prioritizedTasks.add(subTask);
            removePeriodsTaskFromPlaning(subTask);
            addPeriodsTaskToPlaning(subTask);
        }
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        prioritizedTasks.add(epic);
    }

    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            prioritizedTasks.remove(tasks.get(id));
            removePeriodsTaskFromPlaning(tasks.get(id));
            tasks.remove(id);
        }
    }

    public void deleteSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            prioritizedTasks.remove(subTasks.get(id));
            removePeriodsTaskFromPlaning(subTasks.get(id));
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

        List<Long> timeKeys = getTimeKeysFromTask(taskToCheck);
        for (long timeKey : timeKeys) {
            if (Boolean.TRUE.equals(planingPeriods.get(timeKey))) {
                return false;
            }
        }
        return true;
    }

    private List<Long> getTimeKeysFromTask(AbstractTask taskToCheck) {

        List<Long> timeKeys = new ArrayList<>();
        taskToCheck.getStartTime().toEpochSecond(ZoneOffset.UTC);
        long totalMinutes = ChronoUnit.MINUTES.between(taskToCheck.getStartTime(),
                taskToCheck.getEndTime());
        long intervalCount = (totalMinutes / MINUTES_INTERVAL)
                + ((totalMinutes % MINUTES_INTERVAL) > 0 ? 1 : 0);

        long startTime = taskToCheck.getStartTime().toEpochSecond(ZoneOffset.UTC);
        long yearStart = LocalDateTime.of(
                LocalDateTime.now().getYear(), 1, 1, 1, 1, 1)
                .toEpochSecond(ZoneOffset.UTC);

        for (long i = 0; i < intervalCount; i++) {
            long differenceInMinutes = ((startTime - yearStart) / 60) + (i * MINUTES_INTERVAL);
            long interval = differenceInMinutes / MINUTES_INTERVAL;

            long timeKey = interval * MINUTES_INTERVAL * 60 + yearStart;
            timeKeys.add(timeKey);
        }
        return timeKeys;
    }

    private void addPeriodsTaskToPlaning(AbstractTask task) {
        List<Long> timeKeys = getTimeKeysFromTask(task);
        for (long timeKey : timeKeys) {
            if (planingPeriods.containsKey(timeKey)) {
                planingPeriods.put(timeKey, true);
            } else {
                throw new ManagerSaveException();
            }
        }
    }

    private void removePeriodsTaskFromPlaning(AbstractTask task) {
        List<Long> timeKeys = getTimeKeysFromTask(task);
        for (long timeKey : timeKeys) {
            if (planingPeriods.containsKey(timeKey)) {
                planingPeriods.put(timeKey, false);
            } else {
                throw new ManagerSaveException();
            }
        }
    }
}
