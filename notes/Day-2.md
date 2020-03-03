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


Meet at 2:00 PM
