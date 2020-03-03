// OrderDeserializer.java
package kafka.workshop.order;

import java.nio.charset.StandardCharsets;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;


// Convert the serialized json bytes   to order Object
// Consumer
// consumer.poll().. pull kafka msg, then convert the content to order object
public class OrderDeserializer implements Deserializer<Order> {
    private ObjectMapper objectMapper = new ObjectMapper();

    private Class<Order> tClass = Order.class;

    /**
     * Default constructor needed by Kafka
     */
    public OrderDeserializer() {
        System.out.println("OrderDeserializer created");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        System.out.println("PRops are " + props);
        tClass = (Class<Order>) props.get("value.deserializer");
    }

    //  deserialize method is invoked by consumer
    // when we call consumer.poll()
    // poll will get bytes array from broker
    // the byte is passed deserialize
    // deserialize return the Order object
    @Override
    public Order deserialize(String topic, byte[] bytes) {
        System.out.println("orderJsonDeserializer deserialize called ");
        if (bytes == null)
            return null;

        Order order = null;
        try {
            System.out.println("Bytes received " +  new String(bytes, StandardCharsets.UTF_8));
            //System.out.println("Class is " + SMS.toString());

            String o = new String(bytes, StandardCharsets.UTF_8).trim();
            System.out.println("Clean data " + o);

            // convert bytes to Order Object
            order = objectMapper.readValue(o.getBytes(), Order.class);
        } catch (Exception e) {
            System.out.println("Error while parsing ");

            //throw new SerializationException(e);
        }

        return order;
    }

    @Override
    public void close() {

    }
}