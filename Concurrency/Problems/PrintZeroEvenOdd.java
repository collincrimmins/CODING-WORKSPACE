package Problems;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class PrintZeroEvenOdd {
    private int n;
    private Semaphore zeroSemaphore;
    private Semaphore evenSemaphore;
    private Semaphore oddSemaphore;
    
    public PrintZeroEvenOdd(int n) {
        this.n = n;
        zeroSemaphore = new Semaphore(1); // Start at Zero
        evenSemaphore = new Semaphore(0);
        oddSemaphore = new Semaphore(0);
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        boolean isOdd = true; // toggle between even & odd

        for (int i = 1; i <= n; i++) {
            zeroSemaphore.acquire();
            printNumber.accept(0); // print 0

            if (isOdd) {
                oddSemaphore.release();
            } else {
                evenSemaphore.release();
            }

            // toggle
            isOdd = !isOdd;
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i = i + 2) {
            evenSemaphore.acquire();
            printNumber.accept(i);
            zeroSemaphore.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i = i + 2) {
            oddSemaphore.acquire();
            printNumber.accept(i);
            zeroSemaphore.release();
        }
    }

    public static void main(String[] args) {
        int n = 50;
        PrintZeroEvenOdd system = new PrintZeroEvenOdd(n);
        IntConsumer printNumber = System.out::print;

        // Thread A calls zero()
        Thread threadA = new Thread(() -> {
            try {
                system.zero(printNumber);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Thread B calls even()
        Thread threadB = new Thread(() -> {
            try {
                system.even(printNumber);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Thread C calls odd()
        Thread threadC = new Thread(() -> {
            try {
                system.odd(printNumber);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start all threads simultaneously
        threadA.start();
        threadB.start();
        threadC.start();

        // Wait for all threads to finish execution
        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(); // Print newline at the end
    }

    /*
        Prompt  You have a function printNumber 
        that can be called with an integer parameter and prints it to the console.
        calling printNumber(7) prints 7 to the console.
        You are given an instance of the class ZeroEvenOdd 
        that has three functions: zero, even, and odd.
        Modify the given class to output the series "010203040506..." 
        where the length of the series must be 2n.
    
    */
}
