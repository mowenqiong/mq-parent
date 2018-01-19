package com.mo.activemq.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component
public class TopicReceiver {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("destinationTopic")
    private Destination topic;

    public void receiveMsg(){
        String msg = (String) jmsTemplate.receiveAndConvert(topic);
        System.out.println(msg);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-amq.xml");
        TopicReceiver receiver = (TopicReceiver) context.getBean("topicReceiver");
        receiver.receiveMsg();
    }
}
