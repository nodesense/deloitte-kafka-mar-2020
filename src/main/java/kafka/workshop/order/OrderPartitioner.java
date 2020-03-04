// OrderPartitioner.java
package kafka.workshop.order;


import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

// import io.confluent.common.utils.Utils;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;


// hash partition - strategy
// if key null, round robin  - strategy
// custom java strategy

public class OrderPartitioner  implements  Partitioner  {
    @Override
    public void configure(Map<String, ?> configs) {
        // config properties
    }

    // should return a partition id starting 0, upto (total partitions - 1)


    // called during producer.send
    // called after serializer
    @Override
    public int partition(String topic,
                         Object key,  // key object as ref, String
                         byte[] keyBytes, // key in serialized bytes
                         Object value,  // Order object
                         byte[] valueBytes, // serialized json bytes
                         Cluster cluster) {

        System.out.println("partitioner called ");
        int partition = 0;

        // how many total partitions?
        // orders topic, we have total 3 partitions
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);

        int numPartitions = partitions.size();

        // write your custom logic/data driven logic to decide the partition number

        String country = (String) key;
        Order orderConfirmation = (Order) value;

//
//        if (orderConfirmation.amount > 10000) {
//            partition = 0;
//        } else if (orderConfirmation.amount > 5000) {
//            partition = 1;
//        }


        if (country.equals(("IN"))) {
            partition = 0;
        } else if (country.equals(("USA"))) {
            partition = 1;
        }  else if (country.equals(("UK"))) {
            partition = 2;
        } else {
            partition = 2; // other countries data goes into partition 2
        }

        // Any custom partitions can be done


        // Kafka default is hash partition only
        // Other option, use murmur2 algorithm
        // or use hash key
        // -1 does ensure that 0 is not taken
        // partition = Math.abs(Utils.murmur2(country.getBytes()) % (numPartitions - 1)) + 1;


        System.out.printf("Producer Partition  %s => %d\n", country, partition);


        // return the partition
        return partition;
    }

    @Override
    public void close() {
        // cleanup
    }
}