package com.mo.activemq.broker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InnerBroker {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-broker.xml");
    }
}
