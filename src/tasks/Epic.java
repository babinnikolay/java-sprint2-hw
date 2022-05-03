package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends AbstractTask {
    private final List<SubTask> subTasks;
    private LocalDateTime endTime;

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
        updateTimeFields();
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    private void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void updateTimeFields() {
        if (subTasks.isEmpty()) {
            setDuration(Duration.ZERO);
            setStartTime(null);
            setEndTime(null);
            return;
        }
        Duration totalDuration = Duration.ZERO;
        for (SubTask subTask : subTasks) {
            if (getStartTime() == null) {
                setStartTime(subTask.getStartTime());
            }
            if (getEndTime() == null) {
                setEndTime(subTask.getEndTime());
            }
            if (subTask.getStartTime().isBefore(getStartTime())) {
                setStartTime(subTask.getStartTime());
            }
            if (subTask.getEndTime().isAfter(getEndTime())) {
                setEndTime(subTask.getEndTime());
            }
            totalDuration.plus(subTask.getDuration());
        }
        setDuration(totalDuration);
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
