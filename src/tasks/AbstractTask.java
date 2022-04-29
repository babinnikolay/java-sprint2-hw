package tasks;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public abstract class AbstractTask implements Serializable {
    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    Duration duration;
    LocalDateTime startTime;

    protected AbstractTask(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusSeconds(duration.toSeconds());
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "AbstractTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
