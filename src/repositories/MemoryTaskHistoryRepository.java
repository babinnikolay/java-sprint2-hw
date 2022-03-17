package repositories;

import repositories.domain.TaskHistoryList;
import tasks.AbstractTask;

import java.util.List;

public class MemoryTaskHistoryRepository implements TaskHistoryRepository{
    private final TaskHistoryList taskHistory;

    public MemoryTaskHistoryRepository() {
        taskHistory = new TaskHistoryList();
    }

    @Override
    public void add(AbstractTask task) {
        taskHistory.linkLast(task);
    }

    @Override
    public void remove(int id) {
        taskHistory.remove(id);
    }

    @Override
    public List<AbstractTask> history() {
        return taskHistory.getHistory();
    }

}
