package com.gox.jms.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MqSender mqSender;

    @Autowired
    private MqConfig mqConfig;

    @PostMapping("send")
    public String send(@RequestBody Message message){
        try{
            mqSender.send(message);
            return "OK";
        } catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("config")
    public List<MqProperties> config(){
       return mqConfig.getQueueManagers();
    }

    @GetMapping("queues")
    public List<String> queues(){
        return mqConfig.getQueueNames();
    }
}
