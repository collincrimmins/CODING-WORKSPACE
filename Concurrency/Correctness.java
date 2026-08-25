import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// Coarse-Grained Locking = 1 thread runs the operation
// Interview: "I'll use a lock to ensure the check & booking happen atomically"
class TicketBooking {
    private final Object bookingLock = new Object();
    private Map<String, String> seatOwners = new HashMap<>();

    public boolean bookSeat(String seatId, String visitorId) {
        // Wrap the ENTIRE TRANSACTION in a lock
        // Transaction = Read + Write
        synchronized (bookingLock) {
            if (seatOwners.containsKey(seatId)) {
                return false;
            }
            seatOwners.put(seatId, visitorId);
            return true;
        }
    }
}

// Read-Write Locks = 100:1 read-write systems
// Reads = Shared Threads
// Writes = Exclusive
class Cache {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Map<String, String> data = new HashMap<>();

    public String get(String key) {
        // ReadLock (Shared)
        rwLock.readLock().lock();
        try {
            return data.get(key);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void put(String key, String value) {
        // WriteLock (exclusive)
        rwLock.writeLock().lock();
        try {
            data.put(key, value);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}

// Fine-Grained Locking
// Example: Alice wants to book Seat 7A, and Bob Seat 12B. They don't overlap
class TicketBookingFineGrained {
    private final ConcurrentHashMap<String, Object> seatLocks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> seatOwners = new ConcurrentHashMap<>();

    private Object getLock(String seatId) {
        return seatLocks.computeIfAbsent(seatId, k -> new Object());
    }

    public boolean bookSeat(String seatId, String visitorId) {
        synchronized (getLock(seatId)) {
            if (seatOwners.containsKey(seatId)) {
                return false;
            }
            seatOwners.put(seatId, visitorId);
            return true;
        }
    }
}
/* 
Potential problem: Deadlocks w/ 2 locks at the same time
Solution: Access the locks in the exact same order (Seat 7A then Seat 12B)
*/

// Atomic Variables
// compare-and-swap (CAS)
// Interview: "I'll use an atomic integer for the count since it's a single variable and atomics avoid lock overhead."
class BookingStats {
    private final AtomicInteger bookedCount = new AtomicInteger(0);

    public void onSeatBooked() {
        bookedCount.incrementAndGet();
    }

    public int getBookedCount() {
        return bookedCount.get();
    }
}
class ConcurrencyTracker {
    private final AtomicInteger maxConcurrent = new AtomicInteger(0);

    public void updateMaxConcurrent(int current) {
        while (true) {
            int observed = maxConcurrent.get();
            if (current <= observed) {
                return;
            }
            if (maxConcurrent.compareAndSet(observed, current)) {
                return;
            }
            // CAS failed - another thread changed it, retry
        }
    }
}

// Common Pattern: "Check-Then-Act"
class RateLimiter {
    private final Object rateLimitLock = new Object();
    private final Map<String, Integer> requestCounts = new HashMap<>();
    private final int maxRequests = 100;

    public boolean allowRequest(String userId) {
        synchronized (rateLimitLock) {
            Integer count = requestCounts.getOrDefault(userId, 0);
            if (count < maxRequests) {
                requestCounts.put(userId, count + 1);
                return true;
            }
            return false;
        }
    }
}
/*
Common Examples
- Connection Pool (multiple threads grab Connection #7)
- LRU Cache with Max Size (your limit of 1000 items gets exceeded)
- File Download Manager (incorrect state read of "currentlyDownloadingFile")
- Parking Lot (Spot #42 gets multiple writes)
- Singleton (checking if a Singleton already exists & then creating it)
*/

// Common Pattern: "Read-Modify-Write"
class RequestCounter {
    private final AtomicInteger requestCount = new AtomicInteger(0);

    public void onRequest() {
        // Basic Increment +1
        requestCount.incrementAndGet();
    }
}
class BankAccount {
    private final Object balanceLock = new Object();
    private int balance = 0;

    public void deposit(int amount) {
        synchronized (balanceLock) {
            // Multiple Operations & Variables
            balance = balance + amount;
        }
    }

    public void withdraw(int amount) {
        synchronized (balanceLock) {
            // Multiple Operations & Variables
            balance = balance - amount;
        }
    }
}
/*
Common Examples
- Page View Counter: Two threads simultaneously read pageViews as "1000" then add +1 at the same time
- Bank Account: PhoneDeposit & PaycheckDepost hit at same time, updating "Balance"
- Metrics Aggregator: tracking response times w/ running sum & count. Use a lock to update "sum" and "count".
- Inventory System: selling a shirt (check-then-act) and (read-modify-write)
For a single variable, say: "I'll use an atomic integer since the increment operation is atomic."
For multiple fields, say: "I'll use a lock so the read and write happen together."
*/