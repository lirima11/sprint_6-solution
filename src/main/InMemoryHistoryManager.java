package src.main;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;
    private final List<Task> history = new LinkedList<>();
    private final Map<Integer, Node<Task>> nodeMap = new HashMap<>(); // Для быстрого доступа к узлам
    private Node<Task> head; // Голова списка
    private Node<Task> tail; // Хвост списка

    @Override
    public void add(Task task) {
        if (task == null) return;

        // Удаляем старую запись, если она существует
        if (nodeMap.containsKey(task.getId())) {
            removeNode(nodeMap.get(task.getId()));
        }

        // Добавляем новый узел в конец списка
        linkLast(task);

        // Если превышен лимит, удаляем первый элемент
        if (nodeMap.size() > MAX_HISTORY_SIZE) {
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

    // Добавление задачи в конец списка
    private void linkLast(Task task) {
        Node<Task> newNode = new Node<>(task);

        if (tail == null) { // Если список пуст
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        nodeMap.put(task.getId(), newNode); // Обновляем Map
    }

    // Удаление узла
    private void removeNode(Node<Task> node) {
        if (node == null) return;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next; // Если удаляется голова
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev; // Если удаляется хвост
        }

        nodeMap.remove(node.data.getId()); // Удаляем из Map
    }
}
