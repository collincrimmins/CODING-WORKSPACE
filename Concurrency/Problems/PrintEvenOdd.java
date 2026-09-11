package Problems;

import java.util.concurrent.Semaphore;

public class PrintEvenOdd {
    public static class SemaphorePrinter {
        private final int maxNumber;
        private int currentNumber = 1;

        // Initialize odd semaphore with 1 permit so Thread-Odd executes first
        private final Semaphore oddSemaphore = new Semaphore(1);
        // Initialize even semaphore with 0 permits so Thread-Even blocks initially
        private final Semaphore evenSemaphore = new Semaphore(0);

        public SemaphorePrinter(int maxNumber) {
            this.maxNumber = maxNumber;
        }

        public void printOdd() {
            while (true) {
                try {
                    oddSemaphore.acquire();

                    if (currentNumber > maxNumber) {
                        // Unblock even thread so it can inspect condition and exit cleanly
                        evenSemaphore.release();
                        return;
                    }

                    System.out.println(Thread.currentThread().getName() + " -> " + currentNumber);
                    currentNumber++;

                    // Hand off execution permit to the even thread
                    evenSemaphore.release();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        public void printEven() {
            while (true) {
                try {
                    evenSemaphore.acquire();

                    if (currentNumber > maxNumber) {
                        // Unblock odd thread so it can inspect condition and exit cleanly
                        oddSemaphore.release();
                        return;
                    }

                    System.out.println(Thread.currentThread().getName() + " -> " + currentNumber);
                    currentNumber++;

                    // Hand off execution permit back to the odd thread
                    oddSemaphore.release();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        int limit = 100;
        SemaphorePrinter printer = new SemaphorePrinter(limit);

        Thread oddThread = new Thread(() -> printer.printOdd(), "Thread-Odd");
        Thread evenThread = new Thread(() -> printer.printEven(), "Thread-Even");

        oddThread.start();
        evenThread.start();

        try {
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
