package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends AbstractTask {
    private final List<SubTask> subTasks;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        subTasks = new ArrayList<>();
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
        updateStatus();
    }

    public void updateStatus() {
        if (subTasks.isEmpty()) {
            setStatus(TaskStatus.NEW);
            return;
        }
        int newStatusCount = 0;
        int doneStatusCount = 0;
        for (SubTask subTask : subTasks) {
            switch (subTask.getStatus()) {
                case NEW:
                    newStatusCount++;
                    break;
                case DONE:
                    doneStatusCount++;
                    break;
                default:
            }
        }

        if (newStatusCount == subTasks.size()) {
            setStatus(TaskStatus.NEW);
        } else if (doneStatusCount == subTasks.size()) {
            setStatus(TaskStatus.DONE);
        } else {
            setStatus(TaskStatus.IN_PROGRESS);
        }

    }
}
