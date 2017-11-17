package me.cloudcat.develop.service.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver2 implements MessageListener{

    @Override
    public void onMessage(Message message) {
        System.out.println("Received2:" + new String(message.getBody()));
    }
}
