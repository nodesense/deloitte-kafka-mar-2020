// OrderSerializer.java
package kafka.workshop.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;

// Convert OrderConfirmation object (java object) to serialized format/JSON bytes
// OrderSerializer is called by the producer
//  when producer.send(.., order)

public class OrderSerializer<T> implements Serializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Default constructor needed by Kafka
     */
    public OrderSerializer() {
        System.out.println("OrderSerializer object created ");
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        // producer props new Producer(props)
    }

    // invoked when producer.send(orderConfirmationObj)
    @Override
    public byte[] serialize(String topic, T orderConfirmationObj) {
        System.out.println("Orderconfirmation serialize called ");

        if (orderConfirmationObj == null)
            return null;

        try {
            // convert orderconfirmation to JSON bytes
            byte[] bytes = objectMapper.writeValueAsBytes(orderConfirmationObj);
            System.out.println("Bytes " + bytes);

            System.out.println("Bytes string " +  new String(bytes, StandardCharsets.UTF_8));
            return bytes;

        } catch (Exception e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

    @Override
    public void close() {
        // when producer closed, release memory/db connection
    }
}