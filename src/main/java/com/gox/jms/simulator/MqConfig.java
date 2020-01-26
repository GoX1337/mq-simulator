package com.gox.jms.simulator;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.spring.boot.MQConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("mq")
public class MqConfig {

    private List<MqProperties> queueManagers = new ArrayList<>();
    private List<String> queueNames = new ArrayList<>();

    public List<MqProperties> getQueueManagers() {
        return queueManagers;
    }

    public void setQueueManagers(List<MqProperties> queueManagers) {
        this.queueManagers = queueManagers;
    }

    public List<String> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(List<String> queueNames) {
        this.queueNames = queueNames;
    }

    @Bean
    public MQConnectionFactory buildMqConnectionFactory(MQConfigurationProperties props) {
        MQConnectionFactory cf = new MQConnectionFactory();
        try {
            cf.setStringProperty("XMSC_WMQ_QUEUE_MANAGER", props.getQueueManager());
            cf.setStringProperty("XMSC_WMQ_CONNECTION_NAME_LIST", props.getConnName());
            cf.setStringProperty("XMSC_WMQ_CHANNEL", props.getChannel());
            cf.setIntProperty("XMSC_WMQ_CONNECTION_MODE", 1);
            cf.setStringProperty("XMSC_USERID", "admin");
            cf.setStringProperty("XMSC_PASSWORD", "passw0rd");
            cf.setBooleanProperty("XMSC_USER_AUTHENTICATION_MQCSP", true);

        } catch (JMSException e) {
            e.printStackTrace();
        }
        return cf;
    }

    @Bean
    public HashMap<String, JmsTemplate> jmsTemplateMap() {
        // Map : QueueName -> JmsTemplate
        HashMap<String, JmsTemplate> jmsTemplateMap = new HashMap<>();

        for (MqProperties props : this.getQueueManagers()){
            JmsTemplate jmsTemplate = new JmsTemplate(buildMqConnectionFactory(props));
            jmsTemplate.setReceiveTimeout(50000);
            for(String queue : props.getQueues()) {
                jmsTemplateMap.put(queue, jmsTemplate);
                this.queueNames.add(queue);
            }
        }
        return jmsTemplateMap;
    }
}
