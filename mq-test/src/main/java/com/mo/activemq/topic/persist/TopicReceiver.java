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
        TopicReceiver receiver = new TopicReceiver();
//      receiver.receive("tcp://122.112.217.219:61616","persist-topic",
//                "clientID-1","subscriber-1");
        receiver.receive("tcp://122.112.217.219:61616","Mirror.Topic.que1","clientID-1","subscriber-1");
    }

    public void receive(String brokerURL,String topicName,String clientID,String subscribeName) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(clientID);
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);

        TopicSubscriber subscriber = session.createDurableSubscriber(topic, subscribeName);

        connection.start();

        Message message = subscriber.receive();
        while (message!=null){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("【主题消息】："+textMessage.getText());
            message = subscriber.receive(1000l);
        }
        session.commit();
        session.close();
        connection.close();
    }
}