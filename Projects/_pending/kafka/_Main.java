package Projects._pending.kafka;

public class _Main {
    

    /*
        Design Kafka
        https://codewitharyan.com/tech-blogs/design-pub-sub-model-like-kafka
        https://medium.com/@choudharys710/lld-machine-coding-with-implementation-messaging-queue-1fe1162b55aa 

        Requirements
        - Topic: feed name (publishers & consumers)
        - Partition: topics are split into partitions
            Allows Horizontal Scaling
            Each Partition is Ordered & Immutable
        - Offset = sequential id
            Producer Offset: 7
            Consumer offset: 3
        - Producer
        - Consumer
            Consumer Groups
        - Consumer Lag: differnece in offset between the Producer and Consumer
        - Consumer Pulling Mechanisms
            Push model: topic pushes data directly into consumer
            Pull model: consumer actively pulls data from topic (most used)
    */
}
