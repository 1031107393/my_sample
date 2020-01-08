package com.hogan.sample.message.transaction;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 * wujun
 * 2020/01/07 14:22
 */
public class TransactionProducer {

    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("transaction");
        producer.setNamesrvAddr("172.16.30.97:9876");
        producer.setTransactionListener(new TransactionListenerImpl());
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(200), new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread thread = new Thread();
                thread.setName("transaction message check back thread");
                return thread;
            }
        });
        producer.setExecutorService(executorService);
        producer.setInstanceName("TxProducer-instance1");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("transaction", "tag_" + i, ("message_body_" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.sendMessageInTransaction(message, "wujun");
            System.out.printf("SendResult：%s%n", sendResult);

            Thread.sleep(10);
        }
        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}
