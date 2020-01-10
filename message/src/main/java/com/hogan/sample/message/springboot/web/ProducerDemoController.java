package com.hogan.sample.message.springboot.web;

import com.hogan.sample.message.springboot.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * wujun
 * 2020/01/08 11:45
 */
@Slf4j
@RestController
@RequestMapping("/producer")
public class ProducerDemoController {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @GetMapping("/sendSync/oderEvent")
    public void sendSyncOderEvent() {
        Order order = new Order();
        order.setOrderId("20200108001");
        order.setAmount(new BigDecimal("1000.08"));
        rocketMQTemplate.send("order:paid", MessageBuilder.withPayload(order).build());
        log.info("sendSyncOderEvent 发送消息成功");
    }

    @GetMapping("/batchSendSync/oderEvent")
    public void batchSendSyncOderEvent() {

        List<Message> messages = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderId("20200108001");
        order1.setAmount(new BigDecimal("1000.08"));
        Order order2 = new Order();
        order2.setOrderId("20200108002");
        order2.setAmount(new BigDecimal("1000.09"));
        messages.add(MessageBuilder.withPayload(order1).build());
        messages.add(MessageBuilder.withPayload(order2).build());
        rocketMQTemplate.syncSend("order:paid", messages, 60 * 1000);
        log.info("batchSendSyncOderEvent 发送消息成功");
    }

    @GetMapping("/sendTransaction/orderEvent")
    public void sendTransactionOderEvent() {
        Order order1 = new Order();
        order1.setOrderId("20200108003");
        order1.setAmount(new BigDecimal("1000.99"));
        Order order2 = new Order();
        rocketMQTemplate.sendMessageInTransaction("rocketmq-spring-boot-producer-transaction",
                "transaction_order:paid", MessageBuilder.withPayload(order1).build(), "参数");
    }
}
