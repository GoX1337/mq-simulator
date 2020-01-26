package com.gox.jms.simulator;

import com.ibm.mq.spring.boot.MQConfigurationProperties;

import java.util.List;

public class MqProperties extends MQConfigurationProperties {

    private List<String> queues;

    public List<String> getQueues() {
        return queues;
    }

    public void setQueues(List<String> queues) {
        this.queues = queues;
    }
}
