package repositories.domain;

import tasks.AbstractTask;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;


public class TaskHistoryList {
    private Node head;
    private Node last;
    private final Map<Integer, Node> nodes;

    public TaskHistoryList() {
        nodes = new HashMap<>();
    }

    public void linkLast(AbstractTask task) {
        if (nodes.containsKey(task.getId())) {
            remove(task.getId());
        }
        Node tmpNode = this.last;
        Node newNode = new Node(tmpNode, task, null);
        this.last = newNode;
        if (head == null) {
            this.head = newNode;
        } else {
            tmpNode.next = newNode;
        }
        nodes.put(task.getId(), newNode);
    }

    public void remove(int id) {
        if (!nodes.containsKey(id)) {
            throw new NoSuchElementException();
        }
        Node node = nodes.get(id);
        Node prev = node.prev;
        Node next = node.next;
        if (prev != null && next != null) {
            prev.next = next;
            next.prev = prev;
        }
        if (prev == null) {
            next.prev = null;
            head = next;
        }
        if (next == null) {
            prev.next = null;
            last = prev;
        }
        nodes.remove(id);
    }

    public List<AbstractTask> getHistory() {
        List<AbstractTask> result = new ArrayList<>();
        Node node = this.head;
        while (node != null) {
            result.add(node.task);
            node = node.next;
        }
        return result;
    }

    private static class Node {
        private Node prev;
        private final AbstractTask task;
        private Node next;

        public Node(Node prev, AbstractTask task, Node next) {
            this.prev = prev;
            this.task = task;
            this.next = next;
        }
    }
}
