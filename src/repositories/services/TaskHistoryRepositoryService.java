package repositories.services;

import repositories.domain.TaskHistoryList;
import tasks.AbstractTask;

import java.io.Serializable;
import java.util.List;

public class TaskHistoryRepositoryService implements Serializable {
    private final TaskHistoryList taskHistory;

    public TaskHistoryRepositoryService() {
        taskHistory = new TaskHistoryList();
    }

    public void add(AbstractTask task) {
        taskHistory.linkLast(task);
    }

    public void remove(int id) {
        taskHistory.remove(id);
    }

    public List<AbstractTask> history() {
        return taskHistory.getHistory();
    }
}
