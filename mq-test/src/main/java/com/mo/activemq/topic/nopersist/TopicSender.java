package com.mo.activemq.topic.nopersist;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicSender {
    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://122.112.217.219:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //创建主题
        Topic topic = session.createTopic("nopersisi-topic");

        MessageProducer producer = session.createProducer(topic);

        TextMessage textMessage = session.createTextMessage("hello");

        producer.send(textMessage);

        session.commit();
        session.close();
        connection.close();
    }
}
