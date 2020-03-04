Partition - Subset of whole data set

[m1,m2,m3,m4,m4,m5,m6,m7,m8,m9,m10, ....] - whole set 

parition is a subset

For single partition - max 1 partition, that represent whole set
p0 - Partition 0 [m1,m2,m3,m4,m4,m5,m6,m7,m8,m9,m10, ....] - whole set 


two partitions  - no overlapping

p0 - [m1,m2,m3,m4,m4,m5] - subset
p1 - [m6,m7,m8,m9,m10] - subset 

to get all data, get p0 + p1


two partitions   - odd/even

p0 - [m1,m3,m5,m7,m9] - subset
p1 - [m2,m4,m6,m8,m10] - subset 

to get all data, get p0 + p1


two partitions   - round robin

p0 - [m1, m2] - subset
p1 - [m2, m4] - subset 

to get all data, get p0 + p1


thee partitions   - round robin

p0 - [m1, m4, m7] - subset
p1 - [m2, m5, m8] - subset 
p2 -  [m3, m6, m9 ] - subset 
to get all data, get p0 + p1 + p2

A topic is split into partitions 
at least 1 parition must be there...


----



Partitions - 2 numbers 
P0 - USA, USA
P1 - IN, IN, UK

IN - 101  (hash key) % 2 (MAX Partitions) = P-1
USA - 100 (hash key) % 2 = P-0
IN - 100  % 2 = P -1
USA - 101 (hash key)
UK - 99 % 2 = P-1
-----

Partitions - 3 numbers 
P0 -  UK
P1 -  USA, USA
P2 - IN, IN

IN - 101  (hash key) % 3 (MAX Partitions) = P-2
USA - 100 (hash key) % 3 = P-1
IN - 101  % 3 = P -1
USA - 100 (hash key)
UK - 99 % 3 = P-0


Messages with producer 
    [M00, M100, M200, M250, M280, M300, M320... ]

Topics - invoices, 3 patitions [0, 1, 2]
Topics - orders, 4 patitions [0, 1, 2, 3]

Brokers

c:\tmp\kafka-logs
                 invoices-0 [dir] - p0
                        0000000000000000.log - append only file
                        0000000000000000.index [offset, to log file]
                        0000000000000000.timeindex

                 invoices-1 [dir] - p1
                        0000000000000000.log [1 GB] Last offset - 100
                        0000000100000000.log [1 GB], first offset - 101, last offset 180

                        0000000200000000.log [1 GB], first offset 181,..
                        0000000300000000.log [ GB]

                        0000000000000000.index 
                            offset   filename                location in file
                            0        0000000000000000.log     0th byte
                            99       0000000000000000.log     950*1000*1000
                            101      0000000100000000.log     0th byte

                        0000000000000000.timeindex
                             timestamp                      offset
                           01,jan, 2020, 12:00:00            0
                           01,jan, 2020, 13:00:00            1
                           01, mar, 2020, 00:00:01          10000
                        
                 invoices-2  [dir] - p2
                 0000000000000000.log
                        0000000000000000.index
                        0000000000000000.timeindex
                        

                 orders-0 [dir...] - p0
                    0000000000000000.log
                        0000000000000000.index
                        0000000000000000.timeindex
                        
                 orders-1 - p1
                         0000000000000000.log
                        0000000000000000.index
                        0000000000000000.timeindex
                        
                 orders-2
                 orders-3


invoice-P0 -    [M0, M1 ] -- offset - 0
offsets           0, 1, ..........100000000000000 2^63

invoice-P1 -    [M100, M111 ]  
offsets           0,      1, ..........100000000000000 2^63

orders-P0 -    [Order100, Order 200 ]  
offsets           0,      1, ..........100000000000000 2^63



Fault Tolerance

invoices-3 paritions, replication-1 (includes master copy)
chances of failure is 100%

broker-0
    partition invoice-0
broker-1
    partition invoice-1
broker-2 - System fails, HDD broke
    partition invoice-2


