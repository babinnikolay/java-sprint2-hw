package repositories.domain;

import tasks.AbstractTask;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;


public class TaskHistoryList implements Serializable {
    private Node head;
    private Node last;
    private final Map<Integer, Node> nodes;

    public TaskHistoryList(Map<Integer, Node> nodes) {
        this.nodes = nodes;
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
        if (nodes.containsKey(id)) {
            Node node = nodes.get(id);
            Node prev = node.getPrev();
            Node next = node.getNext();
            if (prev != null && next != null) {
                prev.setNext(next);
                next.setPrev(prev);
            }
            if (prev == null) {
                if (next != null) {
                    next.setPrev(null);
                }
                head = next;
            }
            if (next == null) {
                if (prev != null) {
                    prev.setNext(null);
                }
                last = prev;
            }
            nodes.remove(id);
        }
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

    private static class Node implements Serializable{
        private Node prev;
        private final AbstractTask task;

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        private Node next;

        public Node(Node prev, AbstractTask task, Node next) {
            this.prev = prev;
            this.task = task;
            this.next = next;
        }
    }
}
