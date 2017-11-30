# Getting started with Apache Kafka

#### Links

- [Kafka Download](https://kafka.apache.org/downloads)

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
  - retention period is configurable (168 h)
  - retention period is defined on a per-topic basis
  - phisical storage resources can constrain message retention

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