Answer - Replication - exact copy of partition data is stored into another broker



invoices-3 paritions
replication - 2 - chance is failure is 50% instead of 100%

broker-0 - Fails - HDD
    partition invoice-0
    partition invoice-1
broker-1 - backup
    partition invoice-1
    partition invoice-2
broker-2 - backup
    partition invoice-2
    partition invoice-0



invoices-3 paritions
replication - 3 - chance is failure is 33%

orders-4 partitions
partitions : orders-0, orders-1, orders-2, orders-3
replication 3 - 3 copies of data

broker-0 
    partition invoice-0 [Leader Broker 0]
    partition order-0  [Leader]
    partition order-2  [Replica]
broker-1  
    partition invoice-1 [Leader Broker 1]
    partition invoice-2 [Replica]
    partition order-0  [Replica]
    partition order-3  [Leader]

broker-2
   partition invoice-0 [Replica]
   partition invoice-1 [Replica]
   partition invoice-2 [Replica]
   partition order-1  [Lead]
    partition order-3  [Replica]

broker-3
    partition invoice-1 [Replica]
    partition order-2  [Leader]
    partition order-1  [Replica]
    partition order-0  [Replica]
    partition order-3  [Replica]


broker-4
    partition invoice-0 [Replica]
    partition invoice-2 [Leader, Broker-4]
    partition order-2  [Replica]
    partition order-1  [Replica]
    
Within partitions and replicas system,

Per topic, per parition

1. There should be one lead for the partition
2. Lead broker accepts all the write messages from producers
3. Lead broker accept all the read messages from consumers
4. The replicas are not published to products/consumers




----

topics - orders
paritions - 5
producer(s) - 50 msg/sec

Instance 1 - 10 msg/sec
create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

Instance 2 - 10 msg/sec
create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


Instance 3 - 10 msg/sec
create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

Instance 4 - 10 msg/sec
create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

40 msg / sec consumed per consumers (4 instance)


each partitions is assigned to consumer



topics - orders
paritions - 5
producer(s) - 50 msg/sec

messages are stored in 5 paritions
orders-0
orders-1
orders-2
orders-3
orders-4

---

start 1 instance
Instance 1 - 10 msg/sec
create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

partitions 0, 1, 2, 3, 4 are all assgiend to Instance 1

---

start 2 instances
Instance 1 - 10 msg/sec
Instance 2 - 10 msg/sec

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

partitions 0, 4 are all assgiend to Instance 1
partitions 1,2,3 are all assgiend to Instance 2

--


start 3 instances
Instance 1 - 10 msg/sec
Instance 2 - 10 msg/sec
Instance 3 - 10 msg/sec

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

partitions 0 are all assgiend to Instance 1
partitions  2, 1 are all assgiend to Instance 2
partitions 4, 3 are all assgiend to Instance 3
--

start 4 instances
Instance 1 - 10 msg/sec
Instance 2 - 10 msg/sec
Instance 3 - 10 msg/sec
Instance 4 - 10 msg/sec

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


partitions 0 are all assgiend to Instance 1
partitions  2 are all assgiend to Instance 2
partitions 4, 3 are all assgiend to Instance 3
partitions 1 are all assgiend to Instance 4

--

start 5 instances
Instance 1 - 10 msg/sec
Instance 2 - 10 msg/sec
Instance 3 - 10 msg/sec
Instance 4 - 10 msg/sec
Instance 5 - 10 msg/sec

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

partitions 0 are all assgiend to Instance 1
partitions  2 are all assgiend to Instance 2
partitions 4 are all assgiend to Instance 3
partitions 1 are all assgiend to Instance 4
partitions 3 are all assgiend to Instance 5

what happen if we run more consumer instances > partitions


start 7 instances
Instance 1 - 10 msg/sec
Instance 2 - 10 msg/sec
Instance 3 - 10 msg/sec
Instance 4 - 10 msg/sec
Instance 5 - 10 msg/sec
Instance 6 -  msg/sec
Instance 7 -  msg/sec

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices


