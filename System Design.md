Main Patterns
- Cache (Redis)
    > Distributed Lock ("ticket123: locked")
    > Location Cache (Redis Geohash)
    > Rate Limiting
        - Token Bucket
    > Hot Key: Multiple Cache Nodes (cache key fanout) and load balance reads across the nodes. (feed:taylor-swift:1, feed:taylor-swift:2)
- Elasticsearch
    > Text Search (& Fuzzy Search): Inverted Index
    > Properties: An index on the field
    > Locations: Geospatial Index (geohash)
- Queue & Workers
    > Queue (Ticketmaster Queue)
    > Async Processes (Videos)
    > JobID + Status (processsing | finished)
- File Storage (S3)
    > "Presigned/Signed URL" for Upload/Download
        + S3 Multipart Uploads (chunks)
        + S3 Event Notifications (upload completed)
    > "Signatures" for CDNs (AWS CloudFront)
- Cron Job
    > Update XYZ row property every 1 hour
- Feed Systems
    > Precomputed Feeds (& Workers adding new posts to all existing feeds)
- Indexes
    > Composite Primary Key
        PRIMARY KEY (post_id, user_id) - User can only like a post once
        PRIMARY KEY (follower_id, followee_id)
    > Composite Indexes
        CREATE INDEX searchIndex1 ON comments (post_id, created_at DESC)
    > Geospatial Index (PostGIS)
    > Text Index (GIN Index)
- Scaling Writes
    > Sharding & Partitioning
        Horizontal Sharding
        Partitioning Key - like a userid/postid
    > Batching Writes
- Real Time Updates
    > Simple Polling & Long Polling
    > Server Sent Events (SSE)
    > Websockets
        - Redis Pub/Sub: for tracking the connection (by userid)
        - Load Balancer: use "Least Connections" since websockets is stateful
    > WebRTC: Peer-to-Peer (Video Calls)
- Dealing with Contention
    > Atomic: All changes happen or none happen
    > Transactions (read-modify-write)
    > Pessimistic Locking: row lock upfront
    > Optimistic Locking: assumes rare collisions, so detect after transaction is complete, then do actions (like version numbers)
    > Hot Key: Queue to check status for Taylor Swift tickets (eventual consistency)




Question Types
1) Booking System
    Keys: Data consistency (double booking), distributed locking, 
    > Ticketmaster
    > Flight Booking
2) Location Systems
    Keys: Geospatial Indexing & Streaming, Queues (Kafka) for driver-passenger matching
    > Uber
    > Yelp reviews
    > Food delivery
3) High Throughput Messaging & Feed Systems
    Keys: Fan-out scale & read vs write tradeoffs
    > WhatsApp/Messengers
    > Twitter Feed
    > Instagram Feed
4) Heavy Data Storage & Streaming
    Keys: Large Blob Storage, Async Processing, CDNs
    > Youtube
    > Netflix
    > Google Drive
5) Distributed Infastructure Utilities
    Keys: Low Latency, High Availability, Uniqueness
    > URL Shortener
    > API Rate Limiter

Systems Design
1) Requirements
2) Core Entities
3) API Interface
4) Data Flow
5) High Level Design
6) Deep dives...




Numbers to Know
Caching
- ~1 millisecond latency 
- 100k+ operations/second
- Memory-bound (up to 1TB)
Databases
- Up to 50k transactions/second
- Sub-5ms read latency (cached)
- 64 TiB+ storage capacity
App Servers
- 100k+ concurrent connections
- 8-64 cores @ 2-4 GHz
- 64-512GB RAM standard, up to 2TB
Message Queues
- Up to 1 million msgs/sec per broker
- Sub-5ms end-to-end latency
- Up to 50TB storage




Search Optimized Databases
- Slow Traditional Queries
    SELECT * FROM documents WHERE document_text LIKE '%search_term%'
- Elasticsearch: distributed REST search
- Inverted Indexes: fast full text search
    {
        "word1": [doc1, doc2, doc3],
        "word2": [doc2, doc3, doc4],
        "word3": [doc1, doc3, doc4]
    }
- Tokenization: Tokenization is the process of breaking a piece of text into individual words.
- Stemming: Stemming is the process of reducing words to their root form. This allows you to match different forms of the same word. For example, "running" and "runs" would both be reduced to "run".
- Fuzzy Search: Fuzzy search is the ability to find results that are similar to a given search term. 
- Scaling: Just like traditional databases, search optimized databases scale by adding more nodes to a cluster and sharding data across those nodes.



API Gateway
- AWS API Gateway

Load Balancer
- AWS Elastic Load Balancer

