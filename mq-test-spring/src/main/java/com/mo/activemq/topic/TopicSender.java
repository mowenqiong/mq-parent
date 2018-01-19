package com.mo.activemq.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class TopicSender {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("destinationTopic")
    private Destination topic;

    public void sendMsg(){
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("来自topic的消息");
            }
        });
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-amq.xml");
        TopicSender sender = (TopicSender) context.getBean("topicSender");
        sender.sendMsg();
    }
}
