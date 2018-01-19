package com.mo.activemq.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//只要有消息发送过来，这里就会接收到
public class TopicMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage msg = (TextMessage) message;
        try {
            System.out.println("TopicMessageListener:"+msg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
