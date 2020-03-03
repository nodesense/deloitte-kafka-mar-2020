Limitations with single system architecture

Kafka

Brokers
    Store the messages
    producer push message to broker
    consumer pull message from broker
    
Zookeepers
    heart beat of brokers
    centralized configuration service
        clusters
        brokers id
        topics meta meta
        
producers
consumers

Topics
    Logical name, to group the messages
    
Paritions
    subset of topic data
    at least 1 partition
    where the messages are stored in the partition
    
    producer decides where the messages should go, partitions is decided by producer
    
    strategies for partitions
         hash of message/key
         if key is null, then round robin
         
Leader  - 
        A leader per partition
        All message are written/read there
Followers 
        Messages are replicated here
         
Is it Kafka follow Centralized Master/Replicas architecture? Ans: NO
    Distributed, any Kafka broker can be a leader for a partition 
        and also a replica for other partitions
    
offsets  
    offsets is a position within the partition
    automatically incrementing value
    where message is stored
       
messages
    key
    value

Consumer Group
    is a group of consumer instances/programs/process
    have a unique id per consumers group
    
    parallism - process more messages at same time
    consumer splits the available partitiosn amoung themselves
    
    
    A partition can be shared by multiple consumers, Is it true or false? Ans: FALSE
    A consumer can susbcribe from multiple partitions, is it true or false? Ans: TRUE
    

How the messages are stored in storage/hdd
    greetings-0
        000000000000000.log - messages/data
        000000000000000.index - offsets to messsage location in 000000000000.log file
        000000000000000.timeindex - mapping between timestamp to the index offset 000000000000000.index 
        
    greetings-1
             000000000000000.log - messages/data [1 GB]
             000000000000000.index - offsets to messsage location in 000000000000.log file
             000000000000000.timeindex - mapping between timestamp to the index offset 000000000000000.index 
            
            000000001000000.log
            
kafka-console-producer
kafka-console-consumer
kafka-groups

zookeeper-server-start
kafka-server-start
        server.properties
            broker id
            listener port/host
            kafka logs location
             

KAFKA_HOME
JAVA_HOME
Intellij
Maven
SimpleProducer.java

replications with local clusters
leaders/followers
in sync replica    