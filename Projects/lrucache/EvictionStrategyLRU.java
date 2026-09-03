package Projects.lrucache;

import java.util.HashMap;
import java.util.Map;

public class EvictionStrategyLRU<K, V> implements EvictionStrategy<K, V> {
    private final Node head; // Sentinel Head (newest)
    private final Node tail; // Sentinel Tail (oldest)
    private final Map<K, Node> map;

    public EvictionStrategyLRU() {
        this.map = new HashMap<>();
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        this.head.next = tail;
        this.tail.prev = head;
    }

    @Override
    public void keyAccessed(K key) {
        Node node = map.get(key);
        if (node != null) {
            moveToFront(node);
        }
    }

    @Override
    public void keyAdded(K key, V value) {
        // Already Exists
        if (map.containsKey(key)) {
            moveToFront(map.get(key));
            return;
        }

        // Create New
        Node node = new Node(key, value);
        add(node);
    }

    @Override
    public void keyRemove(K key) {
        Node node = map.get(key);
        if (node != null) {
            remove(node);
        }
    }

    @Override
    public K evictKey() {
        if (tail.prev == head) {
            return null;
        }

        Node node = tail.prev;
        remove(node);
        map.remove(node.key);
        
        return node.key;
    }

    // LinkedList

    private Node add(Node node) {
        // Add to Head
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;

        // Add to Map
        map.put(node.key, node);

        return node;
    }

    private void remove(Node node) {
        // Remove from LinkedList
        node.prev.next = node.next;
        node.next.prev = node.prev;

        // Remove from Map
        map.remove(node.key);
    }

    private void moveToFront(Node node) {
        // Remove from LinkedList
        node.prev.next = node.next;
        node.next.prev = node.prev;

        // Add to Head
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    @Override
    public void printCacheDatastructure() {
        Node current = head.next;
        String path = "";

        while (current != tail) {
            path = path + " (" + current.key + " : " + current.value + ") --->";

            current = current.next;
        }

        System.out.println("=== LinkedList ===");
        System.out.println(path);
    }
    
    private class Node {
        K key;
        V value;
        Node prev;
        Node next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
