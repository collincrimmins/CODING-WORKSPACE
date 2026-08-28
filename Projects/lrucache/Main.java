package Projects.lrucache;

public class Main {
    /*
        Design a Cache (or LRU Cache)

        Requirements
        - Put, Get, Delete by Key & Value (String : String)
        - Remove Elements using a Strategy (in this case, 100 size limit in an LRU)

        Entities
        - Cache
        - LRU Cache (w/ Node private class)
        - (Optional) DoublyLinkedList - or just build directly into LRU cache
    */
    public static void main(String[] args) {
        // LRUCache
        Cache<String, String> cache = new LRUCache<>(100);

        System.out.println(cache.get("100"));
        cache.put("100", "hello 100");
        System.out.println(cache.get("100"));
        cache.delete("100");
        System.out.println(cache.get("100"));

        for (int i = 1; i <= 101; i++) {
            cache.put(String.valueOf(i), String.valueOf(i));
        }
        System.out.println("This Value should be 'null':");
        System.out.println(cache.get("1")); // null
        System.out.println("This Value should be '101':");
        System.out.println(cache.get("101")); // 101
    } 
}
