package me.cloudcat.develop.service.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息给队列1
     * @param message
     */
    public void sendMessage1(Object message) {
        amqpTemplate.convertAndSend("key.123.434", message);
    }

    /**
     * 发送消息给队列2
     * @param message
     */
    public void sendMessage2(Object message) {
        amqpTemplate.convertAndSend("key.abc.434", message);
    }

    /**
     * 发送并接收消息
     */
    public void sendMessage3(Object message) {
        Object o = amqpTemplate.convertSendAndReceive("key.123.1", message);
        // 打印队列返回的消息
//        System.out.println("队列返回的数据：" + o.toString());
    }
}
