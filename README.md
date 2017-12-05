# Getting started with Apache Kafka

#### Links

- [Kafka Download](https://kafka.apache.org/downloads)
- [Producer config](https://kafka.apache.org/0102/javadoc/org/apache/kafka/clients/producer/ProducerConfig.html)
- [Consumer config](https://kafka.apache.org/0102/javadoc/org/apache/kafka/clients/consumer/ConsumerConfig.html)

#### Notes and Commands

- **Producers** - applications creating and sending messages

- **Topics** - destination locations
  - have names

- **Consumers** - retrieve messages

- **Broker** - sort, categorize and store  messages
  - demon service
  - access to filesystem
  - multiple nodes, cluster
  - scale
  - controller - master node/worker

- Controller
  - maintaines an invertory of what workers are available
  - maintaines a list of "Work items" that has been commited to workers
  - maintains a status of tasks
  - each task assign to a worker must be also assign to a worker's peer in case of failure

- Leader - a worker with a task
  - leaders pick up followers (other nodes) that will handle a task if leader failes

- Apache Zookeeper
  - centralized service for maintaining metedata about a cluster of distributed nodes
  - configuration information
  - health status
  - group membership
  - "ensemble" - cluster of zookeeper

- Topic
  - named feed, category of messages, mailbox
  - producers produce to a topic
  - consumers consume from a topic

- Kafka message
  - timestamp - when it was received
  - unique identifier
  - payload (binary)

- Message offset
  - placeholder - last read message position
  - established and maintained by the consumer
  - corresponds to the message identifier

- Message retention policy
  - Apache Kafka retains all published messages regardless of consumption
  - retention period is configurable (168 h by default)
  - retention period is defined on a per-topic basis
  - phisical storage resources can constrain message retention

- Transaction/commit logs
  - what happens, appends received events 
  - log entries physically stored and maintained
  - point of recovery
  - basis for replication and distribution

- Partitions
  - each topic has one or more partitions
  - partition == log
  - a partition is the basis for which Kafka can: Scale, become fault-tolerant, achieve higher levels of throughput
  - each partition is maintained on at least one or more Brokers

- Replication Factor
  - redundancy of messages
  - cluster resiliency
  - fault-tolerance
  - 2 or 3 minimum
  - configured on a per-topic basis

- ProducerRecord (message)
  - topic
  - partition (optional)
  - timestamp (optional)
  - key (optional)
  - value (content)

- Produced specifies a level o aknowlegement ("acks")

  - 0: fire and forget  - fast but not very reliable
  - 1: leader acknowledged - producer asks only leader brocker to confirm and store the message
  - 2: replication quarum acknowledged - all all replicas must confirm the receipt

- Handling sending error

  - retries setting
  - retry.backoff.ms - period between retries
  - max.in.flight.request.per.connection - 1 protects us from incorrect order (if retries happened) at hight throuput cost

- Subscribing 

  - subscribe() - for topics (dynamic/automatic), one topic (one-to-many partitions), many topics (many more partitions)
  - assign() - for partitions, one or more partitions regardless of topic, manual self-administering mode

- kafka-producer-pert-test.sh (.bat) - a tool for performance testing

  ```sh
  kafka-producer-perf-test --topic my_other_topic --num-records 50 --record-size 1 --throughput 10 --producer-props bootstrap.servers=localhost:9091 key.serializer=org.apache.kafka.common.serialization.StringSerializer value.serializer=org.apache.kafka.common.serialization.StringSerializer
  ```

- Consumer polling:

  - Fetcher fetches data/metadada by Consumer Network Client
  - Cunsumer sends the hearbeat
  - request for metadata
  - ConsumerCoordinator is aware of partition assingments and offsets (SubscriptionState keeps info about assignent), commits offsets to the cluster
  - SubscriptionState is updated about successful commits
  - when timeout expires, messages are returned to in-memory buffer to be deserialized and then processed

- Offset

  - last committed offset - confirmed by consumer
  - current position
  - uncommited offset (a difference between cur position and last commited)
  - read != commited

- Offset properties

  - enable.auto.commit 
  - auto.commit.interval - frequency commiting offset
  - auto.offset.reset 
    - "latest" (default) reading from the latest known commited offset
    - "earlier"
    - "none"

- __consumer_offsets - technical topic with offsets

- Offset modes:

  - automatic (default)
  - manual (enable.auto.commit = false)  full control of offsets commit

- commitSync - call after proccessing

  -  blocks the thread until receives response from cluster
  -  retries until succeeds or unrecoverable error
  -  retry.backoff.ms (default: 100)

- commitAsync

  - similar to  commitSync  
  - non blocking
  - no info about successful commit
  - no retries
  - callback option to dermine a status

- Consumer Groups

  - independent consumers working as a team
  - group.id setting
  - sharing the message consumption and processing load
  - heartbeat.intervals.ms
  - session.timeout.ms

- Group Coordinator - balances available Consumers to partitions

  - 1:1 Consumer to partition ratio
  - rebalances when topic changes, consumer failure

- Consumer Configuration

  - fetch.min.bytes - minimum number of bytes which must be returned from the poll
  - max.fetch.wait.ms - time to wait if there are not enough bytes
  - max.partition.fetch.bytes
  - max.poll.records

- Consumer position control

  - seek() - specifies the offset which you want to read
  - seekToBeginning()
  - seekToEnd()

- Flow control on consumer side

  - pause()
  - resume()

- Rebalance Listeners

  - informs you when the rebalance occurs 

- Usecases of usage Kafka

  - connectiong disparate source of data
  - large-scage data movement pipelines
  - big data integration

- Apache Avro - schemas

- Apache Kafka Connect - common framework for integration

- Kafka Streams - streaming based processing

  #### Instructions

###### Running Kafka basic configuration

1. Download Kafka
2. Go to kafka_2.12-1.0.0\config\ and edit zookeeper.properties
   - set **dataDir** where you want to store zookeeper data ie. `C:/zookeeper/data/`
3. Go to kafka_2.12-1.0.0\bin\windows
   - type `zookeeper-server-start.bat ..\..\config\zookeeper.properties`
4. Kafka config `kafka_2.12-1.0.0\config\server.properties`
5. Inside `kafka_2.12-1.0.0\bin\windows` run `kafka-server-start ..\..\config\server.properties`

###### Creating a topic

1. Go to `kafka_2.12-1.0.0\bin\windows`
2. Run `kafka-topics --create --topic my_topic --zookeeper localhost:2181 --replication-factor 1 --partitions 1`
   - my_topic - topic name
   - localhost:2181 - zookeeper instance

###### List topics

1. Go to `kafka_2.12-1.0.0\bin\windows`
2. Run `kafka-topics --list --zookeeper localhost:2181`

###### Open  a console producer 

1. Go to `kafka_2.12-1.0.0\bin\windows`
2. Run `kafka-console-producer --broker-list localhost:9092 --topic my_topic`
3. Type your text messages in a open console

###### Open a console consumer

1. Go to `kafka_2.12-1.0.0\bin\windows`
2. Run `kafka-console-consumer --zookeeper localhost:2181 --topic my_topic --from-beginning`

###### Modify the topic

1. Go to **kafka_2.12-1.0.0\bin\windows**
2. Run `kafka-topics --zookeeper localhost:2181 --alter --topic my_topic --partitions 4`

###### View topic state

1. Go to **kafka_2.12-1.0.0\bin\windows**
2. Run `kafka-topics --describe --topic my_topic --zookeeper localhost:2181`

###### Multi-broker Kafka Setup

1. Prepare a separate configuration 

   - use server.properties (prepare copies with names server-0.properties, server-1 etc)

   - change configuration entries which cannot be the same

     ```properties
     broker.id=0
     port=9092
     log.dirs=/tmp/kafka-logs
     ```

2. Run the brokers

3. Create a topic with 3 replicas

```shell
kafka-topics --create --topic replicated_topic --zookeeper localhost:2181 --replication-factor 3 --partitions 1
```