Queues for Requests
- Kafka
- Message Ordering: usually FIFO
- Retry Mehanisms:  Many queues have built-in retry mechanisms that attempt to redeliver a message a certain number of times before considering it a failure
- Dead Letter Queues: used to store messages that cannot be processed
- Scaling with Partitions: Queues can be partitioned across multiple servers so that they can scale to handle more messages.
- Backpressure: Backpressure is a way of slowing down the production of messages when the queue is overwhelmed.

Streams
- When you need to process large amounts of data in real-time.
- When you need to support complex processing scenarios like event sourcing
- When you need to support multiple consumers reading from the same stream

Distributed Lock
- Ticketmaster: you might need a way to lock a resource - like a concert ticket - for a short time (~10 minutes in this case). This is so while one user is in the middle of buying a ticket, no one else can grab it. 
- Use Redis to store a key-value lock
    ticket-123: locked
- Ecommerce checkout system for items in cart
- Ride sharing lock when a client selects a driver
- Locking Mechanisms: There are different ways to implement distributed locks. One common implementation uses Redis and is called Redlock. 
- Lock Expiry: Distributed locks can be set to expire after a certain amount of time
- Locking Granularity: Distributed locks can be used to lock a single resource or a group of resources. For example, you might want to lock a single ticket in a ticketing system or you might want to lock a group of tickets in a section of a stadium.
- Deadlocks: Deadlocks can occur when two or more processes are waiting for each other to release a lock

Distributed Cache - for SCALING and LOWERING LATENCY
- Reduce # of Queries & Speed up Expensive Queries
    Twitter Feed: grabbing a homepage is a lot of queries... Instead, run query once, then store in a distributed cache.
- Eviction strategies
    LRU - Least Recently accessed
    FIFO
    LFU - Least frequently used
- Cache Write Strategy:
    > Write-Through Cache: Writes data to both the cache and the underlying datastore simultaneously.
    > Write-Around Cache: Writes data directly to the datastore, bypassing the cache.
    > Write-Back Cache: Writes data to the cache and then asynchronously writes the data to the datastore.



REST API

Entities (Nouns)
Actions (Verbs) are GET POST PUT PATCH DELETE
GET /api/v1/users/123456

Filtering, Sorting, Pagination
GET /products?category=tech
GET /products?sort=price
GET /products?page=2&limit=20&cursor=123

Path Parameters (ID for Entity): /events/:id
Query Parameters: Filter, Sort, Modify - /events?city=NYC&date=2024-01-01
Request Body: 

200 OK
201 created
400 Bad Request
401 Unauthroized
403 Forbidden (logged in, not having permission)
404 Not Foundation
500 Server Error

Twitter Example
    POST /v1/tweets body: { "text": string }
    GET /v1/tweets/{tweetId} -> Tweet
    POST /v1/follows body: { "followee_id": string }
    GET /v1/feed -> Tweet[]

Common API Patterns
- Pagination
- Offset  Pagination /events?offset=20&limit=10
- Cursor Pagination
    > First request (no cursor)
        /events?limit=10
    > Second request w/ Cursor
        /events?cursor=cmd9atj3p000007ky19w1dpy2&limit=10
- Versioning
    /v1/events

API Rate Limiting common straegies
- Per-user limits: 1000 requests per hour per authenticated user
- Per-IP limits: 100 requests per hour for unauthenticated requests
- Endpoint-specific limits: 10 booking attempts per minute to prevent ticket scalping

RPC
- in microservice architectures where services need to communicate frequently and efficiently
    > For our Ticketmaster example, you might use REST APIs for your public endpoints that mobile apps and web clients consume, but use gRPC for internal communication between your booking service, payment service, and inventory service.




Datbases
- ACID
    > Atomicity: All changes happen, or none
    > Consistency: saved data follows the rules you defined
    > Isolation: Transactions running at the same time do not step on eachother in unsafe ways
    > Durability: Once a transaction is commited, the database can recover it after a crash
- Transactions: full execution or none at all
    BEGIN;

    UPDATE accounts
    SET balance_cents = balance_cents - 10000
    WHERE id = 'A' AND balance_cents >= 10000;

    -- If the previous statement updated 0 rows, ROLLBACK.

    UPDATE accounts
    SET balance_cents = balance_cents + 10000
    WHERE id = 'B';

    COMMIT;

