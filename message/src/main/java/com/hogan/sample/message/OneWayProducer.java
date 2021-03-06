package com.hogan.sample.message;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * TODO
 * wujun
 * 2020/01/06 17:00
 */
public class OneWayProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("wujun");
        producer.setNamesrvAddr("172.16.30.97:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("mro", "tag_b", "mro_" + i, ("mro message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(message);
        }
        producer.shutdown();
    }
}
