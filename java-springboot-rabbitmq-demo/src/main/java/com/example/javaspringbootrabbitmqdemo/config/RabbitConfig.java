package com.example.javaspringbootrabbitmqdemo.config;

import com.example.javaspringbootrabbitmqdemo.rabbitmq.MsgSendConfirmCallBack;
import com.example.javaspringbootrabbitmqdemo.rabbitmq.MsgSendReturnCallBackListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author sh 2018/12/24 12:25
 * @version ideaIU-2018.2.5.win
 */
@Configuration
public class RabbitConfig {
    @Resource
    ConnectionFactory connectionFactory;
    /**
     * 实例渠道交换机
     * @return
     */
    @Bean(name = "exchange")
    public TopicExchange exchange() {
        return new TopicExchange("exchange");

    }

    /**
     * 初始化队列
     * @return
     */
    @Bean(name = "queen")
    public Queue queue(){
        return QueueBuilder.durable("queen")
                .withArgument("x-dead-letter-exchange", "exchange")
                .withArgument("x-dead-letter-routing-key", "queen_delay_routeKey")
                //.withArgument("x-message-ttl",10000)
                //.autoDelete()
                .build();
    }
    /**
     * 队列 绑定到交换机
     * @return
     */
    @Bean
    public Binding bindingExchange() {
        return BindingBuilder.bind(queue()).to(exchange()).with("queen_routeKey");
    }


    /**
     * 延迟队列
     * @return
     */
    @Bean(name = "queue_delay")
    public Queue queueDelay(){
        return QueueBuilder
                .durable("queue_delay")
                .withArgument("x-dead-letter-exchange", "exchange")
                .withArgument("x-dead-letter-routing-key", "queen_routeKey")
                .withArgument("x-message-ttl",60000)
                //.autoDelete()
                .build();
    }

    /**
     * 延迟队列 绑定到交换机
     * @return 返回
     */
    @Bean
    public Binding bindingDelay() {
        return BindingBuilder.bind(queueDelay()).to(exchange()).with("queen_delay_routeKey");
    }

    @Bean("container_name")
    public SimpleRabbitListenerContainerFactory pointTaskContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        System.out.println("初始化监听容器");
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//必须显示确认
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        configurer.configure(factory, connectionFactory);
        return factory;
    }


    /**
     * 定义rabbitMQ 模板
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(msgSendConfirmCallBack());
        //rabbitTemplate.setRecoveryCallback();
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * 实例化生产者消息生产成功确认
     * @return
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }
    /**
     * 实例化生产者消息生产失败监听
     * @return
     */
    @Bean
    public MsgSendReturnCallBackListener msgSendReturnCallBackListener(){
        return new MsgSendReturnCallBackListener();
    }
}
