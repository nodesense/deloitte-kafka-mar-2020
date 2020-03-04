Serialiation
    Producer
    Conversion
        Input  - Key Or Value [String, Long, Order, JSON, etc]
        Output - Byte array
         
        
Deserialization
    Consumer
    Conversion
        Input - Byte array
        Output - String, Long, Order, Invoice etc        
        
        
Kafka Producer Properties
    Meta Data

    Ack 
        "0" - No Ack/Broker Received message
        1 - Ack, The message should be with broker, persisted to lead broker disks
        all - Ack,Persisted to Leader Broker disk, also replicas disk
    
    Batch Size
    
Partition
    Custom Partition creation
    
Kafka consumer
    Auto commit
    Manual Commit
    Consumer Rebalancing
    
Avro
Schema Registry
