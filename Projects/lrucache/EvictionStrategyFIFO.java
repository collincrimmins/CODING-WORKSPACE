package Projects.lrucache;

import java.util.LinkedList;
import java.util.Queue;

public class EvictionStrategyFIFO<K, V> implements EvictionStrategy<K, V> {
    private final Queue<K> queue = new LinkedList<>();

    @Override
    public void keyAccessed(K key) {
        // FIFO does not modify on read
    }

    @Override
    public void keyAdded(K key, V value) {
        if (!queue.contains(key)) {
            queue.add(key);
        }
    }

    @Override
    public void keyRemove(K key) {
        queue.remove(key);
    }

    @Override
    public K evictKey() {
        return queue.poll();
    }

    @Override
    public void printCacheDatastructure() {

    }
    
}
