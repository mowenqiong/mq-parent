package com.mo.activemq.queue;

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
public class QueueSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("destinationQueue")
    private Destination destination;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-amq.xml");
        QueueSender queueSender = (QueueSender) context.getBean("queueSender");
        queueSender.jmsTemplate.send(queueSender.destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello");
            }
        });
    }

}
