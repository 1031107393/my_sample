package com.hogan.sample.message;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * wujun
 * 2020/01/06 19:27
 */
public class BatchProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("wujun");
        producer.setNamesrvAddr("172.16.30.97:9876");
        producer.start();
        String topic = "mro";
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(topic, "tag_a", "order001", "hello_world1".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "tag_a", "order001", "hello_world2".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "tag_a", "order001", "hello_world3".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        producer.send(messages);
        producer.shutdown();
    }
}
