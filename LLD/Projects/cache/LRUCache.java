import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private Node head;
    private Node tail;
    private Map<K, Node> map;

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }

        this.capacity = capacity;
        map = new HashMap<>();
        head = null;
        tail = null;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public V get(K key) {
        //System.out.println("get " + key);

        if (map.containsKey(key)) {
            // Readd to Head
            V value = remove(key);
            add(key, value);
            return value;
        } else {
            return null;
        }
    }

    @Override
    public void put(K key, V value) {
        //System.out.println("put " + key);

        // Remove Existing
        if (map.containsKey(key)) {
            remove(key);
        }

        add(key, value);
        
        //System.out.println(map.toString());

        if (map.size() > capacity) {
            remove(tail.key);
        }
    }

    @Override
    public V delete(K key) {
        if (map.get(key) == null) {
            return null;
        }

        V value = map.get(key).value;
        remove(key);
        return value;
    }

    void add(K key, V value) {
        Node node = new Node(key, value);

        if (head == null && tail == null) {
            head = node;
            tail = node;
            // Initialize List
        } else {
            // Add to Head
            head.prev = node;
            node.next = head;
            head = node;
        }

        // Add to Map
        map.put(key, node);

        //System.out.println(map.toString());
    }

    V remove(K key) {
        //System.out.println("removing " + key);

        // Remove from Middle of List
        Node removingThisNode = map.get(key);
        Node myPrev = removingThisNode.prev;
        Node myNext = removingThisNode.next;
        if (myPrev != null) {
            myPrev.next = removingThisNode.next;
        }
        if (myNext != null) {
            myNext.prev = removingThisNode.prev;
        }

        // Update Head & Tail
        if (removingThisNode == head) {
            head = removingThisNode.next;
        }
        if (removingThisNode == tail) {
            tail = removingThisNode.prev;
        }

        // Remove from Map
        map.remove(key);

        return removingThisNode.value;
    }

    private class Node {
        K key;
        V value;
        Node next;
        Node prev;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}