package Projects.lrucache.old;

public interface Cache3<K, V> {
    V get(K key);
    void put(K key, V value);
    V delete(K key);
    int size();
    int capacity();
}
