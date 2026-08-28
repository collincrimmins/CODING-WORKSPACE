package Problems;

public class PrintEvenOdd {
    /*
        Prompt: print 1 to 100 multithreaded
    */

    private final int MAX_NUMBER = 100;
    private int counter = 1;

    public static void main(String[] args) {
        PrintEvenOdd printer = new PrintEvenOdd();

        Thread t1 = new Thread(() -> printer.printNumbers(1), "Thread-Odd");
        Thread t2 = new Thread(() -> printer.printNumbers(0), "Thread-Even");
        t1.start();
        t2.start();
    }

    public synchronized void printNumbers(int targetRemainder) {
        while (counter <= MAX_NUMBER) {
            // Wait for Even/Odd
            while (counter % 2 != targetRemainder && counter <= MAX_NUMBER) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (counter <= MAX_NUMBER) {
                System.out.println(Thread.currentThread().getName() + ": " + counter);
                counter = counter + 1;

                // Notify waiting threads
                notifyAll();
            }
        }
    }
}
