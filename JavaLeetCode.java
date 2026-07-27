import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.tree.TreeNode;

public class JavaLeetCode {
    public static void main(String[] args) {
        /* Study Guide */
        /*
            BST
                Preorder = (ROOT), Left, Right
                Inorder = Left, (ROOT), Right
                Postorder = Left, Right, (ROOT)    
            DFS   
            BFS: Queue for each round
        
        */

        // Map
        Map<Character, Integer> map = new HashMap<>();
        // Set
        Set<Integer> set = new HashSet<>();
        Set<String> setWithInsertOrder = new LinkedHashSet<>();
        // Stack
        Stack<Integer> MyStack = new Stack<>();
        // Queue
        Queue<TreeNode> queue = new LinkedList<>();
        // Heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
            // Collections.reverseOrder()
            // (a, b) -> (a - b)
            // (ObjectA, ObjectB) -> Integer.compare(ObjectA.value, ObjectB.value)
        );
        

        // -------------------------
        // Arrays & Lists
        // -------------------------

        List<Integer> list = new ArrayList<>();

        list.add(val);                       // Appends element to end - O(1)
        list.add(index, val);                // Inserts at index (shifts elements) - O(N)
        list.get(index);                     // Returns element at index - O(1)
        list.set(index, val);                // Replaces element at index - O(1)
        list.remove(list.size() - 1);        // Efficient removal from end (Stack pop) - O(1)
        list.remove(index);                  // Removes at index (shifts elements) - O(N)
        list.size();                         // Number of elements
        List<Integer> list = Arrays.asList(1, 2, 3); // Immutable wrapper array
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3)); // Mutable list

        int[] arr = {3, 1, 4, 1, 5};
        Arrays.sort(arr);                    // Sorts primitive array in-place - O(N log N)
        Arrays.fill(arr, -1);                // Fills array with a single value - O(N)
        Arrays.binarySearch(arr, key);       // Returns index of key in SORTED array, or negative insertion point
        Arrays.equals(array1, array2)
        Collections.sort(list);              // Sorts List - O(N log N)
        Collections.reverse(list);           // Reverses List in-place - O(N)
        Collections.swap(list, i, j);        // Swaps elements at indices i and j
        Collections.max(list);               // Finds max element - O(N)

        // -------------------------
        // HashMap
        // -------------------------

        Map<Character, Integer> map = new HashMap<>();

        map.put(key, val);                   // Inserts or updates key-value pair - O(1)
        map.get(key);                        // Gets value, returns null if absent - O(1)
        map.containsKey(key);                // Checks if key exists - O(1)
        map.remove(key);                     // Removes key and its value - O(1)
        map.getOrDefault(key, 0);            // Returns value if present, else default (0)
        map.put(key, map.getOrDefault(key, 0) + 1); // Frequency map pattern
        map.clear()
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            char k = entry.getKey();
            int v = entry.getValue();
        }

        // -------------------------
        // HashSet
        // -------------------------

        Set<Integer> set = new HashSet<>();
        set.add(val);                        // Adds element (returns false if duplicate) - O(1)
        set.contains(val);                   // Checks if element exists - O(1)
        set.remove(val);                     // Removes element - O(1)
        MySet.forEach(value -> {
            System.out.println(value);
        });
        Set<Integer> MySet2 = MySet.stream()
                                .map(value -> value + 1)
                                .collect(Collectors.toSet());

        // -------------------------
        // Stack
        // -------------------------

        Stack<Integer> MyStack = new Stack<>();
        MyStack.add(1);
        MyStack.peek(); // Check Top
        MyStack.pop(); // Remove Top
        MyStack.toString();
        
        // -------------------------
        // Heap
        // -------------------------

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap.add(5); // throws exception if greater than arraysize
        minHeap.offer(5); // like .add but will resize array
        minHeap.peek(); // View top element
        minHeap.poll(); // Remove top element

        // -------------------------
        // Queue
        // -------------------------

        // QUEUE (FIFO) Use for BFS
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add();
        queue.offer(node);                       // Adds element to tail
        queue.poll();                            // Removes and returns element from head
        queue.peek();                            // Views element at head without removing
        queue.isEmpty();                         // Check size == 0

        // STACK (LIFO) DFS, Monotonic Stack
        // Deque<Integer> stack = new ArrayDeque<>();
        // stack.push(val);                     // Adds element to top
        // stack.pop();                         // Removes and returns top element
        // stack.peek();                        // Views top element
        // stack.isEmpty();                     // Check size == 0

        // -------------------------
        // Strings
        // -------------------------

        String s = "LeetCode";

        s.length();                          // 8
        s.charAt(i);                         // Character at index i
        s.substring(i, j);                   // Substring from index i to j-1
        s.indexOf("Leet");                   // Index of first occurrence (0), or -1
        s.lastIndexOf('e');                  // Index of last occurrence (7)
        s.toLowerCase(); s.toUpperCase();    // Case conversions
        s.trim();                            // Removes leading/trailing whitespace
        s.toCharArray();                     // Converts String -> char[]
        String str = String.valueOf(123);    // int -> String
        int num = Integer.parseInt("123");   // String -> int
    }

    // BST
    // Preorder = Node, Left, Right
    // Inorder = Left, Node, Right
    // Postorder = Left, Right, Root
   
    /*
        Dynamic Programming - for 1/2 step climbing stairs problem

        // TOP DOWN MEMOIZATION - start at F(n)
        Map<Integer, Integer> cache = new HashMap<>(Map.of(1, 1, 2, 2));
        public int climbStairs(int n) {
            // Completed Subproblem
            if (cache.containsKey(n)) {
                return cache.get(n);
            }

            cache.put(n, climbStairs(n - 1) + climbStairs(n - 2));

            return cache.get(n);
        }

        // BOTTOM UP TABULATION - start a 1, 2, 3...
        public int climbStairs(int n) {
            if (n == 1) {return 1;}
            if (n == 2) {return 2;}

            int[] array = new int[n + 1];
            array[0] = 1;
            array[1] = 2;

            for (int i = 2; i <= n; i++) {
                array[i] = array[i - 1] + array[i - 2];
            }

            return array[n - 1];
        }
    */

    /*
        Bit Manipulation
        ^ = XOR
            - duplicate numbers will remove themself
    */
}
