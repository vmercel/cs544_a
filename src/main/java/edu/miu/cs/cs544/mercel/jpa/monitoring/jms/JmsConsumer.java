package edu.miu.cs.cs544.mercel.jpa.monitoring.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumer {

    @Value("${jms.queue.food-log-receive}")
    private String foodLogQueue;

    @JmsListener(destination = "${jms.queue.food-log-receive}")
    public void consumeMessage(String message) {
        System.out.println("Received message from queue " + foodLogQueue + ":\n " + message);
    }

}
