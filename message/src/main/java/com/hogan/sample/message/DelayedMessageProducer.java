package com.hogan.sample.message;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * TODO
 * wujun
 * 2020/01/06 19:19
 */
public class DelayedMessageProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("wujun");
        producer.setNamesrvAddr("172.16.30.97:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("mro", "tag_a", "mro_" + i, ("mro message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            message.setDelayTimeLevel(3);
            SendResult result = producer.send(message);
            System.out.printf("%s%n", result);
        }
        producer.shutdown();
    }
}
