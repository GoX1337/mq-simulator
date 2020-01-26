package com.gox.jms.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MqSender {

    @Autowired
    private HashMap<String, JmsTemplate> jmsTemplateMap;

    public void send(Message message){
        jmsTemplateMap.get(message.getQueueName()).convertAndSend(message.getQueueName(), message.getPayload());
    }

}
