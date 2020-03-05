package kafka.workshop;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

// input
// kafka-topics --zookeeper k1.training.sh:2181 --create --topic lines --replication-factor 1 --partitions 3
// output
// kafka-topics --zookeeper k1.training.sh:2181 --create --topic words-count --replication-factor 1 --partitions 1

// without any key
// kafka-console-producer --broker-list k1.training.sh:9092 --topic lines

// susbcribe for the words-count
// kafka-console-consumer --bootstrap-server k1.training.sh:9092 --topic words-count --from-beginning --property print.key=true  --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

public class WordCount {
    public static Properties getConfiguration() {
        final String bootstrapServers = "k1.training.sh:9092";
        String schemaUrl = "http://k1.training.sh:8081";

        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "word-count2-stream");
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "word-count2-stream-client");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Default if no Serializer/DeSerializer mentioned, this is used
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // Default if no Serializer/DeSerializer mentioned, this is used
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1 * 1000);
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);


        props.put("schema.registry.url", schemaUrl);
        return props;
    }


    public static void main(final String[] args) throws Exception {
        System.out.println("Running WordCount Stream");

        Properties props = getConfiguration();

        // Ser/Des for string
        final Serde<String> stringSerde = Serdes.String();
        // Ser/Des for Long type
        final Serde<Long> longSerde = Serdes.Long();

        // Use Builder to build topology
        // we define the processing topology of the Streams application.
        final StreamsBuilder builder = new StreamsBuilder();

        // Consumer
        final KStream<String, String> lines = builder
                .stream("lines");

        // Topology processor
        final KStream<String, String> nonEmptyLines = lines
                                                      .map ( (key, line) -> new KeyValue<>(key, line.trim()))
                                                      // output of hte map is passed to input to filter
                                                      // filter line doesn't have space around
                                                      .filter( (key, line) -> !line.isEmpty());
        // filter doesn't forward empty lines



        // just print the data
        nonEmptyLines.foreach(new ForeachAction<String, String>() {
            @Override
            public void apply(String key, String value) {
                System.out.println("Full Line " + key + " Value is  *" + value + "*");
            }
        });


        KStream<String, String> splitWords = nonEmptyLines
                .flatMapValues(line -> Arrays.asList(line.toLowerCase().split("\\W+")));

        // just print the data
        splitWords.foreach(new ForeachAction<String, String>() {
            @Override
            public void apply(String key, String value) {
                System.out.println("Word key " + key + " Value *" + value + "*");
            }
        });



        // splitwords has individual words as input, "apple", "orange", "apple"

        KTable<String, Long> wordCount = splitWords
                // _$ is a key, which is ignored by us
                // return value of group by used as key later
                .groupBy((_$, word) -> word)
                .count(); // against grouped words


        // just print the data
        wordCount.toStream().foreach(new ForeachAction<String, Long>() {
            @Override
            public void apply(String key, Long count) {
                System.out.println("Word key " + key + " Count *" + count + "*");
            }
        });


        // Produce the results to kafka topic
        // specific SerDes used here, this will not DEFAULT mentioned above
        wordCount.toStream().to("words-count", Produced.with(stringSerde, longSerde));



        // WINDOWS

        long windowSizeMs = TimeUnit.MINUTES.toMillis(5); // 5 * 60 * 1000L

        long advanceMs =    TimeUnit.MINUTES.toMillis(1); // 1 * 60 * 1000L

        // .windowedBy( TimeUnit.MINUTES.toMillis(5).advanceBy(TimeUnit.MINUTES.toMillis(1)))


        KStream< Windowed<String>, Long >  windowedStream = splitWords
                .groupBy((_$, word) -> word)
               .windowedBy(TimeWindows.of(Duration.ofSeconds(60))) // tumbling windows/non-overlapping
              //  .windowedBy( TimeWindows.of(TimeUnit.MINUTES.toMillis(5)).advanceBy(TimeUnit.MINUTES.toMillis(1))) // hopping/overlapping
                .count()
                .toStream();

        windowedStream
                .foreach((word, count) -> {
                    System.out.println("Windows 60 sec word is " + word.key() + " Start Time" + word.window().startTime() + " end time " + word.window().endTime() +  " Count is " + count);
                });


        KStream<String, Long> windowedCountStream = windowedStream
        // Convert Windows<String> to <Stirng, Value>
                                .map( (windowedKey, value) -> new KeyValue<>(windowedKey.key(), value) );


        // .through("windowed-word-count", Produced.with(stringSerde, longSerde))
        windowedCountStream
        .foreach((word, count) -> {
            System.out.println("Windows 60 sec word is " + word + " Count is " + count);
        });





        // build a stream from builder
        final KafkaStreams streams = new KafkaStreams(builder.build(), props);

        try {
            // stateful stream
             // streams.cleanUp();
        }catch(Exception e) {
            System.out.println("Error While cleaning state" + e);
        }
        // start the stream
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
