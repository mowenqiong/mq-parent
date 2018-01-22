package com.mo.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class QueueReceiver {

    public static void main(String[] args) {
        QueueReceiver receiver = new QueueReceiver();
        receiver.receive("tcp://122.112.217.219:61616","que1",2);
    }

    /**
     *
     * @param brokerURL 地址
     * @param queueName 队列
     * @param cout 消费消息条数
     */
    public void receive(String brokerURL,String queueName,int cout){
        new Thread(() -> {
            try {
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

                Connection connection = connectionFactory.createConnection();
                connection.start();

                final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue(queueName);

                MessageConsumer consumer = session.createConsumer(destination);

                int i=0;
                while (i<cout){
                    i++;
                    TextMessage message = (TextMessage) consumer.receive(1000L);
                    if (message==null) break;
                    session.commit();//commit后，自动签收，下次就不会再收到这条消息了
                    System.out.println("收到消息:"+message.getText());
                }

                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }).start();

    }


}
