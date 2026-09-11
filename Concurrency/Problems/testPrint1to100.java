package Problems;

public class testPrint1to100 {
    

    private int n;
    private int target;
    private int activeThreadId;
    private int totalThreads;
    private final Object lock;

    public testPrint1to100(int target, int totalThreads) {
        this.target = target;
        this.n = 0;
        this.activeThreadId = 0;
        this.totalThreads = totalThreads;
        this.lock = new Object();
    }

    public void printNumber(int threadId) {
        while (true) {
            synchronized(lock) {
                while (n < target && activeThreadId != threadId) {
                    try {
                        lock.wait(); // sleeps until notified
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // Stop & Notify others
                if (n >= target) {
                    lock.notifyAll();
                    return;
                }

                // increment
                n = n + 1;
                System.out.println("Thread-" + (threadId + 1) + " -> " + n);

                // set next thread
                activeThreadId = (activeThreadId + 1) % totalThreads;
                
                // let all threads check if its their run
                lock.notifyAll();
            }
        }
    }

    public static class ThreadWorker implements Runnable {
        private int threadId;
        testPrint1to100 system;

        public ThreadWorker(int threadId, testPrint1to100 system) {
            this.threadId = threadId;
            this.system = system;
        }

        @Override
        public void run() {
            system.printNumber(threadId);
        }
    }

    public static void main(String[] args) {
        int target = 100;
        int threadCount = 10;
        testPrint1to100 system = new testPrint1to100(target, threadCount);
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new ThreadWorker(i, system), "Thread-" + (i + 1));
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
}
