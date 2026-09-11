package Problems;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class FizzBuzz {
    private int n;

    private Semaphore numberSemaphore; 
    // Semaphore to allow the "Fizz" thread to print
    private Semaphore fizzSemaphore; 
    // Semaphore to allow the "Buzz" thread to print
    private Semaphore buzzSemaphore; 
    // Semaphore to allow the "FizzBuzz" thread to print
    private Semaphore fizzBuzzSemaphore; 

    public FizzBuzz(int n) {
        this.n = n;

         // Initially allow printing of numbers
        numberSemaphore = new Semaphore(1); 

        // Block "Fizz", "Buzz", and "FizzBuzz" threads initially
        fizzSemaphore = new Semaphore(0); 
        buzzSemaphore = new Semaphore(0); 
        fizzBuzzSemaphore = new Semaphore(0); 
    }

    // by 3 and not 5
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 != 0) {
                fizzSemaphore.acquire();
                printFizz.run();
                numberSemaphore.release();
            }
        }
    }

    // divisible by 5 not 3
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for(int i = 1; i <= n; i++) { 
            if(i % 3 != 0 && i % 5 == 0) { 
                // Wait for permission to print "Buzz"
                buzzSemaphore.acquire(); 

                // Execute the provided function to print "Buzz"
                printBuzz.run(); 

                // Signal the next thread to proceed
                numberSemaphore.release(); 
            } 
        } 
    }

    // divisible by 3 and 5
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for(int i = 1; i <= n; i++) { 
            if(i % 3 == 0 && i % 5 == 0) { 
                // Wait for permission to print "FizzBuzz"
                fizzBuzzSemaphore.acquire(); 

                // Execute the provided function to print "FizzBuzz"
                printFizzBuzz.run(); 

                // Signal the next thread to proceed
                numberSemaphore.release(); 
            } 
        } 
    }

    // not divisible 3 or 5
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            numberSemaphore.acquire();

            // Signal to correct thread
            if (i % 3 == 0 && i % 5 == 0) {
                fizzBuzzSemaphore.release();
            } else if ( i % 3 == 0) {
                fizzSemaphore.release();
            } else if ( i  % 5 == 0 ){
                buzzSemaphore.release();
            } else {
                // this function
                printNumber.accept(i);
                numberSemaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzz fizzBuzz = new FizzBuzz(n);

        // Create Thread A for fizz()
        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.print("fizz "));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Create Thread B for buzz()
        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.print("buzz "));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Create Thread C for fizzbuzz()
        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.print("fizzbuzz "));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Create Thread D for number()
        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number(value -> System.out.print(value + " "));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start all 4 threads concurrently
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        // Wait for all threads to complete execution
        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /*
    
        You have the four functions:
        • printFizz that prints the word "fizz" to the console,
        • printBuzz that prints the word "buzz" to the console,
        • printFizzBuzz that prints the word "fizzbuzz" to the console, and
        • printNumber that prints a given integer to the console.

        You will run four threads
        • Thread A: calls fizz() that should output the word "fizz".
        • Thread B: calls buzz() that should output the word "buzz".
        • Thread C: calls fizzbuzz() that should output the word "fizzbuzz".
        • Thread D: calls number() that should only output the integers.
    */
}
