package com.example.javaspringbootrabbitmqdemo.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author sh on 2018/7/17
 * @version ideaIU-2017.2.5.win
 */
public class MsgSendReturnCallBackListener implements RabbitTemplate.ReturnCallback {
    private static Logger logger = LoggerFactory.getLogger(MsgSendConfirmCallBack.class);
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("消息发送失败即将重新发送。。。。。。");
    }
}
