ZooKeeper Clusters

Quoram  of 3 ZooKeeper

Group 3 or 5 or 7 or 9

Strong Consistency

CAP Theory 
    C - Consistency
    A - Availablity 
    P - Partitions
    
KAFKA Brokers - AP (no consistency)
ZooKeepers - CA (No paritions support)


ZooKeeper1
ZooKeeper2
ZooKeeper3

Client write to ZooKeeper1, ZooKeeper ensure that ZooKeeper2, 3 are updated
Now Client read from Zookeeper 3, the data should available


server.1=    0.0.0.0:2888:3888

Server IP
    2888 is incoming port, ZooKeeper listen for sync
    3888 for outgoing port

https://stackoverflow.com/questions/30940981/zookeeper-error-cannot-open-channel-to-x-at-election-address


k10.training.sh
k13.training.sh
k14.training.sh

In k10.training.sh /zookeeper/server  1

nano $KAFKA_HOME/etc/kafka/zookeeper.properties

```
tickTime=2000
dataDir=/tmp/zookeeper
clientPort=2181
maxClientCnxns=60
initLimit=10
syncLimit=5
server.1=0.0.0.0:2888:3888
server.2=k13.training.sh:2888:3888
server.3=k14.training.sh:2888:3888
```

    nano /tmp/zookeeper/myid

paste below

1
 
 
    zookeeper-server-start $KAFKA_HOME/etc/kafka/zookeeper.properties

 
In server2 /k13.training.sh/zookeeper.2

nano $KAFKA_HOME/etc/kafka/zookeeper.properties


```
tickTime=2000
dataDir=/tmp/zookeeper
clientPort=2181
maxClientCnxns=60
initLimit=10
syncLimit=5
server.1=k10.training.sh:2888:3888
server.2=0.0.0.0:2888:3888
server.3=k14.training.sh:2888:3888
```

mkdir -p /tmp/zookeeper

    nano /tmp/zookeeper/myid

paste below

2

    zookeeper-server-start $KAFKA_HOME/etc/kafka/zookeeper.properties


In server3 / zookeeper.3/k14.training.sh
nano $KAFKA_HOME/etc/kafka/zookeeper.properties


```
tickTime=2000
dataDir=/tmp/zookeeper
clientPort=2181
maxClientCnxns=60
initLimit=10
syncLimit=5
server.1=k10.training.sh:2888:3888
server.2=k13.training.sh:2888:3888
server.3=0.0.0.0:2888:3888
```

mkdir -p /tmp/zookeeper

    nano /tmp/zookeeper/myid

paste below

3

 
zookeeper-server-start $KAFKA_HOME/etc/kafka/zookeeper.properties


kafka-topics --zookeeper k10.training.sh:2181 --create --topic topic1 --replication-factor 1 --partitions 3
kafka-topics --zookeeper k10.training.sh:2181 --describe --topic topic1  
kafka-topics --zookeeper k13.training.sh:2181 --describe --topic topic1  
kafka-topics --zookeeper k14.training.sh:2181 --describe --topic topic1  


kafka-topics --zookeeper k10.training.sh:2181 --create --topic topicfortesting --replication-factor 1 --partitions 3
kafka-topics --zookeeper k10.training.sh:2181 --describe --topic topicfortesting  
kafka-topics --zookeeper k13.training.sh:2181 --describe --topic topicfortesting  
kafka-topics --zookeeper k14.training.sh:2181 --describe --topic topicfortesting  


zookeeper-shell k10.training.sh:2181
zookeeper-shell k13.training.sh:2181
zookeeper-shell k14.training.sh:2181


start broker

change the settings server.properties
 zookeepr to inclue all zookeeper
 
 kafka-server-start $KAFKA_HOME/etc/kafka/server.properties

