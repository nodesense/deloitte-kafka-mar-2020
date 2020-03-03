// Order.java
package kafka.workshop.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.IOException;
// POJO
// We need to serialize the data inorder to send to other system
// JSON, XML, Java serialization, Parquet,  Avro, etc, CSV
// Convert in memory java objects to JSON then JSON to bytes and dispatch bytes to Kafka brokers

// when we receive kafka broker, data is coming as bytes
// bytes to JSON TEXT to Java Object

public class Order {
    public String orderId;
    public Double amount;
    public String customerId;
    public String country;


    private static ObjectMapper objectMapper = new ObjectMapper();

    // seriazlize object to JSON text
    public String toJSON() throws IOException {
        return  objectMapper.writeValueAsString(this);
    }

    // deserializer Json text into Java Object
    public static Order fromJson(String json) throws  IOException {
        return objectMapper.readValue(json, Order.class);
    }
}