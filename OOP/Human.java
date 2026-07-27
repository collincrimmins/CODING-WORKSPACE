package OOP;

public class Human extends Animal {
    static int NumberOfHumans = 0;

    private String name;
    private int age;

    public Human() {
        NumberOfHumans = NumberOfHumans + 1;
    }

    @Override
    public void move() {
        System.out.println("this human moved");
    }

    public void setAge(int input) {
        age = input;
    }

    public int getAge() {
        return age;
    }
}
