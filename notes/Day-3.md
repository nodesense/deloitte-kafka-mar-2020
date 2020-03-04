KAFKA Connect

confluent command 


To Start all confluent + kafka services

confluent start

to know where zookeeper and kafka logs are stored

confluent current

destroy current kafka, zookeeper and all data

confluent destroy 

confluent start

to know whether services are running

confluent status


# Connectors command

to show all the available connectors

    confluent list connectors
    
    To install new connectors from confluent hub
    
     confluent-hub install confluentinc/kafka-connect-hdfs2-source:1.2.1-preview 


To know running connectors

    confluent status connectors
    
# Control Center?
    Dashboard system, with Web UI
    Metrics
    Manage Topics
    Manage Connectors
    Manage KSQL streams etc
    Triggers and Alert emails
    
    check browser
        http://yoursystem.training.sh:9021
        http://k1.training.sh:9021
        
        
        http://kx.training.sh:9021
                
                
# File Source
    Monitor the file data change
    if new lines added, publish to kafka topic
    
    
    


# Using File Source Connectors

### NANO EDITOR

    To open a file,

    nano filepath

    Ctrl + X - To Quit, it will to save or leave without saving file
    
    Ctrl + O  - Write to file


Create a property file for connector configureation/Source connector

    touch file-source.properties

    nano file-source.properties

and below content 
```
name=stock-file-source
connector.class=FileStreamSource
tasks.max=1
file=/root/stocks.csv
topic=stocks
```

#DONE


    touch stocks.csv

Load the connectors into kafka?, this will load source connector, keep watching for  stocks.csv changes

    confluent load stock-file-source -d file-source.properties

Check whether connector is running or not

    confluent status connectors

To know specific connector status

    confluent status stock-file-source

Put some data into csv file

    echo "1234,10" >> stocks.csv

    echo "1235,20" >> stocks.csv

    echo "1236,30" >> stocks.csv


    cat stocks.csv



# Second command prompt in your Windows/Linux Putty shell

    kafka-console-consumer --bootstrap-server k1.training.sh:9092 --topic stocks --from-beginning
   
   
   
   stocks.csv --> published to kafka -> kafka topics [stocks]/cluster -->  
                  source connector
                  
                  
   kafka clusters --> subscribe from kafka [stocks] --> write to outputfile.csv
                        sink connectors
                        
   
   create a new file source connector properties
   input file is orders.csv
   
   publish to topic-name: all-orders
   
   
   create a new file sink connector
    susbcribe from topic: all-orders
    
    write to file out-orders.csv
    
    
 # HDFS
 
 Kafka Producer, SimpleProducer.java, producing to topic greetings
 
 Hadoop setup, running on port 9000, DFS
 
 HDFS Sink Connector
 
 Susbcribe data from Kafka Topics, greetings,
   read the data, upto 3 count [changed], then flush to 
   HDFS file system
   
   Read from Kafka and write to Hadoop
 
 