import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10;
    private final Map<Integer, Node<Task>> nodeMap = new HashMap<>(); // Для быстрого доступа к узлам
    private Node<Task> head;
    private Node<Task> tail;

    @Override
    public void add(Task task) {
        if (task == null) return;
        if (nodeMap.containsKey(task.getId())) {
            removeNode(nodeMap.get(task.getId()));
        }
        linkLast(task);

        if (nodeMap.size() > HISTORY_LIMIT) {
            removeNode(head);
        }
    }

    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            removeNode(nodeMap.get(id));
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            history.add(current.data);
            current = current.next;
        }
        return history;
    }


    private void linkLast(Task task) {
        Node<Task> newNode = new Node<>(task);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        nodeMap.put(task.getId(), newNode);
    }


    private void removeNode(Node<Task> node) {
        if (node == null) return;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        nodeMap.remove(node.data.getId());
    }
}
