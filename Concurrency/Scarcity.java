import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

// Semaphores: a counter that limits how many threads can do something at once
/* 
class APIClient {
    private final Semaphore requestPermits = new Semaphore(5);

    public Response makeRequest(String endpoint) throws InterruptedException {
        requestPermits.acquire();
        try {
            return httpClient.get(endpoint);
        } finally {
            requestPermits.release();
        }
    }
}
*/
/*
Question: "What happens if an exception is thrown?"
Answer: You need a finally{} block to release the semaphore.
*/

// Resources Pooling (with Blocking Queue)
/* 
class ConnectionPool {
    private final BlockingQueue<Connection> availableConnections;

    public ConnectionPool(int poolSize) {
        availableConnections = new LinkedBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            availableConnections.add(createNewConnection());
        }
    }

    public Connection acquire() throws InterruptedException {
        return availableConnections.take();
    }

    public void release(Connection conn) throws InterruptedException {
        availableConnections.put(conn);
    }

    public void executeQuery(String query) throws InterruptedException {
        Connection conn = acquire();
        try {
            conn.execute(query);
        } finally {
            release(conn);
        }
    }
}
*/
/*
Question: "Why not just use a Semaphore for the connection pool?"
Answer: A semmaphor would set a limit of 10 operations.
But the BlockingQueue actually stores the Connection Object, and gets the next one.
*/

// Connection Pool w/ Timeout
/*
class ConnectionPoolWithTimeout {
    private final BlockingQueue<Connection> availableConnections;
    private final long timeoutMs;

    public ConnectionPoolWithTimeout(int poolSize, long timeoutMs) {
        this.availableConnections = new LinkedBlockingQueue<>(poolSize);
        this.timeoutMs = timeoutMs;
        for (int i = 0; i < poolSize; i++) {
            availableConnections.add(createNewConnection());
        }
    }

    public Connection acquire() throws InterruptedException {
        Connection conn = availableConnections.poll(timeoutMs, TimeUnit.MILLISECONDS);
        if (conn == null) {
            throw new RuntimeException("No connection available within " + timeoutMs + "ms");
        }
        return conn;
    }

    public void executeQuery(String query) throws InterruptedException {
        Connection conn = acquire();
        try {
            conn.execute(query);
        } finally {
            availableConnections.put(conn);
        }
    }
}
*/
/*
Question: "What if acquiring a resource takes too long?"
Answer: "I'll use poll with a timeout instead of take. If no connection is available within the timeout, I'll throw an exception and return an error to the caller."
*/

// Common Pattern: Limit Concurrent Operations
class File{}
class DownloadManager {
    private final Semaphore downloadSlots = new Semaphore(3);

    public void download(String url, File destination) throws InterruptedException {
        downloadSlots.acquire();
        try {
            //byte[] data = httpClient.download(url);
            //Files.write(destination.toPath(), data);
        } finally {
            downloadSlots.release();
        }
    }
}
/*
Examples
- Rate Limited API Wrapper
- Image Processing Pipeline
- Video Transcoding 
*/

// Common Pattern: Limit Aggregate Consumption
// You're tracking "47 MB of bandwidth currently in use across those uploads."
class Path{}
class DiskWriter {
    private static final int MB = 1024 * 1024;
    private final Semaphore diskCapacity = new Semaphore(100); // 100 MB

    public void writeFile(byte[] data, Path path) throws InterruptedException {
        int permits = Math.max(1, (data.length + MB - 1) / MB);

        diskCapacity.acquire(permits);
        try {
            //Files.write(path, data);
        } finally {
            diskCapacity.release(permits);
        }
    }
}

// Common Pattern: Reuse Expensive Objects
// Database Connection Pools w/ 10 database connection objects