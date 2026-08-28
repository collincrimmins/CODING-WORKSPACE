package Projects.lrucache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRUCache<KeyType, ValueType> {
    // Cache
    private final int capacity;
    private final Map<KeyType, Node> map;
    // LinkedList
    private final Node head; // Sentinel Head (newest)
    private final Node tail; // Sentinel Tail (oldest)

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Invalid capacity");
        }

        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        this.head.next = tail;
        this.tail.prev = head;
    }

    // LRUCache
    
    public synchronized ValueType get(KeyType key) {
        // Check Exists
        if (!map.containsKey(key)) {
            return null;
        }

        // Get Node
        Node node = map.get(key);

        // Move to Front
        moveToFront(node.key);

        return node.value;
    }
    
    public synchronized void put(KeyType key, ValueType value) {
        if (map.containsKey(key)) {
            remove(key);
        }

        // Add to Head
        add(key, value);

        // Remove Greater Than Capacity
        if (map.size() > capacity) {
            KeyType lruKey = tail.prev.key;
            remove(lruKey);
        }
    }

    public synchronized void delete(KeyType key) {
        if (map.containsKey(key)) {
            remove(key);
        }
    }

    public synchronized int size() {
        return map.size();
    }

    public synchronized void printLinkedList() {
        Node current = head.next;
        String path = "";

        while (current != tail) {
            path = path + " (" + current.key + " : " + current.value + ") --->";

            current = current.next;
        }

        System.out.println("");
        System.out.println("=== LinkedList ===");
        System.out.println(path);
    }

    // Linked List

    private Node add(KeyType key, ValueType value) {
        Node node = new Node(key, value);

        // Add to Head
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;

        // Add to Map
        map.put(node.key, node);

        return node;
    }

    private void remove(KeyType key) {
        Node node = map.get(key);

        // Remove from LinkedList
        node.prev.next = node.next;
        node.next.prev = node.prev;

        // Remove from Map
        map.remove(node.key);
    }

    private void moveToFront(KeyType key) {
        Node node = map.get(key);

        // Remove from LinkedList
        node.prev.next = node.next;
        node.next.prev = node.prev;

        // Add to Head
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private class Node {
        KeyType key;
        ValueType value;
        Node prev;
        Node next;

        public Node(KeyType key, ValueType value) {
            this.key = key;
            this.value = value;
        }
    }
}
