package Problems;

public class Print1to100 {

    public static class PrinterController {
        private final int maxNumber;
        private final int totalThreads;
        private final Object lock = new Object();
        
        private int currentNumber = 1;
        private int activeThreadId = 0; // Index of the thread allowed to print next

        public PrinterController(int maxNumber, int totalThreads) {
            this.maxNumber = maxNumber;
            this.totalThreads = totalThreads;
        }

        public void printNumber(int threadId) {
            while (true) {
                synchronized (lock) {
                    while (currentNumber <= maxNumber && activeThreadId != threadId) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    if (currentNumber > maxNumber) {
                        lock.notifyAll(); // Signal remaining waiting threads to terminate cleanly
                        return;
                    }

                    System.out.printf("Thread-%d: %d%n", threadId + 1, currentNumber);
                    
                    currentNumber++;
                    activeThreadId = (activeThreadId + 1) % totalThreads;
                    
                    lock.notifyAll(); // Notify all waiting threads to re-evaluate turn predicate
                }
            }
        }
    }

    /**
     * Runnable task representing an individual worker thread.
     */
    public static class PrinterWorker implements Runnable {
        private final int threadId;
        private final PrinterController controller;

        public PrinterWorker(int threadId, PrinterController controller) {
            this.threadId = threadId;
            this.controller = controller;
        }

        @Override
        public void run() {
            controller.printNumber(threadId);
        }
    }

    public static void main(String[] args) {
        int maxNumber = 100;
        int totalThreads = 3; // Configurable: scales to any number of threads

        PrinterController controller = new PrinterController(maxNumber, totalThreads);
        Thread[] threads = new Thread[totalThreads];

        for (int i = 0; i < totalThreads; i++) {
            threads[i] = new Thread(new PrinterWorker(i, controller), "Worker-" + (i + 1));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /*
        prompt: print 1 to 100 mulitthreaded
    */
}
