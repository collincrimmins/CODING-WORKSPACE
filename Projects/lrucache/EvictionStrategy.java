package Projects.lrucache;

public interface EvictionStrategy<K, V> {
    void keyAccessed(K key);
    void keyAdded(K key, V value);
    void keyRemove(K key);
    K evictKey();
    void printCacheDatastructure();
}
