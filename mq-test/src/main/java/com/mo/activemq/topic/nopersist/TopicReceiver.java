package com.mo.activemq.topic.nopersist;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 订阅模式下，一条消息可以有多个消费者
 * 要先启动才能收到消息，启动之前的消息就收不到了
 */
public class TopicReceiver {

    public static void main(String[] args) throws JMSException{
        TopicReceiver topicReceiver = new TopicReceiver();
        topicReceiver.receive("tcp://122.112.217.219:61616","Mirror.Topic.que1");
    }

    public void receive(String brokerURL,String topicName) throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        //创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //接收消息
        Message message = consumer.receive();
        while (message!=null){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("【主题消息】："+textMessage.getText());

            //继续收消息，超过1秒还没收到消息就退出
            message = consumer.receive(1000l);
        }
        session.commit();
        session.close();
        connection.close();
    }
}
