package com.mo.activemq.topic.nopersist;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 订阅模式下，一条消息可以有多个消费者
 */
public class TopicReceiver {

    public static void main(String[] args) {
        for(int i=0;i<2;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new TopicReceiver().receive();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void receive() throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://122.112.217.219:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("nopersisi-topic");
        MessageConsumer consumer = session.createConsumer(topic);
        Message message = consumer.receive();
        while (message!=null){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("收到消息："+textMessage.getText());
            message = consumer.receive(1000l);
        }
        session.commit();
        session.close();
        connection.close();
    }
}
