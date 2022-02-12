package tasks;

public class SubTask extends AbstractTask{
    Epic parent;

    public SubTask(String name, String description, Epic parent) {
        super(name, description, TaskStatus.NEW);
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
}
