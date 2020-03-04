package kafka.workshop.product;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import kafka.workshop.models.Product;
import org.apache.avro.data.TimeConversions;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.HashMap;
import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class ProductSpecificConsumer {
    public static String TOPIC = "db_products";

    public static String BOOTSTRAP_SERVERS = "k1.training.sh:9092";
    // FIXME: Always check
    public static String SCHEMA_REGISTRY = "http://k1.training.sh:8081"; //default

    public static void main(String[] args) {


        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(GROUP_ID_CONFIG, "product-specific-consumer-example"); // offset, etc, TODO
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");

        //props.put(GROUP_ID_CONFIG, UUID.randomUUID().toString());
        // props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");


        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        props.put("schema.registry.url", SCHEMA_REGISTRY);

        // <Key as string, Value as string>
        KafkaConsumer<String, Product> consumer = new KafkaConsumer<>(props);

        // Subscribe for topic(s)
        consumer.subscribe(singletonList(TOPIC));

        System.out.println("Consumer Starting!");

        while (true) {
            // poll (timeout value), wait for 1 second, get all the messages
            // <Key, Value>
            ConsumerRecords<String, Product> records = consumer.poll(ofSeconds(1));
            // if no messages
            if (records.count() == 0)
                continue;


            // Iterating over each record
            for (ConsumerRecord<String, Product> record : records) {

                System.out.printf("partition= %d, offset= %d, key= %s, value= %s\n",
                        record.partition(),
                        record.offset(),
                        record.key(),
                        record.value());


                System.out.println(record.value().getPrice());

                GenericRecord genericRecord = (GenericRecord) record.value();


                HashMap<String, String> specificDeserializerProps = new HashMap<String, String>();
                specificDeserializerProps.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY);
                specificDeserializerProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

                CachedSchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(SCHEMA_REGISTRY, 100);

                KafkaAvroDeserializer specificAvroDeserializer = new KafkaAvroDeserializer(schemaRegistryClient, specificDeserializerProps);

                // specificAvroDeserializer.deserialize("db_products");

                SpecificData.get().addLogicalTypeConversion(new TimeConversions.TimestampMillisConversion());

                // FIXME:
                Product product = (Product) SpecificData
                                                    .get()
                                                 //   .addLogicalTypeConversion(new TimeConversions.TimestampMillisConversion())
                                                     .deepCopy(Product.SCHEMA$, genericRecord);

                // SpecificData.get().addLogicalTypeConversion(new TimeConversions.TimestampMillisConversion());

                System.out.println("Generic Product data " + genericRecord);
                System.out.println("Product ID " + product.getId());
                System.out.println("Product Name " + product.getName());
                System.out.println("Price  " + product.getPrice());
            }
        }
    }
}