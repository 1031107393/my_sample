package com.hogan.sample.message.springboot.service;

import com.hogan.sample.message.springboot.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 * wujun
 * 2020/01/08 11:57
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "order", selectorType = SelectorType.TAG, selectorExpression = "paid",
        consumerGroup = "${spring.application.name}")
public class ConsumerListener implements RocketMQListener<Order> {

    private AtomicInteger count = new AtomicInteger();

    @Override
    public void onMessage(Order message) {
        log.warn("order ConsumerListener 第{}次接收到消息：{}", count.incrementAndGet(), message);
    }
}
