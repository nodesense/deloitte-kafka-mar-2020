# STREAMs

# Stateless Stream
    Doesn't preserve old/previsous state
    
    Sum - stateless
    1, 2, 3 => 1 + 2 + 3 = 6
    
    Restart [stateless]
    
    4, 5, 6 => 4 + 5 + 6 = 15

# Stateful stream
    Preserve old/previous state
    
    Sum - Stateful 
    1, 2, 3 => 1 + 2 + 3 = 6 [State]
    
    Restart with state
    
    4, 5, 6 => [PrevState 6] +  4 + 5 + 6 = 21

Kafka Topics: orders/items_sold
KSTREAM
    (apple, 5), (orange, 1), (apple, 2), (apple, 1), (orange, 3)

KTable [Aggregate Sum of apple/orange sold]/group by

(apple, 5)
(orange, 1)
(apple, 2)
(apple, 1)
(orange, 3)

| name   | sum    |
-------------------
| apple  |  8     | (Change log)-> new row added, update existing 
| orange |  4     | (Change log)-> new row added

IN Ktable, For Every Change log, publish the latest added/updated record


KTable to KStream  [sum_sold_so_far]

(apple, 5)
(orange, 1)
(apple, 7)
(apple, 8)
(orange, 4)


=====
Kafka Stream using Java 
  1. Word Count 
        console-producer to produce to topics "lines"
          produce a sentence "how are you"
          
           produce a sentence "are you fine"
            
     Kafka Stream
        Parse "how are you" into words
        Parse "are you fine" into words

        
        how
        are
        you
        
        
        are
        you
        fine
        
        Word count [KTable]
        
        how - 1
        are - 1 + 1 = 2
        you - 1 + 1 = 2
        fine - 1


        Convert ktable changes into kstream
        and produce a topic word-count  
                key: wordname
                value: count value

        
  2. Invoices
            invoices from producers
            run the stream find orders count by state
                                amount by state
                                get values by windows
  
  
  map (pass an array as input, output of hte map also array )
  flatMap (pass an array as input, output shall be element of the array)
  flatMapValues (pass an array as input, output shall be element of the array)
  
---
KAFKA WINDOW

Time Bound
Stream is a continue unbouned data, ...........................

The problem so far: How many apple/words sold/count so far?

how many apples sold every hour?
how many apples sold last hour?

Three Types of Windows
   Non-Overlapping Window [Thumbling Window]
        how many apples sold every hour? 
            hours mean? (6:00 to 6:59:59.999, 7:00 to 8:00, 8:00 to 9:00.....)
   Over-lapping Window [Hopping Window]
        how many apples sold last hour?
            Ans may vary? 
                if the question is asked at 12:08 PM?
                    one may answer: 11:00 to 12 [Non-overlapping window can solve this]
                    other may answer: 11:08 to 12:08 [Overlapping window]
                    
                if the question is asked at 12:10 PM?
                       may answer: 11:10 to 12:10 [Overlapping window]
                                   (from prev question, 
                                                we have data 11:08 to 12:08 +
                                                12:08 to 12:10)
                       
   Session Window
    based on user activity
    non overlapping window
    but one diff with non-overlapping window, that is, the session is not fixed, may vary from user to user
    
    2:00 PM 
    
    KSQL
    
    Repartition tools/replicas
    ZooKeeper Cluster
    Replicator
    Control Center
    
    
    Users {
       userId
       regionId
       gender
       }
       
       pageviews {
         userId
         pageId
      }
      
      join {
        userid
        regionId
        gender
        pageId
      }
      
       

Reparitions
  create a topic, 2 paritions, 3 replicas
     4 brokers, paritions and replicas assigned
     
     1 broker is down, it has some data
     migrate to other broker
   
   
kafka-topics  --create --zookeeper localhost:2181 --replication-factor 3 --partitions 2 --topic greetings

kafka-topics --list --zookeeper localhost:2181

kafka-topics --describe --zookeeper localhost:2181 --topic greetings

 

  touch  topics-to-move.json
   
  cat topics-to-move.json
  
  {
   "topics": [
             {"topic": "greetings"},
             {"topic": "invoices"}
            ],
       "version":1
  }
  
    kafka-reassign-partitions --topics-to-move-json-file topics-to-move.json --broker-list "localhost:9092" --generate --zookeeper localhost:2181




kafka-reassign-partitions --zookeeper localhost:2181 --broker-list "0,1,2" --topics-to-move-json-file topics-to-move.json --generate > full-reassignment-file.json


take the proposed content from full-assignment.json file and paste into new-partitions.json


kafka-reassign-partitions --zookeeper localhost:2181  --reassignment-json-file new-partitions.json --execute



touch partitions-to-move.json

nano partitions-to-move.json

{"partitions":
             [{"topic": "greetings",
               "partition": 1,
               "replicas": [1,2,4] }],              
  "version":1
}


kafka-reassign-partitions --reassignment-json-file partitions-to-move.json --execute

Retention 
    delete - removed from disk
    compact - it keeps the last key
             KEY IS USED as UNIQUE

LOG Compaction
    Publish the event
    Publish the latest product prices
    Publish latest setting changes
    
    all are stored into kafka, we cannot ourselve the messages
    
    Use case:
        I want only the last published value for a given key
        I don't have interest all the history of price changes
        


More Study 
    https://kafka.apache.org/documentation/
    https://towardsdatascience.com/log-compacted-topics-in-apache-kafka-b1aa1e4665a7
    

dirty ratio = the number of bytes in the head / total number of bytes in the log(tail + head)

kafka-topics --create --zookeeper k1.training.sh:2181 --topic product-prices --replication-factor 1 --partitions 1 --config "cleanup.policy=compact" --config "delete.retention.ms=100"  --config "segment.ms=100" --config "min.cleanable.dirty.ratio=0.01"

kafka-console-producer --broker-list k1.training.sh:9092 --topic product-prices --property parse.key=true --property key.separator=:

 kafka-console-consumer --bootstrap-server k1.training.sh:9092 --topic product-prices --property  print.key=true --property key.separator=: --from-beginning


 p1:10
 p2:10
 p1:20
 
    
    
    
  
 