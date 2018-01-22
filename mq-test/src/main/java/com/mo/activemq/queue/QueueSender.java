package com.mo.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueSender {

    public static void main(String[] args) throws Exception {

        //failover容错
        //failover:(uri1,uri2...)?key=value
        //这种协议用于随机的去选择一个地址去连接，如果连接失败了，会找其他的连接。
        //randomize使用随机连接，达到负载均衡的效果，默认为true
        String brokerURL = "failover:(tcp://122.112.217.219:61616,tcp://122.112.217.219:61617)?randomize=true";
        //String brokerURL = "tcp://122.112.217.219:61616";
        //String brokerURL = "tcp://localhost:61616";
        //1 连接activemq服务器
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

        //2 创建连接，并启动
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //3 创建会话
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

        //4 创建目的地，消息发送的地址
        Destination destination = session.createQueue("my-queue");

        //5 创建生产者，消息发送人
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 10; i++) {
            //6 创建消息，并发送
            TextMessage textMessage = session.createTextMessage("message--" + i);
            //Thread.sleep(1000);
            producer.send(textMessage);
        }

        //7 清理
        session.commit();
        session.close();
        connection.close();

    }

}
