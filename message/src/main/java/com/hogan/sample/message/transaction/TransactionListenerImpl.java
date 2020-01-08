package com.hogan.sample.message.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 * wujun
 * 2020/01/07 15:47
 */
public class TransactionListenerImpl implements TransactionListener {

    private AtomicInteger count = new AtomicInteger();

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<String, Integer>();

    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.printf("执行事务操作的消息：%s，参数：%s%n", msg, arg);
        localTrans.put(msg.getTransactionId(), count.getAndIncrement() % 3);
        return LocalTransactionState.UNKNOW;
    }

    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        Integer status = localTrans.get(msg.getTransactionId());
        System.out.printf("%s check back status %s", msg, status);
        if (status == null) return LocalTransactionState.COMMIT_MESSAGE;
        switch (status) {
            case 0:
                return LocalTransactionState.COMMIT_MESSAGE;
            case 1:
                return LocalTransactionState.ROLLBACK_MESSAGE;
            case 2:
                return LocalTransactionState.UNKNOW;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
