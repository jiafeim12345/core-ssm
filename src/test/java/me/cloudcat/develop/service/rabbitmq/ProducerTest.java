package me.cloudcat.develop.service.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-rabbitmq.xml"})
public class ProducerTest {

    @Autowired
    Producer producer;

    @Test
    public void sendMessage1() throws Exception {
        producer.sendMessage1("测试队列");
    }

    @Test
    public void sendMessage2() throws Exception {
        producer.sendMessage1("测试队列");
    }

    @Test
    public void sendMessage3() throws Exception {
        producer.sendMessage3("测试队列");
    }
}