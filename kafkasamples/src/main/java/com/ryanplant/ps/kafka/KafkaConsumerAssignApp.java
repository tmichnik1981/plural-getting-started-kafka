package com.ryanplant.ps.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Properties;

public class KafkaConsumerAssignApp {
    public static void main(String[] args){
        Properties props = new Properties();

        props.put("bootstrap.servers","localhost:9091, localhost:9093");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer myConsumer = new KafkaConsumer(props);
        ArrayList<TopicPartition> partitions = new ArrayList<TopicPartition>();
        TopicPartition myTopicPart0 = new TopicPartition("my_topic", 0);
        TopicPartition myOtherTopicPart2 = new TopicPartition("my_other_topic", 2);

        partitions.add(myTopicPart0);
        partitions.add(myOtherTopicPart2);

        myConsumer.assign(partitions);

        try {
            while(true){
                ConsumerRecords<String, String> records = myConsumer.poll(10);
                for (ConsumerRecord<String, String> record : records ){
                    //process each record:
                    System.out.println(String.format("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s",
                                                     record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                }

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            myConsumer.close();
        }
    }
}
