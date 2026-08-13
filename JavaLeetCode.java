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

            Greedy: (No algorithm) Making the BEST CHOICE per STEP. Whats the Trick of each step?
            DP: Problems need to reference previously solved Sub-Problems: int[] dp
            Backtracking: Trying ALL paths and choosing the best outcome.

            Graphs
                Topological Sort:
                    - DIRECTED EDGES [0, 1], [1, 2], [2, 3]
                    - Use INDEGREE and BFS wherever Indegree == 0
                Dijkstra:
                    - DIRECTED and WEIGHTED graph
                    - Used to find shorest distance, etc.
                    - BFS PriorityQueue go to shortest "int[] dist"
                Prim's:
                    - POINTS
                    - Create a Minimum Spanning Tree. Always go to next shortest distance. Mark as "boolean[] visited"
        
        */

        // Map
        Map<Character, Integer> map = new HashMap<>();
        TreeMap<Integer, Integer> treeMap = new TreeMap<>(); // Map and Sorted by Key
        // Set
        Set<Integer> set = new HashSet<>();
        Set<String> setWithInsertOrder = new LinkedHashSet<>();
        // Stack
        Stack<Integer> MyStack = new Stack<>();
        // Queue
        Queue<TreeNode> queue = new LinkedList<>();
        Deque<Integer> queue = new ArrayDeque<>();
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

    /*
        Backtracking: Trying all outcomes (2^n) and building a Path (List<String> path)

        class Solution {
            public List<List<Integer>> solveBacktracking(int[] nums) {
                List<List<Integer>> list = new ArrayList<>();
                // Sorting helps handle duplicates and prunes early if needed
                Arrays.sort(nums); 
                backtrack(list, new ArrayList<>(), nums, 0);
                return list;
            }

            private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
                // 1. Base Case / Goal Met
                if (isSolution(tempList)) {
                    list.add(new ArrayList<>(tempList)); // Must make a deep copy
                    return;
                }

                for (int i = start; i < nums.length; i++) {
                    // 2. Skip duplicates or handle invalid choices (Constraints)
                    if (shouldSkip(nums, i, start)) continue; 

                    // 3. Make Choice
                    tempList.add(nums[i]);

                    // 4. Move to next state (Recurse)
                    backtrack(list, tempList, nums, i + 1); // Use i + 1 or i depending on reuse rules

                    // 5. Undo Choice (Backtrack)
                    tempList.remove(tempList.size() - 1);
                }
            }
        }
    */

    /*
        Bit Manipulation
        & = Both Bits are 1
        | = Either Bit are 1
        ^ = XOR (duplicate numbers will remove themselves)
        - = NOT operator will flip the bits
        << = Add 0 to the right end
        >> = Move to right
    */
}


//////
////// Low Level Design (LLD)
//////
/*
    Creation Patterns 
    - Singleton: Class has 1 instane and a global point of access
        Interviewers will ask you to write a thread-safe implementation. 
        You must use Double-Checked Locking with a volatile keyword (in Java) 
        or use a static inner holder class (Bill Pugh breakthrough) 
        to prevent race conditions during lazy initialization.

    - Prototype: Allows an object to clone() itself 
        (return a new object, same attributes of the existing class)
   
    - Factory: Object Type depends on Input
        VehicleFactory
        Interface Class ("Vehicle") has function createVehicle()
        Class ("Truck") has @Override createVehicle()
    
    Structural Patterns
    - Adapter: convert "Circle" to "Square"

    - Decorator: Add new behavior to object, without changing original clas
        interface Beverage
        class Coffe implements Beverage
        class Milk implements Beverage
        new Sugar(new Milk(new Coffee())).cost() = 2.80

    - Chain of Responsibility: Request must pass through multiple approvers
        "Request" passes through TeamLead, Manager, Director

    - Observor: State Change needs to update other objects
        Order Status update ("create" or "paid")
            -> EmailService
            -> SmsService
            -> InventoryNotification

    - Strategy: Same tasks processed in different ways
        "PaymentProcessor" -> CardPayment vs. WalletPayment vs. Check

    - Composite: Show Tree Structures
        Files, Folders

    - Facade: Hide Complexity with a Front Facing Interface
        "OrderFacade" hides logic of Inventory, Payment, Shipping

    - Command: Actions need to be represented as Objects
        interface Command
        class DeleteCommand implments Command

    - State: Object Behavior Depends on State
        ship() depends on "created" vs "Paid" vs "delivered"

    - Template Method: Multiple Objects follow similar structue, but implements differently
        


    OOP Basics
    Inheritance: "Human" extends "Animal"
    Encapsulation = attributes are PRIVATE, and must be gotten through methods (get/set)
    Polymorphism: Method overriding (Classes) or Method overriding (input types)
    Abstraction = Hides the implementation details and just shows the methods



    Class Relationships
    - Association: 2 classes reference eachother
    - Aggregation: Has-A relationship (Team "HasA" player)
    - Composition: Has-A relationship, class cannot exist without previous (List<Items> needs "Order")
    - Dependancy: One class uses another briefly, like "Order" acccesing "Gateway"



    SOLID Principles
    S: Single Responsibility class
    O: Open/Close open for extension, closed for modificaiton
    L: Liskov Substition: Subtypes can be used the same as their base types ("FixedList" extends "List", or "Square" extends "Shape")
    I: Interface Segregation: Interfaces should be small & purpose based ("iPhone" vs "RotaryPhone")
    D: Dependancy Inversion: High Level Modules (Business Logic) should not depened on low-level modules, like writing to a SQL database. 
        They should call to a database service.



    UML Diagrams
    - Class: attributes & methods, and lines between classes
    - Use Case: Person -> "book ticket" vs. "cancel" vs. "pay"
    - Sequence: timeline, with Client/Order/Payment lines on a timeline
*/