- Ecommerce might store orders in PostgreSQL (for ACID properties) and noSQL for product caalog because its Schema is flexible/changing, and is Read heavy.
- Database Indexes: Reads get faster (email column), but Writes/Updates become slower.
- Vertical Partitioning: When your Entity Table has dozens of columns, split them into more narrow tables.
Joined by a Shared Key
"User" -> ProfileTable (id, name, email, bio), AuthTable (id, passwordHash), BillingTable (id, card_number)
- Normalization: Keep your data joined by foreign keys (Users / Products / Orders) then do joins - to avoid duplication of data
- Denormalization: For READ HEAVY tables (such as "Order" having "Username" and "ProductTitle" directly stored in the ORDER table) you can put the dynamic data into 1 table. You must update it if the original data is updated.
- Blob Storage: Binary-Large-Objections (Images/Videos/PDFs) Amazon S3
Database just stores the URL string "aws.com/myimage"
- Elasticsearch: used for full-text search (tweets/documents)



Cache
- Redis "cache-aside" (or lazy loading). 
    > Store in Redis with a TTL
    > The application checks the cache first. On a hit, it returns immediately. On a miss, it fetches from the database, stores the result in the cache, and then returns it.
- "write through": app only writes to cache, then cache synchronously writes to databasee before returning to the application
- "read through"  read-through caching, the cache acts as a smart proxy. Your application never talks to the database directly. On a cache miss, the cache itself fetches from the database, stores the data, and returns it.
- Eviction
    LRU
    LFU
    FIFO
    TTL
- Cache Consistency: the cache and database return different values for the same data. This is common because most systems read from the cache but write to the database first.
    > Cache invalidation on writes
    > Short TTLs for stale tolerance
    > Accept eventual consistency: For feeds, metrics, and analytics, a short delay is usually fine.
- hot key: is a cache entry that receives a huge amount of traffic compared to everything else.
    >Replicate hot keys: Store the same value on multiple cache nodes and load balance reads across them.
    >  Add a local fallback cache: Keep extremely hot values in-process to avoid pounding Redis.
    > Apply rate limiting: Slow down abusive traffic patterns on specific keys.
- Uses for Caches
    > Read heavy workloads
    > Expensive queries: user homepageeed

Redis
- Cache
- Distributed Lock
    > TicketMaster needs to hold a lock on a ticket sale in cart
    SET lock:concert:343 my-token NX EX 30
    NX = succeed only if the key doesnt already exist
    EX 30 = Expire after 30 seconds
- Rate Limiting
    Fixed Window Rate Limiter
- Proximity Search
- Event Sourcing (not as durable as Kafka)
    Kafka: when you need durable, replayable streams with long retention for many independent consumers
- Pub/Sub (not as durable as Kafka)
- Hot Keys  
    > Key Copies
    > Read Replicas

