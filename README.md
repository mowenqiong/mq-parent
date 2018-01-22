# mq-parent
activemq的例子  
**mq-test**:使用jms api的例子  
**mq-test-spring**:使用spring集成activemq  

## activemq依赖
```xml
<dependency>
    <groupId>org.apache.activemq</groupId>
    <artifactId>activemq-all</artifactId>
    <version>5.9.0</version>
</dependency>
<dependency>
    <groupId>org.apache.xbean</groupId>
    <artifactId>xbean-spring</artifactId>
    <version>3.16</version>
</dependency>
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

## 消息生产者
```java
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class QueueSender {

    public static void main(String[] args) throws Exception {

        //1 连接activemq服务器
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://122.112.217.219:61616");

        //2 创建连接，并启动
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //3 创建会话
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

        //4 创建目的地，消息发送的地址
        Destination destination = session.createQueue("my-queue");

        //5 创建生产者，消息发送人
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 3; i++) {
            //6 创建消息，并发送
            TextMessage textMessage = session.createTextMessage("message--" + i);
            Thread.sleep(1000);
            producer.send(textMessage);
        }

        //7 清理
        session.commit();
        session.close();
        connection.close();
    }
}
```

## 消息消费者
```java

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class QueueReceiver {
    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://122.112.217.219:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("my-queue");

        MessageConsumer consumer = session.createConsumer(destination);

        int i=0;
        while (i<3){
            i++;
            TextMessage message = (TextMessage) consumer.receive();
            session.commit();
            System.out.println("收到消息:"+message.getText());
        }

        session.close();
        connection.close();
    }
}
```
