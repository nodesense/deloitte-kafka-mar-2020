// OrderConfirmationProducer.java
package kafka.workshop.order;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

//kafka-topics --zookeeper localhost:2181 --create --topic order-confirmations-2 --replication-factor 1 --partitions 4

public class OrderProducer {
    //public static String BOOTSTRAP_SERVERS = "116.203.31.40:9092";

    public static String BOOTSTRAP_SERVERS = "localhost:9092";
    public static String TOPIC = "orders";

    static Random r = new Random();

    static String countries[] = new String[]{"IN", "USA", "UK", "AU"};

    // generate random data
    static Order nextOrder() {
        Order order = new Order();
        order.amount = 100.0 + r.nextInt(1000);
        order.orderId = String.valueOf(r.nextInt(1000000));
        order.customerId = String.valueOf(r.nextInt(1000000));

        String country = countries[r.nextInt(countries.length)];

        order.country = country;

        return order;
    }


    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to producer");

        Properties props = new Properties();

        props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 16000);
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // custom serializer.
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, OrderSerializer.class);

        //props.put("partitioner.class", "ai.nodesense.workshop.order.OrderConfirmationPartitioner");
        props.put("partitioner.class", OrderPartitioner.class);


        // Key as string, value as Order
        Producer<String, Order> producer = new KafkaProducer<>(props);


        Random r = new Random();

        int counter = 100;
        for (int i = 0; i < 100; i++) {
            Order order = nextOrder();
            // producer record, topic, key (null), value (message)
            // send message, not waiting for ack

            // Key is a string
            // Value is Order
            ProducerRecord<String, Order> record = new ProducerRecord<>(TOPIC,
                    order.country, // key serializer
                    order); // value serializer
            System.out.println("Sending " + order.orderId);

            // during send, producer calls Serializer, convert order into bytes
            // calls the custom partitioner, assign partition

            producer.send(record);
            System.out.printf("order send %s sent\n", record);
            Thread.sleep(5000); // Demo only,
        }

        producer.close();
    }
}