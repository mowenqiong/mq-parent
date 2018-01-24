package com.mo.activemq.topic.nopersist;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 主题消息发送者
 * 其他的订阅者要事先启动
 *
 * 默认的虚拟主题是在主题名前加VritualTopic前缀，比如VritualTopic.a
 * 消费者1,它去消费队列名为Consumer.A.VritualTopic.a的消息
 * 消费者2,它去消费队列名为Consumer.B.VritualTopic.a的消息
 * 消费者1和2要事先注册后，虚拟主题才会把消息转发到这2个队列中
 */
public class TopicSender {

    public static void main(String[] args) throws JMSException {
        TopicSender topicSender = new TopicSender();
        topicSender.send("tcp://122.112.217.219:61616","VirtualTopic.a","hello");

    }

    public void send(String brokerURL,String topicName,String message) throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //创建主题
        Topic topic = session.createTopic(topicName);

        //创建生产者
        MessageProducer producer = session.createProducer(topic);

        //消息
        TextMessage textMessage = session.createTextMessage(message);

        //发送消息
        producer.send(textMessage);

        session.commit();
        session.close();
        connection.close();
    }

}
