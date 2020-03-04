Java Apps

Simple consumer/producer
Understanding the SDK

Custom Serializer/Deserializer
    JSON
    
Custom Partitioner

Avro
    Producer
    Consumers
    
Schema Registry

# Serializer

String value = "ABC" - Developers/users see the data
ASCII Bytes - 65 66 67 -- this is how kafka store data

Conversion 
    Convert String to Bytes 
        "ABC" --> [65, 66, 67]
        
        1234567 - ASCII - [49, 20, 51, 55, 53, 54, 55]
                   HEX - 00 12 D6 87
                    
       123456789012 (bytes/json-    1C BE 99 1A 14 (5 bytes)
Producer which convert the key and value to byts format
    This is known as serialization
    take Java Object/value --> convert to Bytes
    If key is null, bytes shall be null
    
    KEY_SERIALIZER_CLASS_CONFIG
    VALUE_SERIALIZER_CLASS_CONFIG
    
Consumer, get the message in bytes format from broker
        Consumer should convert bytes to String, Long, POJO, etc
        
        Deserialization
        Convert Bytes  to String
             [65, 66, 67] --> "ABC"
             
             
        Consumer pull data from broker
         consumer process the data [success/FAILURE]
         after process, write to DB, other destination 
             
             
       Commit Offset
        Consumer Group to paritions,
        upto when  (offset on speicifc parition) the messgaes consumed
        
        Commit offset from consumer to broker
        to commit that consumer consumed the message
        
        
       Two Types of Commit Offset possible
            Automatic
                Consumer SDK itselfs shall commit to the broker after retrival
                no matter the message is processed or not
            Manual
                Programmer decide when to commit offset
                first process the data
                then commit the offset
                commitSync()
              
                internal topics __commit_offsets
                
                on exception, the offset is NOT commited
                
                __commit_offsets
                {
                  group: 'messages-consumer-group',
                  offset: 100,
                  parition: 1,
                  topic: 'messages'
                }
                
             
 Producer Side
    ack is a receipt from broker to producer upon receiving a message from producer
    
    producer to a message to broker
    broker send ack back to producer
    
    
    Producer has background worker threads to send message to broker
    
    send function first queue the record
    producer.send(record)
    
    the background thread, pull the record(s) from the queue 
    and send as batch to the brokers
                 
 Acks Types
            0 - 
                    producer send a message to Lead broker
                    Lead Broker receive the message, message is in memory,
                            not yet to persisted to disk
                            broker send ack back to producer
                            
                    Pros
                        Faster, Memory is faster
                    Cons
                        If the system/broker application crashes, before
                            writing messages to disk, then message is lost
                            
            1 - 
                 producer send a message to broker
                     Lead Broker receive the message, 
                     Write the message to disk
                     broker send ack back to producer
                        
                  Pros
                    The message safe, persistened in HDD in at least 1 system
                    
                  Cons
                    No replica updated yet,
                    If the Lead broker system permanantly not available, then
                    we loose the data 
                    Medium slow            
            all - 
                    producer send a message to broker
                     Lead Broker receive the message, 
                     Write the message to disk
                     Ensure that replicas updated the message to their disk
                     broker send ack back to producer
                      
              Pros
                Data is safe always, because replicas have backup
                
              Cons
                Slower
        
 Consumer Group
   Add instance to the group   [Kafka rebalance partitiosn amoung consumer instances]
   Remove instance from the group [Kafka rebalance partitiosn amoung consumer instances]
   
    Whenever new instance added/removed, 
        first kafka revokes all hte paritions from consumer instances
        then assign the partitions to consumer instance
        
        
        kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group rebalance-consumer

To know bytes consumed by given topic paritions

kafka-log-dirs  --describe --bootstrap-server localhost:9092  --topic-list greetings


kafka-topics --zookeeper localhost:2181 --create --topic orders --replication-factor 1 --partitions 3

Follow order consumer and producer with custom java code


JSON , 7.5 more than Binary data

Kafka Cluster - 2 TB x 7.5 = 15 TB data

Parse JSON - The time needed to parse JSON is more

read text 862004 
and convert to number 

{"orderId":862004,"amount":535.0,"customerId":275624,"country":"IN"}

Payload size is 68 chars - Java Unicode
  bytes 68 * 2 = 136 bytes 


Save Bytes?
    Less storage
    Transmit/latency reduces for products, consumers, brokers

{
    "orderId":862004, [Data Type/Int]     - 4 bytes
    "amount":535.0, [Data Type/Float]     - 4 bytes
    "customerId":275624, // DataType/Int  - 4 bytes
    "country":"IN" // DataType/String     - Variable length [null/0, 1 or n chars]
                                          - 2 bytes, IN

                                          5 bytes, India

                                          4 bytes + Total char length  - 4 + 2 (IN) = 6 bytes
}


18 Bytes is actula data (Binary format)

00 00 00 1f 00 00 11 00 00 00 11 23 00 02 75 85
----------- ----------- ----------- -----------
order        amount       custId      countryCode


AVRO SCHEMA - Apache Project


Bits and Bytes

Int i = 23232323243; 4 bytes

s = "23232323243"; // how many bytes 11 bytes

FileStream outputStream;

outputStream.writeInt(i) - 4 bytes - BINARY FORMAT
outputStram.writeString(s) - 11 bytes -- TEXT FORMAT

{
 amount: 32323232
}

BINARY FORMAT or TEXT Format


-- Define a avro schema called invoice.avsc in resources/avro folder
-- use Maven tools, to generate a POJO Model class for invoice.avsc


SCHEMA REGISTRY

 1. Copy files from https://github.com/confluentinc/schema-registry/tree/master/bin/windows
    to KAFKA_HOME/bin/windows
    
 2. Schema Registry by default runs on port 8081 [ Mcaffee Anti Virus uses the same port]
 Change the registry port to 8071
 
     edit the file below
     
     KAFKA_HOME/etc/schema-registry/schema-registry.properties
     
     change the port from 8081 to 8071
 
 3. Start the schema registry 
    
    schema-registry-start %KAFKA_HOME%\etc\schema-registry\schema-registry.properties
    
 4. Check browser
       http://localhost:8071
       
       List subjects
       
       Subject is a schema name
       With Kafka, Subjects has naming convention
       
       For a topic, topicA, the subject name shall be,
        topicA-key is a schema
        topicA-value is a schema
       
        invoices is a topic,
        
        invoices-key
        invoices-value
       
       http://localhost:8071/subjects
       
       [
       ]
              
              
              
       to get versions in the schemas
       
       
       http://localhost:8071/subjects/invoices-key/versions
       
       http://localhost:8071/subjects/invoices-value/versions
              

       
       to get schema from schema registry
       
        http://localhost:8071/subjects/invoices-key/versions/1
              
       http://localhost:8071/subjects/invoices-value/versions/1
          
       {} 
    
        http://localhost:8071/schemas/ids/1
        http://localhost:8071/schemas/ids/2
    
 
 
    