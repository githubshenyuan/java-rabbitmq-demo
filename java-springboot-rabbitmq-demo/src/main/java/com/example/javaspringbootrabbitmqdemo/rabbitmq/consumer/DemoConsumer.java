package com.example.javaspringbootrabbitmqdemo.rabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author sh 2018/12/24 10:18
 * @version ideaIU-2018.2.5.win
 */
@Component

public class DemoConsumer {

    @RabbitListener(queues = "queen", containerFactory = "container_name")
    @RabbitHandler
    public void process(@Payload String msg,
                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                        Channel channel, Message message) {

        try {
            MessageProperties messageProperties = message.getMessageProperties();
            Map<String, Object> headers = messageProperties.getHeaders();

            //TODO 业务处理
            int i = Integer.valueOf(msg);
            i=1 / i;
            System.out.println("消息消费成功,内容："+msg);

            // 消息成功确认
            // deliveryTag 消息 投递标记
            // multiple 是否批量确认，false
            channel.basicAck(deliveryTag,false);

        } catch (Exception e) {
            try {
                System.out.println("消息消费失败,内容："+e.getMessage());
                channel.basicNack(deliveryTag, false, false);
            } catch (Exception ex) {
                System.out.println("消息处理失败，回调消息队列失败，请人工处理：");
            }

        }

    }

}
