package com.hogan.sample.message.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * TODO
 * wujun
 * 2020/01/08 14:44
 */
@Slf4j
@Service
@RocketMQTransactionListener(txProducerGroup = "rocketmq-spring-boot-producer-transaction")
public class TransactionalMessageListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.printf("executeLocalTransaction：%s，参数:%s%n", msg, arg);
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        System.out.printf("checkLocalTransaction：%s%n", msg);
        return RocketMQLocalTransactionState.COMMIT;
    }
}
