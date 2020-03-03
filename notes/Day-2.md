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