create-invoice-consumer.java - 10 msg/sec, 
        consume orders from orders topic
        create invoices

partitions 0 are all assgiend to Instance 1
partitions  2 are all assgiend to Instance 2
partitions 4 are all assgiend to Instance 3
partitions 1 are all assgiend to Instance 4
partitions 3 are all assgiend to Instance 5

NO parition is  assgiend to Instance 6 [IDLE] - $$
No parition is  assgiend to Instance 7 [IDLE] - $$

2:00 PM

Java JDK 1.8
IntelliJ  Java Code
confluent kafka downlaoded
be sharp 2:00 PM




re-partition(s)
    broker - 0 - failed
        order-0 [lead] - 

    broker -1 
        order-0 [lead]
     broker -2 
        order-0 [replica]

    broker - 5
        order-0  [replica]
            
{
    order-0, broker-5 as replica
}

 
Open command prompt

java --version

javac --version

Add below to environment variable PATH
c:\programs file\java\jdk1.8.xyz\bin

extract the confluent2.2.2.....zip 
    into c:\confluent-5.2.2
                bin
                etc
                share


by chance, if the files are in
c:\confluent-5.2.2\confluent\5.2.2.xyz/bin

move to 
c:\confluent-5.2.2
            bin


ENVIRONMENT VARIABLES

JAVA_HOME to c:\program files\java\jdk1.8xyz
KAFKA_HOME to c:\confluent-5.2.2

PATH, might be already there, you need to add below to path

c:\confluent-5.2.2\bin\windows

SAVE/APPLY ALL ENVIRONEMNTS 


open new command prompt to start zookeeper

zookeeper-server-start %KAFKA_HOME%\etc\kafka\zookeeper.properties

zookeeper runs on port 2181 by default


--

open second command prompt

KAFKA BROKER

kafka-server-start %KAFKA_HOME%\etc\kafka\server.properties

Runs on port 9092


---

kafka-topics --list --zookeeper localhost:2181

kafka-topics  --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

kafka-topics --list --zookeeper localhost:2181

kafka-topics --describe --zookeeper localhost:2181 --topic test


open 4th command prompt

notes: enter some text and press enter key, each line is consider as one message value, key is null here

kafka-console-producer --broker-list localhost:9092 --topic test

--

note: open 5th Command Prompt

listen for the messages published/latest

kafka-console-consumer --bootstrap-server localhost:9092 --topic test


listen/read all the messsages from beginning, then listen for new messages


kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning

to print the key

kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning --property print.key=true

to read data from specific partition

kafka-console-consumer --bootstrap-server localhost:9092 --topic test --partition 0  --from-beginning

to read data from specific partition and the specific offset onwards


kafka-console-consumer --bootstrap-server localhost:9092 --topic test --partition 0 --offset 3


to read from specific offset and mention number of max messages and then exit

kafka-console-consumer --bootstrap-server localhost:9092 --topic test --partition 0 --offset 3 --max-messages 2



kafka-topics  --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic greetings

kafka-topics --list --zookeeper localhost:2181

kafka-topics --describe --zookeeper localhost:2181 --topic greetings

5 Mins

Try to produce consume hte topics from greetings

try to read from partitcular partitions

--partition 0
--partition 1
--partition 2



kafka-console-producer --broker-list localhost:9092 --topic greetings




kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings --from-beginning --property print.key=true



kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings --partition 0 --from-beginning --property print.key=true


kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings --partition 1 --from-beginning --property print.key=true

kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings --partition 2 --from-beginning --property print.key=true


Message Ordered Delivery

Producer produce the messages in order

whether consumer can consume in same order or not? 

Ans: If the topic has only one partition, then consumer will consume message in order the message produced

if the topic has more than one partitions, then ordered delivery is not possible with KAFKA, but within parition ordered delivery is guareentee


example, to include the key in the console producer.

kafka-console-producer --broker-list localhost:9092 --topic greetings --property "parse.key=true" --property "key.separator=:"

