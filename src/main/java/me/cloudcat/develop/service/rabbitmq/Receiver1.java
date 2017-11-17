package me.cloudcat.develop.service.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver1 implements MessageListener {

    @Autowired
    private RabbitTemplate template;

    @Override
    public void onMessage(Message message) {
        System.out.println("Received1:" + new String(message.getBody()));
//        template.convertAndSend("key.abc.125", "此处为队列1返回的数据");
    }
}
