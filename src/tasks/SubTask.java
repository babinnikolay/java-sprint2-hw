package tasks;

public class SubTask extends AbstractTask{
    private Epic parent;

    public SubTask(String name, String description, Epic parent) {
        super(name, description, TaskStatus.NEW);
        this.parent = parent;
        type = TaskType.SUB_TASK;
    }

    public void setParent(Epic parent) {
        this.parent = parent;
    }

    public Epic getParent() {
        return parent;
    }

    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
        parent.updateStatus();
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", id=" + this.getId() +
                ", status=" + this.getStatus() +
                ", parentId=" + this.parent.getId() +
                '}';
    }
}
