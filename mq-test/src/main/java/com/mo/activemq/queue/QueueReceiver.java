package com.mo.activemq.queue;

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
            TextMessage message = (TextMessage) consumer.receive(1000L);
            if (message==null) break;
            session.commit();//commit后，自动签收，下次就不会再收到这条消息了
            System.out.println("收到消息:"+message.getText());
        }

        session.close();
        connection.close();
    }
}
