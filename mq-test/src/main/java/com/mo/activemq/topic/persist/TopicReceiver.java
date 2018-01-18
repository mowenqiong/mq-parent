package com.mo.activemq.topic.persist;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 需要在连接上设置消费者id，用来识别消费者
 * 需要创建TopicSubscriber来订阅
 * 要设置好了过后再connection.start()
 * 一定要先运行一次，向消息服务中间件注册这个消费者，然后再运行客户端发送消息，这个时候，
 * 无论消费者是否在线，都会接收到。不在线的话，下次连接的时候，会把没有收过的消息都接收下来
 */
public class TopicReceiver {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://122.112.217.219:61616");
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("clientID-1");
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("persist-topic");

        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "subscriber-1");

        connection.start();

        Message message = subscriber.receive();
        while (message!=null){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("收到消息："+textMessage.getText());
            message = subscriber.receive(1000l);
        }
        session.commit();
        session.close();
        connection.close();
    }
}