API Gateway
- Request Routing to Services
    /users/*
- Validate Request
    Valid URL request, required headers, request body
- Middleware (Auth, Rate Limiitng)
    Authenticate JWT Tokens
    Limit Request Rates
    Terminate SSL connections
    Log Traffic
    Version APIs
    IP whitelist/blacklist
    handle response timeouts
- Response Transformation
- Scaling
    Horizontal: Add more servers to a Load Balancer
        > API Gateways are stateless
        > Client-to-Gateway Load Balancing: This is typically handled by a dedicated load balancer in front of your API Gateway instances (like AWS ELB or NGINX).
        > Gateway-to-Service Load Balancing: The API Gateway itself can perform load balancing across multiple instances of backend services
    Global Distribution: Deploy a Gateway at a geographical edge
- Popular Examples
    AWS API Gatway
- When to use
    > You have a microservice architecture 


Kafka
- Message Queue OR Stream Processing
- Use Cases
    > Queue: Processing Asynchronously for Youtube: add video to Kafka topic to be transcoded
    > Queue: Virtual Waiting Queue for Ticketmaster
    > Stream: continuous and immediate processing of incoming data, treating it as a real-time flow
    > Stream: Messages need to be processed by multiple consumers simultaneously. In Design FB Live Comments 
- Producers and Consumers
- Partitioning Strategy
- Consumer Group
- Topics
- Record
    > Value (Payload)
    > Key (determins which partition the message is sent to)
    > Timestamp (ordering within a partition is determined by offsets, not timestamps)
    > Headers (Metadata)
-A Kafka cluster is made up of multiple brokers. These are just individual servers (they can be physical or virtual).
- Each broker has a number of partitions. Each partition is an ordered, immutable sequence of messages that is continually appended to -- think of like a log file
- A topic is just a logical grouping of partitions.
- Each partition in Kafka functions essentially as an append-only log file
    > Immutability (Messages cannt be altered)
    > Efficiency: appends to end of hte log
    > Scalability: Horizontal scaling because of append-only logging
- Offset: Each message in a Kafka partition is assigned a unique offset, which is a sequential identifier indicating the message’s position in the partition. This offset is used by consumers to track their progress in reading messages from the topic.
- Example
    // Initialize the Kafka client
    const kafka = new Kafka({
    clientId: 'my-app',
    brokers: ['localhost:9092']
    })

    // Initialize the producer
    const producer = kafka.producer()

    const run = async () => {
    // Connecting the producer
    await producer.connect()

    // Sending messages to the topic 'my_topic' with keys
    await producer.send({
        topic: 'my_topic',
        messages: [
        { key: 'key1', value: 'Hello, Kafka with key!' },
        { key: 'key2', value: 'Another message with a different key' }
        ],
    })
    }
Scalability
    - Keep messages as small as possible (<1MB)
        Such as storing the link to a s3url video, not storing the video itself on a kafka message


Sharding
- Use Case:
    > A single database has too much traffic
- (Default) Hash Based: hash(user_id) % 4
- Range based (User IDS 1 to 1 million, etc)
- How to talk about in interviews
    > Shard Key is XYZ ("Shard by user_id because we're mostly querying by their posts, followers, and likes")
    > Shard Strategy ("Hash sharding with consistent hashing")
    > Tradeoffs ("The trade-off is that global queries become expensive. If we need 'trending posts across all users' we have to query all shards and aggregate results. We can handle that by caching trending content and pre-computing it with a background job rather than calculating it on every request.")
    >  Address how you'll handle growth "We'll start with 64 shards, which gives us room to grow. Consistent hashing makes it easier to add shards later without resharding all the data. If we need more capacity, we can add shards and only a fraction of the data moves."


Scaling
- Vertical Scaling: More CPU, More RAM - for 1 just server
- Horizontal Scaling: Get more servers, use Load Balancer to distribute to Server1, Server2...
- Load Balancers
    > Round Robin: sequential cyclical
    > Sticky round-robin: If Alice’s first request goes to service A, the following requests go to service A as well.
    > Weighted round-robin
    The admin can specify the weight for each service. The ones with a higher weight handle more requests than others.
    > Least # of Connections (useful for persistent connections like SSE or Websockets)
    > Least Respose Time
    > Random assignment


Replication
- primary-replica: all writes go to the primary, and changes are replicated to one or more replicas that handle read queries.
    > Primary Datababase: READ + WRITE
    > Replica #1: READ Only
    > Replica #2: READ only
- Sharding: Multiple Database Nodes
    > Shard Router = shard_key % N (Hash based - like using a userID as key)



////////////////////////////////
////////////////////////////////
Distributed Systems
////////////////////////////////
////////////////////////////////

CAP Thereom
- In a distributed system, you can only have 2 QUALITIES at one time
- Example:
    - Server in USA and Europe
    - User updates profile "bio", it now replicates to those 2 servers
    - Connection between our 2 servers fails (partitioned)
    - Do we
        > Availability: keep giving out potentially stale data
        > Consistency: fail requests if data not guaranteed to be accurate
- Consistency (Reads return the latest write)
    > Strong Consistency vs Eventual Consistency
- Availability (every request gets a response)
- Partition Tolerance (system works even if the network links betwene nodes fails)
- When to choose Consistency
    > Ticket Booking: User A & B both book the same seat on a flight, because the system shows Stale data, as "available seat".
    > Ecommerce Inventory: Amazon sells its last x1 inventory to 2 people
    > Financial Systems: Stock data
- When to choose Availability:
    > Social Media: Showing outdated Username/Photo
    > Content (Netflix): outdated movie description
    > Review website: outdated reviews or businss hours etc
- Consistency: Single Node Solutions (Database), Distributed Transactions
- Availability: Multiple Asynchronous Read Replicas

Consistency Model
- Strong consistency: means that once a write succeeds, later reads must see that write or something newer.
    > Data must be correct instantly
- Eventual consistency: means copies of the data may disagree for a while, but they should become the same later if updates stop and replication keeps working.
    > Temporary stale data is acceptable - and Availability or Low Latency is more important.
- Casual Consistency: Related events appear in the same order to all users. This ensures logical ordering of dependent actions, such as ensuring comments on a post must appear after the post itself.
- Read-your-own-writes Consistency: Users always see their own updates immediately, though other users might see older versions. (My profile update instantly updates)

Idempotency: An idempotent operation produces the same result regardless of how many times you execute it.
    > If the client retries, you could end up charging a customer twice or sending duplicate emails. Idempotency prevents this.
    > GET and DELETE are naturally idempotent (getting a resource twice returns the same thing, deleting an already-deleted resource is a no-op). POST is not naturally idempotent, which is why you add an idempotency key: a unique identifier the client sends with each request. The server checks if it has already processed that key and returns the cached result instead of processing again.
    > Idempotency Key: "abc-123" for request POST /payments. Server already sees that key "abc-123" has ran, so it ignores requets.



Architecture Patterns
- Microservices
- Message Queues: Message queues decouple services by introducing an intermediary that stores messages until the consumer is ready to process them.
    > Message queues decouple services by introducing an intermediary that stores messages until the consumer is ready to process them.
    > Decoupling: Services do not need to know about eahcother
    > Buffering: Handle traffic spikes by bsorbing burts
    > Reliability: Message spersist even if consumers crash
    > Scalability: Add more consumers to process faster
- Rate Limiting
    > Token Bucket: tokens refill at a fixed rate, each requets costs a token)
    > Sliding Window: count requests in a rolling time window
    > Fixed Window: count requetss in discrete time interval
    > 429 Rejected Request - too many requests
- API Gateway
    > Auth
    > Rate Limiting
    > Routes
    > Transform






Data Modeling Example
Users
- userId (pk)
- name
- email (unique)
- createdAt
Posts
- postId (pk)
- userId(fk, index)
- content
- mediaUrls
- createdAt (index)
Comments
- commentId (pk)
- postId (fk, index)
- userId (fk, index)
- content
- createdAt(index)
Indexes
- Composite Index: (user_id, created_at) to load chronological posts




Scaling Reads
- Indexes on Datbases
    Turns O(n) row scan into O(logn) with indexes
    "Too many indexes" is overblown for modern databases
- Vertical Scaling: a bigger server with RAM and Disk space
- Denormalization: store duplicate data in important tables, to avoid doings many joins
- Horizontal Scaling
- Read Replicas
     Synchronous replication ensures data consistency but introduces latency.
     Asynchronous replication is faster but introduces potential data inconsistencies.
- Application Layer Redis Cache
    TTL
    Write-through invalidation: Update or delete cache entries immediately when writing to the database
    Write behind invalidatio:  Queue invalidation events to process asynchronously.
- Database Sharding
- Common Examples
    URL Shortener: read heavy = redis with no TTL expiration & CDN caching
    Ticketmaster: Cache Event information
    News Feeds: Precomputer feeds, cache recent posts from followed users, use pagination to avoid loading entire feeds
    Youtube: Video metadata - titles, thumbnails
- When not to use
    Write heavy systems - ubers location tracking
    Small scale apps < 1000 users
    Strongly Consistent Systems - Finanacial Transactions, Inventory Management (no stale data allowed)
- "What happens when your queries start taking longer as your dataset grows?"
    Slow Read Example: Your scanning every row for matching "email", at 200 bytes per row with 10 million users that 2GB of data to scan to find a user
- ""How do you handle millions of concurrent reads for the same cached data?""
    Cache key fanout spreads a single hot key across multiple cache entries. (feed:taylor-swift:1, feed:taylor-swift:2)
- ""What happens when multiple requests try to rebuild an expired cache entry simultaneously?""
    Cache Stampede
    probabilistic early refresh - serving cached data while refreshing it in the background
- ""How do you handle cache invalidation when data updates need to be immediately visible?""
    cache versioning
        event:123:v42     // before update
        event:123:v43     // after update

Scaling Writes
- Vertical Scaling (Disk I/O Speed, CPU, Network bandiwth)
- Sharding and Partitioning
    Horizontal Sharidng
    Partitioning Key - like a userid, or postid
- Vertical Partitioning
    splitting columns
    TABLE posts -> post_content, post_metrics, post_analytics
- Queues (for sudden bursts)
- Batching Writes
    Intermediate Processing: for a "like system" on a post, you can update the number in a window like 1 minute then update the db
- Common Interview Examples
    Instagram: sharding by user ID for posts, vertical partitioning for different data types (user profiles, posts, analytics), and hierarchical storage for older posts.
    Search Applications - Search applications are often write-heavy with substantial preprocessing required in order to make the search results quick to retrieve. Partitioning and batching are key to making this work.
- ""How do you handle resharding when you need to add more shards?""
    You started with 8 shards, but now you need 16. How do you migrate data without downtime?
    Production systems use gradual migration which targets writes to both locations (e.g. the shard we're migrating from and the shard we're migrating to). This allows us to migrate data gradually while maintaining availability.
- ""What happens when you have a hot key that's too popular for even a single shard?""
    Split All Keys
    Split Hot Keys Dynamically



Handling Large Blobs
- Uploads: Presigned URL on S3 Blob
    1 specific file, 1 specific location, time limit (15 mins to 1 hour)
    https://mybucket.s3.amazonaws.com/uploads/user123/video.mp4
    ?X-Amz-Algorithm=AWS4-HMAC-SHA256
    &X-Amz-Credential=AKIAIOSFODNN7EXAMPLE%2F20240115%2Fus-east-1%2Fs3%2Faws4_request
    &X-Amz-Date=20240115T000000Z
    &X-Amz-Expires=900
    &X-Amz-SignedHeaders=host
    &X-Amz-Signature=b2754f5b1c9d7c4b8d4f6e9a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0
- Downloads: Signed URLS for S3 Blob
- Downloads: CDN signatures for AWS CloudFront
    https://d123456.cloudfront.net/videos/lecture.mp4
    ?Expires=1705305600
    &Signature=j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9c0d1e2f3g4h5i6j7k8l9m0
    &Key-Pair-Id=APKAIOSFODNN7EXAMPLE
- Upload in chunks[]
- Metadata in SQL
    CREATE TABLE files (
        id              UUID PRIMARY KEY,
        user_id         UUID NOT NULL,
        filename        VARCHAR(255),
        size_bytes      BIGINT,
        content_type    VARCHAR(100),
        storage_key     VARCHAR(500),  -- s3://bucket/user123/files/abc-123.pdf
        status          VARCHAR(50),   -- 'pending', 'uploading', 'completed', 'failed'
        created_at      TIMESTAMP,
        updated_at      TIMESTAMP
    );
- Cloud Features
    > Temporary Upload URLS (Presigned URLS)
    > Multipart Uploads (in chunks[])
    > Event Notifications (S3 Event Notification)
    > CDN Signed URLs (Cloudffront signed URLs)
    > Cleanup Policies
- Common Examples
    Youtube
    Instagram
    Dropbox
    whatsapp
- when NOT to use
    > Small files <10MB can use normal server route
    > Synchronous validation requirements: your validaitng CSV info as it is uploaded
- "What if the upload fails at 99%?"
    chunked uploads
- "How do you prevent abuse?"
    Before letting any user access the files- do your backend processes, like virus scans, contnet validation ,etc
- "How do you handle metadata?"
    uploads/{user_id}/{timestamp}/{uuid}
- "How do you ensure downloads are fast?"
    CDNs


# Real Time updates
- Layer 4 Transport Layer: TCP / UDP
- Layer 7 Application Layer: DNS, HTTP, Websockets, WebRTC
- Load Balancers
    Layer 4
    Layer 7
- simple polling (simple, stateless)
    async function poll() {
        const response = await fetch('/api/updates');
        const data = await response.json();
        processData(data);
    }

    // Poll every 2 seconds
    setInterval(poll, 2000);
- long polling:  the client makes a request to the server and the server holds the request open until new data is available
    // Client-side of long polling
    async function longPoll() {
    while (true) {
        try {
            const response = await fetch('/api/updates');
            const data = await response.json();
            
            // Handle data
            processData(data);
        } catch (error) {
            // Handle error
            console.error(error);
            
            // Add small delay before retrying on error
            await new Promise(resolve => setTimeout(resolve, 1000));
        }
    }
    }
- Server Sent Events
    > Client established stateful SSE connection
    > Sever keeps connection open, and sends when new data comes in
- Websockets
    > Bidirectional & Stateful
    > Load Balancer "Least Connections" is ideal for websockets, because the connection is persistent
- WebRTC: Peer-to-Peer
    > Video calls
- Pushing via Pub/Sub (redis cluster)
- Common Interview Examples
    > Chat Applications - The classic real-time use case. Messages must appear instantly across all participants. WebSockets handle the bidirectional communication perfectly, while pub/sub distributes messages to the right servers
    > Live Comments
    > Document Co-editing
    > Live Dashboards
- Avoid real-time updates when you can get away with a simple polling model. If you're not latency sensitive, polling is a great baseline and minimizes complexity
- "How do you handle connection failures and reconnection?"
    For recovery, you need to track what messages or updates a client has received. When they reconnect, they should get everything they missed
- "What happens when a single user has millions of followers who all need the same update?"
 cache the update once and distribute through multiple layers
- "How do you maintain message ordering across distributed servers?"
Vector clocks or logical timestamps help establish ordering relationships between messages. Each server maintains its own clock, and messages include timestamp information that helps recipients determine the correct order.

# Dealing with Contention
multiple processes compete for the same resource at the same time, like booking the last concert ticket or bidding on an auction item.
- "read-modify-write cycle" isnt "atomic" leading to issues
- Conditional Writes: Guard the resource people are fighting over
    > Transactions
    > UPDATE tickets
    SET status = 'sold', user_id = 'user123'
    WHERE concert_id = 'weeknd_tour'
    AND seat_number = 'A15'
    AND status = 'available';
- Pessimistic Locking
    > explicit row lock acquires locks up front
    > Pesimistic = assumes conflicts will happen and blocks them
    >   BEGIN TRANSACTION;

        -- Lock the open seats in this section while we pick a block
        SELECT seat_number FROM seats
        WHERE concert_id = 'weeknd_tour'
        AND section = 'floor'
        AND status = 'available'
        FOR UPDATE;

        -- App scans the result, finds A15-A18 open and adjacent, then claims them
        UPDATE seats
        SET status = 'sold', user_id = 'user123'
        WHERE concert_id = 'weeknd_tour'
        AND seat_number IN ('A15', 'A16', 'A17', 'A18');

        COMMIT;
    > "FOR UPDATE": locks every row the SELECT returns
- Optimistic concurrency control (OCC)
    > assumes conflicts are rare and detects them after the fact instead of blocking to prevent them
    > version number
- Isolation Levels
    > READ UNCOMMITTED - Can see uncommitted changes from other transactions (rarely used)
    > READ COMMITTED - Can only see committed changes (default in PostgreSQL)
    > REPEATABLE READ - Same data read multiple times within a transaction stays consistent (default in MySQL)
    > SERIALIZABLE - Strongest isolation, transactions appear to run one after another
- Distributed Locks
    > Redis with TTL
    > Database columns - If you're already on a database, a lock is just two columns on the row, one for who holds it and one for when the hold expires
    > ZooKeeper/etcd - These are purpose-built coordination services designed specifically for distributed systems
- "How do you prevent deadlocks with pessimistic locking?"
ordered locking, which means always acquiring locks in a consistent order regardless of your business logic flow
- "How do you handle the ABA problem with optimistic concurrency?"
a dedicated version column that increments on every update, regardless of whether any business data changed
- "What about performance when everyone wants the same resource?"
queue to follow taylor swift w/ eventual consistency

# Multi Step Processes
Common Example: Amazon
    Charge Payment -> Reserve Inventory -> Create Label -> Pick nad Pack -> Send confirmation email -> wait for pickup
Solutions
    - Single Server Primitives (API server talks to Payment service, inventory service, shipping service, etc)
    - Saga Pattern
        > Choreography
        > Event-Driven Choreography
            Kafka log, our workers (Pyment, Inventory, Shipping) then all start working on it
            Workers consume events
        > Orchestration
        > Workflow:  a reliable, long-running process that can survive failures and continue where it left off
    - AWS Step Functions is the managed, serverless counterpart. You define workflows as state machines in JSON,
Interview Examples
    - Payment systems
    - Human-in-the-loop workflows (Uber)
When not to use
- Simple async processing: If you just need to resize an image or send an email, use a message queue. Workflows are overkill for single-step operations.
- "What happens if the process running your saga crashes partway through?"
The fix is durable progress. You record which steps have completed to a store that survives the crash, so on restart the coordinator reads that record and knows exactly where it left off.
- "How will you handle updates to the workflow?"
versioning of workflow
- "How do we deal with external events?" "Your workflow needs to wait for a customer to sign documents. They might take 5 minutes or 5 days. How do you handle this efficiently?"
External systems deliver signals through the workflow engine's API.
- "How can we ensure X step runs exactly once?"
idempotent
Storing off a key to a database (e.g. the idempotency key of the email) and then checking if it exists before performing the irreversible action is a common pattern to accomplish this.

# Managing Long Running Tasks
- "Generate PDF Report" -> Job Queue -> Workers -> Update Job Status in Database & create Job ID
- How to
    1) Message Queue
    2) Pool of Workers
- Kafka Queue
- Workers
    > "Normal" servers
    > Serverless functions (Lambda, cloud functions) - Each job triggers a function execution that scales automatically
    > Container-based workers (on Kubernetes or ECS)
- How it works
    1) Web server creates job record in database (status "pending")
    2) Server pushes message to queue w/ Job ID
    3) Server returns Job ID to client immediately
    4) Worker pulls messages from queue, fetches job details from database
    5) Worker updates job status to "processing"
    6) Worker does stuff.....
    7) Worker stores result (in s3 for files, database for metadata, etc)
    8) Worker updates job status to "completed" or "fail"
- When to use Long Running Tasks
    > When they mention specific slow operations - The moment you hear "video transcoding", "image processing", "PDF generation", "sending bulk emails", or "data exports"
    > When they ask about scale or failures - "With async workers, if one crashes mid-job, another worker picks it up from the queue. No user requests are lost."
- "what happens if the worker crashes while working the job?"
the job will be restarted by another worker
- "What happens if a job keeps failing? Maybe there's a bug in your code or bad input data that crashes the worker every time."
Dead Letter Queue (DLQ). After a job fails a certain number of times (typically 3-5), you move it to a separate queue instead of retrying again
- Preventing Duplicate Work
 Idempotency keys (combining user ID + action + timestamp)
-  "Some of your PDF reports take 5 seconds, but end-of-year reports take 5 hours. They're all in the same queue. What problems does this cause?"
The solution is to separate queues by job type or expected duration. Quick reports go to a "fast" queue with many workers. Complex reports go to a "slow" queue with fewer, beefier workers

# Elasticsearch
- Document
    {
        "id": "XYZ123",
        "title": "The Great Gatsby",
        "author": "F. Scott Fitzgerald",
        "price": 10.99,
        "createdAt": "2024-01-01T00:00:00.000Z"
    }
- Index: Collection of Documents ("Books")
- Mapping: Schema of an index
    "properties": {
        "id": { "type": "keyword" },
        "title": { "type": "text" },
        "author": { "type": "text" },
        "price": { "type": "float" },
        "createdAt": { "type": "date" }
    }
- Geospatial Search
    > geo_distance() - documents within a radius of a point
- Sorting
- Pagination & Cursors
- Updated by using "CDC (Change Data Capture)"
- Designed for "read heavy" workloads
- ElasticSearch is Eventual Consistency

# PostgreSQL
- Full Text earch (GIN indexes)
- Geospatial Search (PostGIS)
- Covering Index: store all the data we need right in the index itself
    // A covering index that includes all needed columns
    CREATE INDEX idx_posts_user_include 
    ON posts(user_id) INCLUDE (title, created_at);
- Partial Index
    -- Standard index indexes everything
    CREATE INDEX idx_users_email ON users(email);  -- Indexes ALL users

    -- Partial index only indexes active users
    CREATE INDEX idx_active_users 
    ON users(email) WHERE status = 'active';  -- Smaller, faster index
- Writes
    - Sharding: multiple PostgreSQL instances (rather than 1 instance)
        Shard by "user_id"
    - Vertical Scaling: Better hardware for our node
    - Batching: many operations executed in 1 transaction
        // Instead of 1000 separate inserts:
        INSERT INTO likes (post_id, user_id) VALUES 
        (1, 101), (1, 102), ..., (1, 1000);
    - Write Offloading: Async writes (adding to a kafka queue)
        activity logging, analytics, last seen timestamps
    - Table Partitioning
        Time Based Partitioning
            // let's say we have a posts table that grows by millions of rows per month:
            CREATE TABLE posts (
                id SERIAL,
                user_id INT,
                content TEXT,
                created_at TIMESTAMP
            ) PARTITION BY RANGE (created_at);

            -- Create partitions by month
            CREATE TABLE posts_2024_01 PARTITION OF posts
                FOR VALUES FROM ('2024-01-01') TO ('2024-02-01');
- Read Replicas
    - High Availability
        - 1 node will be the "Primary" Leader
    - Our Social Media Feed System can read from Replicas
- ACID
    - Transactions
        - Row Level Locking (during Transactions): "FOR UPDATE"
            BEGIN;
            -- Lock the item and get current max bid
            SELECT maxBid FROM Auction WHERE id = 123 FOR UPDATE;

            -- Place new bid if it's higher
            INSERT INTO bids (item_id, user_id, amount) 
            VALUES (123, 456, 100);

            -- Update the max bid
            UPDATE Auction SET maxBid = 100 WHERE id = 123;
            COMMIT;
        - Example
            BEGIN;
            UPDATE accounts SET balance = balance - 100 WHERE id = 1;
            UPDATE accounts SET balance = balance + 100 WHERE id = 2;
            COMMIT;
- When to use other databases
    - Extreme Write Throughput
        NoSQL databases (like Cassandra) for event streaming
        Key-value stores (like Redis) for real-time counters       
    - Global Multi-Region Requirements 
    - Simple Key-Value Access Patterns
        Redis for in-memory performance
        DynamoDB for managed scalability
        Cassandra for write-heavy workloads

# SQL Basics
- Many to Many
    CREATE TABLE likes (
        user_id INTEGER REFERENCES users(id),
        post_id INTEGER REFERENCES posts(id),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (user_id, post_id)
    );
- Consistency (Data Integrity)
    balance DECIMAL CHECK (balance >= 0),
- Isolation (Concurrent Transactions)
    BEGIN;
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;  -- Default level
    -- or REPEATABLE READ
    -- or SERIALIZABLE
    COMMIT;