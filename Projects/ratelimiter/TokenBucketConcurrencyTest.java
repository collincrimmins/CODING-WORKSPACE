package Projects.ratelimiter;

import org.junit.jupiter.api.Test;

import Projects.ratelimiter.Limiters.TokenBucketLimiter;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenBucketConcurrencyTest {

    @Test
    public void testConcurrentRequestsSingleClientExactCapacity() throws InterruptedException {
        int maxTokens = 50;
        int refillRate = 0; // Disable refill during burst test
        TokenBucketLimiter limiter = new TokenBucketLimiter(maxTokens, refillRate);

        int totalThreads = 200;
        ExecutorService executor = Executors.newFixedThreadPool(32);
        CountDownLatch readyLatch = new CountDownLatch(totalThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        
        AtomicInteger allowedCount = new AtomicInteger(0);
        AtomicInteger deniedCount = new AtomicInteger(0);

        for (int i = 0; i < totalThreads; i++) {
            executor.submit(() -> {
                readyLatch.countDown();
                try {
                    startLatch.await(); // Wait so all threads fire simultaneously
                    RateLimitResponse response = limiter.allow("client1");
                    if (response.isAllowed()) {
                        allowedCount.incrementAndGet();
                    } else {
                        deniedCount.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        readyLatch.await(); // Ensure all threads are ready
        startLatch.countDown(); // Fire all requests at once
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(maxTokens, allowedCount.get(), "Allowed requests must match max tokens exactly");
        assertEquals(totalThreads - maxTokens, deniedCount.get(), "Excess requests must be rejected");
    }

    @Test
    public void testConcurrentMultiClientIsolation() throws InterruptedException {
        int maxTokensPerClient = 20;
        TokenBucketLimiter limiter = new TokenBucketLimiter(maxTokensPerClient, 0);

        int threadsPerClient = 50;
        String[] clients = {"clientA", "clientB", "clientC"};
        ExecutorService executor = Executors.newFixedThreadPool(30);
        
        ConcurrentHashMap<String, AtomicInteger> allowedPerClient = new ConcurrentHashMap<>();
        for (String client : clients) {
            allowedPerClient.put(client, new AtomicInteger(0));
        }

        CountDownLatch latch = new CountDownLatch(clients.length * threadsPerClient);

        for (String client : clients) {
            for (int i = 0; i < threadsPerClient; i++) {
                executor.submit(() -> {
                    try {
                        RateLimitResponse response = limiter.allow(client);
                        if (response.isAllowed()) {
                            allowedPerClient.get(client).incrementAndGet();
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        latch.await();
        executor.shutdown();

        // Each client should get exactly maxTokensPerClient allowed regardless of concurrency
        for (String client : clients) {
            assertEquals(maxTokensPerClient, allowedPerClient.get(client).get(), 
                "Client " + client + " exceeded quota under concurrent load");
        }
    }

    @Test
    public void testConcurrentRefillUnderLoad() throws InterruptedException {
        int maxTokens = 10;
        int refillRatePerSecond = 50; 
        TokenBucketLimiter limiter = new TokenBucketLimiter(maxTokens, refillRatePerSecond);

        // Exhaust initial tokens
        for (int i = 0; i < maxTokens; i++) {
            limiter.allow("client1");
        }

        // Wait 200 ms -> should refill ~10 tokens (50 * 0.200)
        Thread.sleep(200);

        ExecutorService executor = Executors.newFixedThreadPool(16);
        CountDownLatch latch = new CountDownLatch(30);
        AtomicInteger allowedPostRefill = new AtomicInteger(0);

        for (int i = 0; i < 30; i++) {
            executor.submit(() -> {
                try {
                    RateLimitResponse response = limiter.allow("client1");
                    if (response.isAllowed()) {
                        allowedPostRefill.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // 10 refilled tokens expected; due to slight sleep variation tolerance is allowed around ~9-11
        int actualAllowed = allowedPostRefill.get();
        org.junit.jupiter.api.Assertions.assertTrue(actualAllowed >= 9 && actualAllowed <= 11,
            "Expected ~10 refilled tokens allowed, but got: " + actualAllowed);
    }
}