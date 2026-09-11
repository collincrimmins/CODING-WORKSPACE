package Problems;

import java.util.concurrent.Semaphore;

public class testPrintZeroEvenOdd {
    private volatile int n;
    private final int numberTarget;
    private final Semaphore zeroSemaphore;
    private final Semaphore evenSemaphore;
    private final Semaphore oddSemaphore;

    public testPrintZeroEvenOdd(int numberTarget) {
        this.n = 0;
        this.numberTarget = numberTarget;
        this.zeroSemaphore = new Semaphore(1); // start
        this.evenSemaphore = new Semaphore(0);
        this.oddSemaphore = new Semaphore(0);
    }

    public void printZero() throws InterruptedException {
        boolean isOdd = true;

        while (true) {
            zeroSemaphore.acquire();

            if (n == numberTarget) {
                // Signal to threads to return
                oddSemaphore.release();
                evenSemaphore.release();
                return;
            }

            System.out.println("0 [" + Thread.currentThread().getName() + "]");
            
            if (isOdd) {
                oddSemaphore.release();
            } else {
                evenSemaphore.release();
            }

            isOdd = !isOdd;
        }
    }

    public void printEven() throws InterruptedException {
        while (true) {
            evenSemaphore.acquire();

            if (n == numberTarget) {
                return;
            }

            n = n + 1;
            System.out.println(n + " [" + Thread.currentThread().getName() + "]");
            
            zeroSemaphore.release();
        }
    }

    public void printOdd() throws InterruptedException {
        while (true) {
            oddSemaphore.acquire();

            if (n == numberTarget) {
                return;
            }

            n = n + 1;
            System.out.println(n + " [" + Thread.currentThread().getName() + "]");
            
            zeroSemaphore.release();
        }
    }

    public static void main(String[] args) {
        testPrintZeroEvenOdd system = new testPrintZeroEvenOdd(50);

        Thread threadZero = new Thread(() -> {
            try {
                system.printZero();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Thread-Zero");

        Thread threadEven = new Thread(() -> {
            try {
                system.printEven();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Thread-Even");

        Thread threadOdd = new Thread(() -> {
            try {
                system.printOdd();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Thread-Odd");

        threadZero.start();
        threadEven.start();
        threadOdd.start();

        try {
            threadEven.join();
            threadOdd.join();
            threadZero.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("done");
    }


}
