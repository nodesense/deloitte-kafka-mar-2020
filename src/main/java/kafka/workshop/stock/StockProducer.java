package kafka.workshop.stock;

import kafka.workshop.models.Stock;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

// kafka-topics --zookeeper localhost:2181 --create --topic stocks --replication-factor 1 --partitions 3

public class StockProducer {
    public static String BOOTSTRAP_SERVERS = "localhost:9092";
    // FIXME: Always check
    public static String SCHEMA_REGISTRY = "http://localhost:8071"; //default
    public static String TOPIC = "stocks";

    static Random random = new Random();

    public static Stock getNextRandomStock() {
        String id = UUID.randomUUID().toString();
        Stock stock = new Stock();
        stock.setId(id);
        stock.setQty(random.nextInt(1000));
        return stock;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Properties props = new Properties();
        // hardcoding the Kafka server URI for this example
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("acks", "all");
        props.put("retries", 0);


        // Key/Value serializer for Avro format
        props.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");

        // PATH to SCHEMA REGISTRY
        // Very first, The producer automatically register the avro schema with Schema Registry
        // what is the schema name?
        // <<topicname>>-value, <<topicname>>-key
        props.put("schema.registry.url", SCHEMA_REGISTRY);

        Producer<String, Stock> producer = new KafkaProducer<String, Stock>(props);

        for (int i = 0 ; i < 100; i++) {
            Stock stock = getNextRandomStock();

            ProducerRecord<String, Stock> record = new ProducerRecord<>(TOPIC, stock.getId(), stock);

            RecordMetadata data = producer.send(record).get(); // sync

            System.out.println("Data Written partition: " + data.partition() + " ofset:" + data.offset());
            Thread.sleep(5000);
        }

        producer.close();
    }


    }
