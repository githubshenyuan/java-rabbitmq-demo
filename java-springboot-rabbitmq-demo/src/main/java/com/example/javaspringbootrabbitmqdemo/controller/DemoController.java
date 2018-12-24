package com.example.javaspringbootrabbitmqdemo.controller;

import com.example.javaspringbootrabbitmqdemo.rabbitmq.consumer.DemoConsumer;
import com.example.javaspringbootrabbitmqdemo.rabbitmq.producer.DemoProducer;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sh 2018/12/24 10:43
 * @version ideaIU-2018.2.5.win
 */
@RestController
public class DemoController {
    // 消息生产者，注入
    @Resource
    DemoProducer demoProducer;

    @RequestMapping("mq")
    public String sendMsg(@RequestParam("msg") String msg) {
        if (msg == null || "".equals(msg)) {
            return "参数为空";
        }
        demoProducer.send(msg);
        return "rabbitmq";
    }
}
