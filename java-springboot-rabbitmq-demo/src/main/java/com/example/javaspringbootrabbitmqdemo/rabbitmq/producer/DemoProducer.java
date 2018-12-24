package com.example.javaspringbootrabbitmqdemo.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author sh 2018/12/24 10:17
 * @version ideaIU-2018.2.5.win
 */
@Component
public class DemoProducer {
    @Resource
    RabbitTemplate rabbitTemplate;
    public void send(String message) {

        String uuid = UUID.randomUUID().toString();
        System.out.println("生产消息id：" + uuid);
        CorrelationData correlationData = new CorrelationData(uuid);
        // 发送消息到 交换机，
        rabbitTemplate.convertAndSend("exchange", "queen_routeKey", message, correlationData);
    }

    public static void main(String[] args) {
    }
}