>key:value
>birthday:happy birtday
>republic:happy republic day from Krish

based on key hash code, the partition decided
given the same key, the same partition shall be assigned

After break

1. CONSUMER GROUP cli
2. Kafka Cluster - Replications
3. Simple Consumer and Simple Producer Java Code


Consumer Group is group of consumer instances
Consuemr Group shall have unique id

Consumers inside consumer group split the partitions

For producer

kafka-console-producer --broker-list localhost:9092 --topic greetings --property "parse.key=true" --property "key.separator=:"


3 commands prompts

kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings  --group greetings-consumer-group  --property print.key=true


kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings   --group greetings-consumer-group  --property print.key=true


kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings   --group greetings-consumer-group  --property print.key=true

kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings   --group greetings-consumer-group  --property print.key=true


# consumer group commands

kafka-consumer-groups --bootstrap-server localhost:9092 --list

kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group greetings-consumer-group

with active members if any

kafka-consumer-groups --bootstrap-server k5.nodesense.ai:9092 --describe --group invoice-consumer-example --members

--state [assignment strategy, round robin, range]

kafka-consumer-groups --bootstrap-server k5.nodesense.ai:9092 --describe --group invoice-consumer-example --state

delete consumer group --group my-other-group1 --group my-other-group2

kafka-consumer-groups --bootstrap-server localhost:9092 --delete --group --group invoice-consumer-example


------



KAFKA in cluster mode on the same machine

-- You can listen on only one port on given IP

Cluster setup on the same machines, we need to different ports

broker- id: 0, port: 9092, log dir c:/tmp/kakfa-logs
broker - id: 1, port: 9093, log dir c:/tmp/kakfa-logs-1
broker - id: 2, port: 9094, log dir c:/tmp/kakfa-logs-2
broker - id: 3, port: 9095, log dir c:/tmp/kakfa-logs-3
zookeeper: 2191, single instance

Already Running

kafka-server-start %KAFKA_HOME%\etc\kafka\server.properties

Runs on port 9092, broker id 0

open new command prompt

kafka-server-start %KAFKA_HOME%\etc\kafka\server-1.properties

Runs on port 9093, broker-id 1


open new command prompt

kafka-server-start %KAFKA_HOME%\etc\kafka\server-2.properties

Runs on port 9094, broker-id 2


open new command prompt

kafka-server-start %KAFKA_HOME%\etc\kafka\server-3.properties

Runs on port 9095, broker-id 3

check c:\tmp\
        kafka-logs
        kafka-logs-1
        kafka-logs-2
        kafka-logs-3


How to check if cluster is ready, 
whether brokers are working or not?

open in new command prompt

zookeeper-shell  localhost:2181


zookeeper store data in hierarchy structure, like tree, znode

some commands

ls /
ls /brokers
ls /brokers/ids
ls /brokers/topics
get /brokers/topics/greetings



to exit zookeeper-cli, press ctrl + c


kafka-topics  --create --zookeeper localhost:2181 --replication-factor 3 --partitions 4 --topic messages


kafka-topics --list --zookeeper localhost:2181

kafka-topics --describe --zookeeper localhost:2181 --topic messages


kafka-console-producer --broker-list localhost:9092 --topic messages


kafka-log-dirs  --describe --bootstrap-server localhost:9092  --topic-list greetings


MAC

zookeeper-server-start $KAFKA_HOME/etc/kafka/zookeeper.properties
kafka-server-start $KAFKA_HOME/etc/kafka/server.properties
kafka-server-start $KAFKA_HOME/etc/kafka/server-1.properties
kafka-server-start $KAFKA_HOME/etc/kafka/server-2.properties
kafka-server-start $KAFKA_HOME/etc/kafka/server-3.properties
    schema-registry-start $KAFKA_HOME/etc/schema-registry/schema-registry.properties

kafka-topics  --create --zookeeper localhost:2181 --replication-factor 1 --partitions 4 --topic test2
