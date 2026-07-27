package OOP;

public class Animal {
    private boolean Alive;

    public Animal() {
        Alive = true;
    }

    public void move() {
        System.out.println("this animal moved");
    }

    public boolean getAlive() {
        return Alive;
    }
}
