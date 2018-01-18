package com.mo.activemq.topic.persist;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicSender {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://122.112.217.219:61616");

        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("persist-topic");
        MessageProducer producer = session.createProducer(topic);

        //要持久化订阅，消息发送者要用DeliveryMode.PERSISTENT模式
        //要在start()之前设置
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();

        TextMessage textMessage = session.createTextMessage("hello");

        producer.send(textMessage);

        session.commit();
        session.close();
        connection.close();
    }
}
