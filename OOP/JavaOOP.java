package OOP;
public class JavaOOP {
    public static void main(String[] args) {
        // Inheritance
        // "Human" extends "Animal"
        Animal myAnimal = new Animal();
        myAnimal.move();
        Human myHuman = new Human();
        myHuman.move();
        System.out.println(myHuman.getAge());
        System.out.println("Number of Humans: " + Human.NumberOfHumans);
        System.out.println("Human alive: " + myHuman.getAlive());
        
        // Static = Property belongs to class itself, not a new Instance of it
        new Human();
        new Human();
        System.out.println("Number of Humans: " + Human.NumberOfHumans);

        // Encapsulation = attributes are PRIVATE, and must be gotten through methods (get/set)
        // Protected = Only this package can access it
        // Private = Only this class can access it
        // Public = Anyone can access it
        myHuman.setAge(15);

        // Polymorphism
        // Method overriding w/ inheritance (like "Human" .move() overriding "Animal" .move())
        // Method overloading (input types)

        // Abstraction = Hides the implementation details and just shows the methods
        // Abstract Classes: "is-a" stores state and methods
        // Interfaces: "can-do" can only do methods
        // For classes:
        // public abstract class Vehicle() {
        //      void go() {}
        // }
        Interface myClassExample = new InterfaceImpl();
        System.out.println(myClassExample.MySecretMethod());
    }
}

/*
    Stack vs Heap memory
    Stack: stores methods, variables
    Heap: stores objects

    OOP 4 pillars = Encapsulation, Abstraction, Inheritance, Polymorphism

    Abstraction = Hides implementation details
    Encapsulation = Hiding state only being visible to that class 

    Overriding = Replacing the method in a subclass
    Overloading = one method with many different input arguments

    static method = belongs to class itself, not a new instance of it
    static block = executes code once when the class loads into memory

    singleton class = guarantees only 1 instance of a class ever exists

    List: ordered, duplicates allowed
    Set: not ordered, no duplicates allowed
    Map: Key value pairs, unique keys

    ArrayList: fast access, slow insert/delete
    LinkedList: slow access, fast insert/delete

    Spring
    Dependency Injection: spring creates objects for you, and injects them
    @Component: Generic Bean
    @Service: Business Logic
    @Repository: Data Access Layer
    @Controller: Returns views
    @RestController: for REST API, Returns JSON/XML directly (@Controller + @responsebody)

    Bean Lifecycle: Instantiation -> Dependency Injection -> Initialization -> Ready -> Destruction
    @Transactional: Manages database transactions, ensures commit/rollback automatically

    JPA: Specification
    Hibernate: an implementation of JPA
*/