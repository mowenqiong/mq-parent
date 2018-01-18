package com.mo.activemq.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component
public class QueueReceiver {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("destinationQueue")
    private Destination destination;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-amq.xml");
        QueueReceiver receiver = (QueueReceiver) context.getBean("queueReceiver");
        String msg = (String) receiver.jmsTemplate.receiveAndConvert(receiver.destination);
        System.out.println(msg);
    }
}
