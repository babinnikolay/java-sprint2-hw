package tasks;

public class Task extends AbstractTask {

    public Task(String name, String description) {
        super(name, description, TaskStatus.NEW);
        type = TaskType.TASK;
    }

}
