package services;

import tasks.AbstractTask;

import java.util.List;

public interface HistoryManager {
    void add(AbstractTask task);
    void remove(int id);
    List<AbstractTask> getHistory();
}
