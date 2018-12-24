package com.example.javaspringbootrabbitmqdemo.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @author s.h. Time: 2018/7/8 14:38
 * @version ideaIU-2018.1.4.win
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    protected static Logger logger = LoggerFactory.getLogger(MsgSendConfirmCallBack.class);
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 生产确认id:" + correlationData);
        if (ack) {
            System.out.println("消息生产成功:"+correlationData);
            // TODO 消息发送成功后 ，根据消息id 将本地消息删除。
        } else {
            System.out.println("消息生产失败:" + cause+"\n重新发送");
            // TODO  消息发送失败后，根据消息id  取出本地消息重新发送。

        }
    }
}
