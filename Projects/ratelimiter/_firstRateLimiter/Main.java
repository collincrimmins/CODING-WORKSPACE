package Projects.ratelimiter._firstRateLimiter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Projects.ratelimiter._firstRateLimiter.Limiters.Config;
import Projects.ratelimiter._firstRateLimiter.Limiters.ConfigTokenBucket;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String client1 = "client1";
        String endpoint1 = "/MyEndpoint";
        String endpoint2 = "/MyEndpoint2Window";

        List<Config> listConfigs = new ArrayList<>();

        // TokenBucket
        ConfigTokenBucket configTokenBucket = new ConfigTokenBucket(endpoint1, 100, 50);
        listConfigs.add(configTokenBucket);

        // SlidingWindowLog
        // HashMap<String, Integer> configSlidingWindow = new HashMap<>();
        // configSlidingWindow.put("maxRequests", 100);
        // configSlidingWindow.put("windowMs", 50);
        // Map<String, Object> config2 = new HashMap<>();
        // config2.put("endpoint", endpoint2);
        // config2.put("algorithm", "SlidingWindowLog");
        // config2.put("settings", configSlidingWindow);
        // listConfigs.add(config2);

        RateLimiter system = new RateLimiter(listConfigs);

        double Time = 0;
        int interval = 10;
        int totalTokensUsed = 0;
        for (int i = 1; i <= 500; i++) {
            // Request
            RateLimitResponse response1 = system.allow(client1, endpoint1);
            if (response1.isAllowed()) {
                totalTokensUsed = totalTokensUsed + 1;
            }

            // Print
            System.out.println("");
            System.out.println("=> Time: " + Time / 1000);
            System.out.println(response1.toString());

            // Wait
            Thread.sleep(interval);
            Time = Time + interval;
        }
        
        // Summary
        System.out.println("");
        System.out.println("Total Tokens used: " + totalTokensUsed);
    }

    /*
        https://codewitharyan.com/tech-blogs/design-rate-limiter 

        Prompt: "You're building an in-memory rate limiter for an API gateway. 
        The system receives configuration from an external service that 
        provides rate limiting rules per endpoint. Each endpoint can have its 
        own limit with a specific algorithm. Here's an example configuration for one endpoint:
        {
            "endpoint": "/search",
            "algorithm": "TokenBucket",
            "algoConfig": {
                "capacity": 1000,
                "refillRatePerSecond": 10
            }
        }
        This config allows bursts up to 1000 requests, refilling at 10 requests per second.
        Your job is to build the in-memory rate limiter that enforces these rules."

        Requirements
        - Rate Limit Config provided at startup
        - User sends {clientId, endoint}
        - Each Endpoint has a config
            - Algorithm ("TokenBucket", "SlidingWindowLog", etc)
        - ClientID is used to check for rate limit checks
        - Return: {allowed, requests remaining in my quota, when you can retry}
        - If endpoint has no config, use default config

        Entities
        - RateLimiter
        - Client (X - Not Entity, just a key)
        - Request (X - Not Entity, just basic input)
        - RateLimitResponse
        - Endpoint (X - Just a String, not an Entity)
        - RateLimiterAlgorithm

        Questions: "How would you add a new rate limiting algorithm?"
            We use our Factory pattern to add new algorithms

        Question: "How would you handle dynamic configuration updates?"
            Have a method that during runtime replaces all existing List<Limiters>

        Question: "How would you handle thread safety for concurrent requests?"
            Each algorithm class has a Map: Map<String, TokenBucket> buckets
            Every client has their own TokenBucket (by key "client1") so we lock the object
    */
}
