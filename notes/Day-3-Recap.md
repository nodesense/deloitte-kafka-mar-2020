# Day 4 recap

Consumer Group [should do the same job]
    Consumer1 [5 msg/sec]
    Consumer2 [5 msg/sec]
    ...
    
    
SMSGroup
    SMS Consumer pull message from kafka and use sms gateway to send message
    
Email
    Email Consumer pull message from kafka and use email gateway to send message
    
    
    

Offset commit
    __commit_offsets (50 partitions)

Use Case
    -- Parallism & Scaling
    -- High Availablity 
    
    
Topic: messages
Paritions: 3 paritions
messages-0
messages-1
messages-2


Consumer Groups:
    1. SMS Consumer Group
    2. Email Consumer Group
    
Run the consumer in the consumer group very first time:

    1. Consumers start with last known offset if last known offset is NOT NULL
    2. If last known offset is null, consumers start with messages posted now onwards
    
    3. Rebalance consumer, you subscribe for the message, and then ensure that 
       if last known offset is null, you start with begining
       
For each consumer group, there is commit offset per topic-partitions.

messages-0: [msg1, msg4, msg7, msg10, msg11, msg50, msg 60, msg 61]
messages-1: [msg2, msg5, msg8, msg 12, msg 15, msg 25]
messages-2: [msg3,msg5,..., msg100]


SMSGroup {
    messages-0: 5
    messages-1: 4
    messages-2: 20
}

EmailGroup {
    messages-0: 6
    messages-1: 5
    messages-2: null
}

You start a consumer in a SMSGroup:
  if offset is null, it sets read from messages now onwards
            reads msg11 [offset 4]
            commit offset 4 P0
            reads msg15, [offset 4]
               commit offset 4 P1
               
            reads msg100 [offset 20]
                commmit offset 20 P2
               
Stop the SMSGroup consumer group

Star the SMSGroup consumer group again:
    the offset is not null, then start from known commit offset
        Read msg 50
         commit offset 5
         
--

You start a consumer in a EmailGroup:
  if offset is null, it sets read from messages now onwards
            reads msg61  
            commit offset 6 - P0
            reads msg15, 
               commit offset 5 P1
         
Connect 
    subscribe from begining very first time
     then it follows offset       