package repositories;

import tasks.AbstractTask;

import java.util.List;

public interface TaskHistoryRepository {
    void add(AbstractTask task);

    List<AbstractTask> history();
}
