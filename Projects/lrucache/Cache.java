package Projects.lrucache;

import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {
    private final int capacity;
    private final Map<K, V> map;
    private final EvictionStrategy<K, V> evictionStrategy;

    public Cache(int capacity, EvictionStrategy<K, V> evictionStrategy) {
        // Invalid Capacity
        if (capacity <= 0) {
            throw new IllegalArgumentException("Invalid capacity");
        }

        // Invalid EvictionStrategy
        if (evictionStrategy == null) {
            throw new IllegalArgumentException("Invalid eviction strategy");
        }

        this.capacity = capacity;
        this.map = new HashMap<>();
        this.evictionStrategy = evictionStrategy;
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }

        evictionStrategy.keyAccessed(key);

        return map.get(key);
    }

    public void put(K key, V value) {
        // Already Exists
        if (map.containsKey(key)) {
            map.put(key, value);
            evictionStrategy.keyAccessed(key);
            return;
        }

        // Add
        map.put(key, value);
        evictionStrategy.keyAdded(key, value);

        // Capacity
        if (map.size() > capacity) {
            K evictedKey = evictionStrategy.evictKey();
            if (evictedKey != null) {
                map.remove(evictedKey);
            }
        }
    }

    public V delete(K key) {
        if (map.containsKey(key)) {
            evictionStrategy.keyRemove(key);
            return map.remove(key);
        }
        return null;
    }

    public int size() {
        return map.size();
    }

    public int capacity() {
        return capacity;
    }

    public void printCacheDatastructure() {
        evictionStrategy.printCacheDatastructure();
    }
    
}
