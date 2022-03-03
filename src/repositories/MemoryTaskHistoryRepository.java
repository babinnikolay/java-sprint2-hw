package repositories;

import tasks.AbstractTask;

import java.util.LinkedList;
import java.util.List;

public class MemoryTaskHistoryRepository implements TaskHistoryRepository{
    private final List<AbstractTask> taskHistory;
    private static final int QUEUE_SIZE = 10;

    public MemoryTaskHistoryRepository() {
        taskHistory = new LinkedList<>();
    }

    @Override
    public void add(AbstractTask task) {
        taskHistory.add(task);
        if (taskHistory.size() > QUEUE_SIZE) {
            taskHistory.remove(0);
        }
    }

    @Override
    public List<AbstractTask> history() {
        return taskHistory;
    }
}
